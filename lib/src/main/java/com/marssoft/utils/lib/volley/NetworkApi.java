package com.marssoft.utils.lib.volley;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.marssoft.utils.lib.json.MyGsonBuilder;
import com.marssoft.utils.lib.logs.Log;
import com.marssoft.utils.lib.pojo.AppLogEvent;
import com.marssoft.utils.lib.pojo.ParseResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexey on 22.10.2015.
 */
public class NetworkApi {

    private static final String TAG = NetworkApi.class.getSimpleName();
    private static NetworkApi instance;
    public String mParseAppId = "kxOeR7hTRes9GJNBXUkX1rBTDtvlZPypI4yPbdLY";
    public String mParseApiKey = "qmTN3TWOGLdzwDRrwIdCamgcAX4kzDvdogiT3gdA";
    RequestQueue queue;
    Context mContext;

    private NetworkApi(Context context) {
        mContext = context;
        Volley.newRequestQueue(context);
        queue = Volley.newRequestQueue(context);
    }

    public static NetworkApi getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkApi(context.getApplicationContext());
        }
        return instance;
    }

    public void sendToParse(final AppLogEvent logEvent) {
        String url = "https://api.parse.com/1/classes/AppLog";

        final Gson gson = MyGsonBuilder.getInstance();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Parse-Application-Id", mParseAppId);
        headers.put("X-Parse-REST-API-Key", mParseApiKey);

        StringRequest stringRequest = new MyStringRequest(Request.Method.POST,
                url,
                headers,
                gson.toJson(logEvent).getBytes(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "file info " + response);
                        ParseResponse parseResponse = gson.fromJson(response, ParseResponse.class);
                        logEvent.setServerKey(parseResponse.getObjectId());
                        logEvent.update();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error sending logs " + error.toString());
            }
        });
        queue.add(stringRequest);
    }
}
