/*
 * Copyright (c) 2014.
 */

package com.geo;



/**
 * Created by Alexey on 08.07.2014.
 */
public class Geometry
{
    private Bounds bounds;
    private Viewport viewport;
    private String locationType;
    private GeoLocation location;

    public Bounds getBounds ()
    {
        return bounds;
    }

    public void setBounds (Bounds bounds)
    {
        this.bounds = bounds;
    }

    public Viewport getViewport ()
    {
        return viewport;
    }

    public void setViewport (Viewport viewport)
    {
        this.viewport = viewport;
    }

    public String getLocationType()
    {
        return locationType;
    }

    public void setLocationType(String locationType)
    {
        this.locationType = locationType;
    }

    public GeoLocation getLocation ()
    {
        return location;
    }

    public void setLocation (GeoLocation location)
    {
        this.location = location;
    }
}
