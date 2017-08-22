package com.marssoft.utils.lib.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Alexey on 22.10.2015.
 */
public class MyStringRequest extends StringRequest {
    private byte[] mBody;
    private Map<String, String> mHeaders;
    public MyStringRequest(int post, String url, Map<String, String> headers, byte[] body, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(post, url, listener, errorListener);
        mBody = body;
        mHeaders = headers;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders;
    }


    @Override
    public byte[] getBody() throws AuthFailureError {
        return mBody;
    }
}
