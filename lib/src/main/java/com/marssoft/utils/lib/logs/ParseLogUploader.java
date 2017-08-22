package com.marssoft.utils.lib.logs;

import android.content.Context;

import com.marssoft.utils.lib.pojo.AppLogEvent;
import com.marssoft.utils.lib.volley.NetworkApi;

import java.util.List;

/**
 * Created by Alexey on 22.10.2015.
 */
public class ParseLogUploader implements LogUploader {
    private static final String TAG = ParseLogUploader.class.getSimpleName();

    @Override
    public void upload(Context context) {
        // send logs to server
        final List<AppLogEvent> logs = AppLogEvent.select("serverKey is null", null, "order by localId", AppLogEvent.class);
        if (logs.size() > 0) {
            for (AppLogEvent event : logs) {
                NetworkApi.getInstance(context).sendToParse(event);
            }
        }
    }


}
