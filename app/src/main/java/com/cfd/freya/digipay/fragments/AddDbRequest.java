package com.cfd.freya.digipay.fragments;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.cfd.freya.digipay.Models.Item;
import com.cfd.freya.digipay.Models.QRModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREYA on 12-02-2017.
 */

public class AddDbRequest extends StringRequest {

    private static final String REQUEST_URL = "http://foodos.netai.net/logsinsert.php";
    private Map<String, String> params;
    public AddDbRequest(QRModel qrModel, Response.Listener<String> listener) {

        super(Method.POST,REQUEST_URL, listener, null);
        Gson gson=new Gson();
        params=new HashMap<>();
        Type type = new TypeToken<ArrayList<Item>>() {
        }.getType();
        params.put("number",qrModel.getBillId());
        params.put("storename",qrModel.getStoreName());
        params.put("username",qrModel.getUserName());
        params.put("date",qrModel.date);
        params.put("total",qrModel.grandTotal);
        params.put("product",gson.toJson(qrModel.getItemArrayList(),type));

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
