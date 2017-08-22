/*
 * Copyright (c) 2014.
 */

package com.geo;


/**
 * Created by Alexey on 08.07.2014.
 */
public class Viewport
{
    private Southwest southwest;
    private Northeast northeast;

    public Southwest getSouthwest ()
    {
        return southwest;
    }

    public void setSouthwest (Southwest southwest)
    {
        this.southwest = southwest;
    }

    public Northeast getNortheast ()
    {
        return northeast;
    }

    public void setNortheast (Northeast northeast)
    {
        this.northeast = northeast;
    }
}

