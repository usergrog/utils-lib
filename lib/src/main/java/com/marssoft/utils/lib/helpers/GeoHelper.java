package com.marssoft.utils.lib.helpers;

import android.os.AsyncTask;

import com.geo.GeocodeResponse;
import com.geo.Geometry;
import com.geo.GeoLocation;
import com.geo.Result;
import com.google.gson.Gson;
import com.marssoft.utils.lib.json.MyGsonBuilder;
import com.marssoft.utils.lib.logs.Log;
import com.marssoft.utils.lib.pojo.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Alexey on 10.09.2014.
 */
public class GeoHelper {
    public static final String REVERSE_GEOCODE_API_URL
            = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%s&sensor=false";

    public static final String REVERSE_GEOCODE_API_URL_UKRAINE
            = "http://maps.googleapis.com/maps/api/geocode/json?latlng=%s&sensor=false&region=ua&language=uk";

    public static final String GEOCODE_API_URL
            = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false";

    public static final String GEOCODE_API_URL_UKRAINE
            = "http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false&region=ua&language=uk&components=country:UA";

    private static final String TAG = GeoHelper.class.getSimpleName();

    public static void geocoding(final String address, final GeocodingCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    GeocodeResponse response = null;
                    response = geocodingRequest(String.format(GEOCODE_API_URL, URLEncoder.encode(address, "UTF-8")));
                    if (response != null && response.getStatus().equals("OK")) {
                        for (Result result : response.getResult()) {
                            if (result != null) {
                                Geometry geometry = result.getGeometry();
                                if (geometry != null) {
                                    GeoLocation location = geometry.getLocation();
                                    callback.onResult(location);
                                    return null;
                                }
                            }
                        }
                    }
                    callback.onError();
                } catch (IOException e) {
                    Log.e(TAG, "error", e);
                }
                return null;
            }
        }.execute();
    }

    public static void reverseGeocoding(final GeoLocation location, final ReverseGeocodingCallback callback) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                GeocodeResponse geocodeResponse = null;
                try {
                    geocodeResponse = geocodingRequest(String.format(REVERSE_GEOCODE_API_URL, location.getAsText()));
                    if (geocodeResponse != null) {
                        if (geocodeResponse.getStatus() != null) {
                            if (geocodeResponse.getStatus().equals("OK")
                                    && geocodeResponse.getResult() != null) {
                                Result result = geocodeResponse.getResult().get(0);
                                callback.onResult(result.getFormattedAddress());
                                return null;
                            } else {
                                callback.onError(geocodeResponse.getErrorMessage());
                            }
                        }
                    } else {
                        callback.onError("Response is null");
                    }
                } catch (IOException e) {
                    Log.e(TAG, "error", e);
                    callback.onError(e.toString());
                }
                return null;
            }
        }.execute();
    }

    public static GeocodeResponse geocodingRequest(String urlStr) throws IOException {
        // http://maps.googleapis.com/maps/api/geocode/json?address=kyiv&sensor=false&region=ua&language=uk
        GeocodeResponse geocodeResponse = null;
        StringBuilder answer = new StringBuilder("");
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("User-Agent", "");
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        connection.setDoInput(true);
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = "";
        while ((line = rd.readLine()) != null) {
            answer.append(line);
        }
        Gson gson = MyGsonBuilder.getInstance();

        try {
            geocodeResponse = gson.fromJson(answer.toString(), GeocodeResponse.class);
        } catch (Exception e) {
            Log.e(TAG, "error json " + answer, e);
        }
        return geocodeResponse;
    }

    public static void geocode(List<Address> addresses, String text) throws IOException {
        GeocodeResponse response = geocodingRequest(text);
        if (response != null && response.getStatus().equals("OK")) {
            addresses.clear();
            for (Result result : response.getResult()) {
                if (result != null) {
                    Geometry geometry = result.getGeometry();
                    if (geometry != null) {
                        GeoLocation location = geometry.getLocation();
                        if (location != null) {
                            Address address = new Address();
                            address.setAddressDescr(result.getFormattedAddress());
                            address.setLocation(location);
                            addresses.add(address);
                        }
                    }
                }
            }
        }
    }

    public static GeocodeResponse reverseGeocoding(final GeoLocation location) {
        GeocodeResponse geocodeResponse = null;
        try {
            geocodeResponse = geocodingRequest(String.format(REVERSE_GEOCODE_API_URL, location.getAsText()));
        } catch (IOException e) {
            Log.e(TAG, "error", e);
        }
        return geocodeResponse;
    }

    public interface ReverseGeocodingCallback {
        void onResult(String address);

        void onError(String s);
    }

    public interface GeocodingCallback {
        void onResult(GeoLocation latLng);

        void onError();
    }

}
