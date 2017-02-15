package com.cfd.freya.digipay.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.cfd.freya.digipay.Models.QRModel;
import com.cfd.freya.digipay.R;
import com.cfd.freya.digipay.adapter.CustomAdapter;
import com.cfd.freya.digipay.adapter.CustomAdapterForList;
import com.cfd.freya.digipay.starter.Login;
import com.cfd.freya.digipay.starter.Register;
import com.cfd.freya.digipay.starter.RegisterRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TransactionHistory extends Fragment {
    View v;
    ArrayList<QRModel> transactionList;
    ListView listView;

    public static TransactionHistory newInstance() {
        TransactionHistory fragment = new TransactionHistory();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_transction, container, false);
        listView=(ListView)v.findViewById(R.id.list_transaction);

        getData();
        return v;
    }


    public void getData(){
        class GetDataJSON extends AsyncTask<String, Void, Boolean> {
            String res;
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            @Override
            protected void onPreExecute() {
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Loading. Please wait...");
                dialog.setIndeterminate(true);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                res="";
            }

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    URL url = new URL("http://foodos.netai.net/retrievelogs.php");
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String data= URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode("rahul@gmail.com","UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line="";
                    Integer i=1;
                    Boolean result=false;
                    while((line=bufferedReader.readLine())!=null)
                    {
                        if(line.equalsIgnoreCase("Success")) {
                            result = true;
                            break;
                        }
                        else
                            result=false;
                        res=res+line;
                        Log.d("res",line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (UnknownHostException exception) {
                Toast.makeText(getActivity(),"Please connect to internet",Toast.LENGTH_SHORT).show();
                }    catch (IOException
                 e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            protected void onPostExecute(Boolean te) {
        dialog.hide();

//                myJSON=te;
//                //t1.setText(myJSON);
//                res="";
                Log.d("resulttt", res + "");
                // Toast.makeText(getApplicationContext(), "return" + te, Toast.LENGTH_LONG).show();
                if(res.length()>0) {
                    res = res.substring(0, res.indexOf('<'));
                    res=res.replaceAll("\\\\", "");
                    StringBuilder sb=new StringBuilder(res);

                    if(sb.charAt(sb.indexOf("item")+6)=='\"')
                        sb.deleteCharAt(sb.indexOf("item")+6);
                    sb.deleteCharAt(sb.lastIndexOf("\""));
                    res=sb.toString();
                }
                System.out.println(res);
                try {
                    Gson gson = new Gson();

                    Type type = new TypeToken<ArrayList<QRModel>>() {
                    }.getType();


                     transactionList= gson.fromJson(res, type);
                    System.out.println(gson.fromJson(res, type));

                    if (transactionList.size()>0) {
                        //Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();

                        listView.setAdapter(new CustomAdapterForList(getActivity(),transactionList));
                        //finish();
                    } else {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_LONG).show();
                        //   startActivity(new Intent(LoginActivity.this, RequestBlood.class));
                    }
//                la=showList();
//                if (te != null) return te;
//                // t1.setText(te);
                }catch (Exception e){e.printStackTrace();}
            }

        }
        GetDataJSON g = new GetDataJSON();
        g.execute();

    }
}
