package com.cfd.freya.digipay.fragments;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by FREYA on 12-02-2017.
 */

public class CompletionRequest extends StringRequest {
    private static final String REQUEST_URL = "http://foodos.netai.net/sendingtransactionstatus.php";
    private Map<String, String> params;

    public CompletionRequest(String id,Response.Listener<String> listener) {
        super(Method.POST,REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
