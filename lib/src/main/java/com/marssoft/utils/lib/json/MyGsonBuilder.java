package com.marssoft.utils.lib.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Alexey on 22.07.2015.
 */
public class MyGsonBuilder {
    private static Gson instance;

    public static synchronized Gson getInstance() {
        if (instance == null) {
            instance = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // will use with our server
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
        }
        return instance;
    }
}
