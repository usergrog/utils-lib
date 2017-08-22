/*
 * Copyright (c) 2014.
 */

package com.geo;


import java.util.List;

/**
 * Created by Alexey on 03.07.2014.
 */
public class Leg
{
    private Duration duration;

    private Distance distance;

    private EndLocation endLocation;

    private String startAddress;

    private String endAddress;

    private StartLocation startLocation;

    private List<Step> step;

    public Duration getDuration ()
    {
        return duration;
    }

    public void setDuration (Duration duration)
    {
        this.duration = duration;
    }

    public Distance getDistance ()
    {
        return distance;
    }

    public void setDistance (Distance distance)
    {
        this.distance = distance;
    }

    public EndLocation getEndLocation()
    {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation)
    {
        this.endLocation = endLocation;
    }

    public String getStartAddress()
    {
        return startAddress;
    }

    public void setStartAddress(String startAddress)
    {
        this.startAddress = startAddress;
    }

    public String getEndAddress()
    {
        return endAddress;
    }

    public void setEndAddress(String endAddress)
    {
        this.endAddress = endAddress;
    }

    public StartLocation getStartLocation()
    {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation)
    {
        this.startLocation = startLocation;
    }

    public List<Step> getStep ()
    {
        return step;
    }

    public void setStep (List<Step> step)
    {
        this.step = step;
    }
}