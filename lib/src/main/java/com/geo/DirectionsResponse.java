/*
 * Copyright (c) 2014.
 */

package com.geo;


/**
 * Created by Alexey on 03.07.2014.
 */
public class DirectionsResponse
{
    private String status;
    private Route route;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Route getRoute ()
    {
        return route;
    }

    public void setRoute (Route route)
    {
        this.route = route;
    }
}