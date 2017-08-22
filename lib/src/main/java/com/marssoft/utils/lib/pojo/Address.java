package com.marssoft.utils.lib.pojo;

import com.geo.GeoLocation;

/**
 * Created by Alexey on 06.11.2015.
 */
public class Address {
    private String addressDescr;

    private GeoLocation location;

    public String getAddressDescr() {
        return addressDescr;
    }

    public void setAddressDescr(String addressDescr) {
        this.addressDescr = addressDescr;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressDescr='" + addressDescr + '\'' +
                ", location=" + location +
                '}';
    }
}
