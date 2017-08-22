/*
 * Copyright (c) 2014.
 */

package com.geo;

import java.util.List;

/**
 * Created by Alexey on 08.07.2014.
 */
public class AddressComponent {

    private String longName;

    private String shortName;

    private List<String> type;

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }
}

