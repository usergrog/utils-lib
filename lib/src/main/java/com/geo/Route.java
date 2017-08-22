/*
 * Copyright (c) 2014.
 */

package com.geo;



/**
 * Created by Alexey on 03.07.2014.
 */
@SuppressWarnings("UnusedDeclaration")

public class Route
{

    private String summary;

    private Bounds bounds;

    private Leg leg;

    private String copyrights;

    private OverviewPolyline overviewPolyline;

    public String getSummary ()
    {
        return summary;
    }

    public void setSummary (String summary)
    {
        this.summary = summary;
    }

    public Bounds getBounds ()
    {
        return bounds;
    }

    public void setBounds (Bounds bounds)
    {
        this.bounds = bounds;
    }

    public Leg getLeg ()
    {
        return leg;
    }

    public void setLeg (Leg leg)
    {
        this.leg = leg;
    }

    public String getCopyrights ()
    {
        return copyrights;
    }

    public void setCopyrights (String copyrights)
    {
        this.copyrights = copyrights;
    }

    public OverviewPolyline getOverviewPolyline()
    {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline)
    {
        this.overviewPolyline = overviewPolyline;
    }
}