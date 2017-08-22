/*
 * Copyright (c) 2014.
 */

package com.geo;


import java.util.List;

/**
 * Created by Alexey on 08.07.2014.
 */
public class GeocodeResponse {

    private List<Result> results;

    private String status;

    private String errorMessage;

    public List<Result> getResult() {
        return results;
    }

    public void setResult(List<Result> result) {
        this.results = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}