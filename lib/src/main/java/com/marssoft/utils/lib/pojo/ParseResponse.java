package com.marssoft.utils.lib.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexey on 22.10.2015.
 */
public class ParseResponse {
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("objectId")
    String objectId;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "ParseResponse{" +
                "createdAt='" + createdAt + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
