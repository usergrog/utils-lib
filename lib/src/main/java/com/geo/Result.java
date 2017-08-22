/*
 * Copyright (c) 2014.
 */

package com.geo;


import java.util.List;

/**
 * Created by Alexey on 08.07.2014.
 */
@SuppressWarnings("UnusedDeclaration")

public class Result {

    private String formattedAddress;

    private List<AddressComponent> addressComponents;

    private List<String> types;

    private Geometry geometry;

    private List<String> postcodeLocality;

    private boolean partialMatch;

    private String placeId;

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public List<AddressComponent> getAddressComponent() {
        return addressComponents;
    }

    public void setAddressComponent(List<AddressComponent> addressComponent) {
        this.addressComponents = addressComponent;
    }

    public List<String> getType() {
        return types;
    }

    public void setType(List<String> type) {
        this.types = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public List<String> getPostcodeLocality() {
        return postcodeLocality;
    }

    public void setPostcodeLocality(List<String> postcodeLocality) {
        this.postcodeLocality = postcodeLocality;
    }

    public boolean isPartialMatch() {
        return partialMatch;
    }

    public void setPartialMatch(boolean partialMatch) {
        this.partialMatch = partialMatch;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}