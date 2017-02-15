package com.cfd.freya.digipay.fragments;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TransactionRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://foodos.netai.net/retrievelogs.php";
    private Map<String, String> params;

    public TransactionRequest(String username, Response.Listener<String> listener) {
        super(REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("email", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
