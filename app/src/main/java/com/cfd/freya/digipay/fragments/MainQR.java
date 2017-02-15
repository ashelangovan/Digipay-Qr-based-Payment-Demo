package com.cfd.freya.digipay.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cfd.freya.digipay.Models.QRModel;
import com.cfd.freya.digipay.R;
import com.cfd.freya.digipay.adapter.CustomAdapter;
import com.cfd.freya.digipay.starter.Login;
import com.cfd.freya.digipay.starter.LoginRequest;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainQR extends Fragment {
    Boolean success=false;
    CountDownTimer cdt;
    private Button buttonScan,buttonOpenPay;
    ListView listView;
    View v;
    //qr code scanner object
    private IntentIntegrator qrScan;
    LinearLayout qrLayout;
    public static QRModel qrModel=null;
    CustomAdapter customAdapter=null;
    public static MainQR newInstance() {
        MainQR fragment = new MainQR();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_main, container, false);
        qrLayout=(LinearLayout)v.findViewById(R.id.qr_layout);
        qrLayout.setVisibility(View.INVISIBLE);
        buttonScan = (Button) v.findViewById(R.id.buttonScan);
        buttonOpenPay = (Button) v.findViewById(R.id.buttonPay);
        listView=(ListView)v.findViewById(R.id.list);
//        listView.setVisibility(View.GONE);
        //intializing scan object
        qrScan = new IntentIntegrator(getActivity());

        //attaching onclick listener
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                success=false;qrScan.initiateScan();
            }
        });
        buttonOpenPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Payment");
                builder.setMessage("Choose Your Preferred Payment");

                builder.setPositiveButton("Cash", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        dialog.dismiss();
                        startPolling();
                    }
                });

                builder.setNegativeButton("PayTm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("net.one97.paytm");
                        if (launchIntent != null) {
                            startActivity(launchIntent);//null pointer check in case package name was not found
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please install PayTm",Toast.LENGTH_SHORT).show();
                        }
                        // Do nothing
                        dialog.dismiss();
                        startPolling();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }});
        buttonScan.performClick();
        return v;
    }
    //Getting the scan results

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.d("MainQr","NR");
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    Gson gson =new Gson();
                    qrModel=gson.fromJson(result.getContents(),QRModel.class);
                    System.out.println(result.getContents());
                    System.out.println(gson.toJson(qrModel));
                    if(qrModel.getItemArrayList()!=null) {
                        customAdapter = new CustomAdapter(getActivity(), qrModel);
                        qrLayout.setVisibility(View.VISIBLE);
                        TextView tvDate=(TextView)v.findViewById(R.id.date);
                        TextView tvFrom=(TextView)v.findViewById(R.id.storename);
                        TextView tvTo=(TextView)v.findViewById(R.id.username);
                        TextView tvTotal=(TextView)v.findViewById(R.id.total);
                        tvDate.setText(qrModel.getDate().substring(0,24));
                        tvFrom.setText(qrModel.getStoreName());
                        qrModel.userName=Login.username;
                        tvTo.setText(qrModel.getUserName());
                        tvTotal.setText(qrModel.getGrandTotal());
                        listView.setAdapter(customAdapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void startPolling() {


        cdt = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                if (seconds % 9 == 0) {
                 //   cdt.cancel();
                    if(success)
                    {
                        cdt.cancel();
                        addTransactionToDB();

                    }
                    check();

                }
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                Toast.makeText(getActivity(), "Transaction Failed", Toast.LENGTH_SHORT).show();
            }

        }.start();
    }
    public void check(){
        final Response.Listener<String> listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length()>3)
                {
                    String res;
                    if(response.indexOf('<')>0) {
                        res = response.substring(0, response.indexOf('<'));
                    }
                    else
                    res=response;

                    if(res.trim().equalsIgnoreCase("Success")){
                        success=true;
                  }
                    else{
                       System.out.println(res);
                }
                }
            };

        };
        CompletionRequest request = new CompletionRequest(qrModel.billId,listener);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }
    public void addTransactionToDB(){
    final Response.Listener<String> listener=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
          Toast.makeText(getActivity(),"Transaction Saved",Toast.LENGTH_SHORT).show();
        }
    };
        AddDbRequest addDbRequest=new AddDbRequest(qrModel,listener);
        RequestQueue queue=Volley.newRequestQueue(getActivity());
        queue.add(addDbRequest);
        System.out.println("transaction sento db");
    }

}
