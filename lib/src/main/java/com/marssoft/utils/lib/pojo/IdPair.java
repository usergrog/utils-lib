package com.marssoft.utils.lib.pojo;

/**
 * Created by Alexey on 03.08.2015.
 */
public class IdPair {
    long serverId;
    long localId;

    @Override
    public String toString() {
        return "IdPair{" +
                "localId=" + localId +
                ", serverId=" + serverId +
                '}';
    }

    public long getLocalId() {
        return localId;
    }

    public long getServerId() {
        return serverId;
    }
}
