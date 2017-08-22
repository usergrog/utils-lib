/*
 * Copyright (c) 2014.
 */

package com.geo;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexey on 08.07.2014.
 */
public class GeoLocation implements Parcelable {
    private double lng;
    private double lat;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public GeoLocation(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public GeoLocation(String address, double lng, double lat) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public GeoLocation() {
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }

    public String getAsText() {
        return lat + "," + lng;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lng);
        dest.writeDouble(this.lat);
        dest.writeString(this.address);
    }

    protected GeoLocation(Parcel in) {
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.address = in.readString();
    }

    public static final Creator<GeoLocation> CREATOR = new Creator<GeoLocation>() {
        @Override
        public GeoLocation createFromParcel(Parcel source) {
            return new GeoLocation(source);
        }

        @Override
        public GeoLocation[] newArray(int size) {
            return new GeoLocation[size];
        }
    };
}