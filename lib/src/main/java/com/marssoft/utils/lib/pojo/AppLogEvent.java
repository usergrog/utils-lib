package com.marssoft.utils.lib.pojo;

import android.os.Parcel;

import com.marssoft.utils.lib.UtilsLibConfig;

/**
 * Created by Alexey on 17.07.2015.
 */
public class AppLogEvent extends Pojo {
    public static final String TABLE_NAME = "AppLogs";
    public static final int MAX_LENGHT = 255;
    String tag;
    String title;
    String serverKey;
    String deviceName;
    String deviceId;
    int appVersionCode;

    public AppLogEvent() {
        deviceId = UtilsLibConfig.deviceId;
        deviceName = UtilsLibConfig.deviceName;
        appVersionCode = UtilsLibConfig.appVersionCode;
    }

    public int getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(int appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        int maxLength = title.length() > MAX_LENGHT ? MAX_LENGHT : title.length();
        return title.substring(0, maxLength);
    }

    public void setTitle(String title) {
        int maxLength = title.length() > MAX_LENGHT ? MAX_LENGHT : title.length();
        this.title = title.substring(0, maxLength);
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        int maxLength = title.length() > MAX_LENGHT ? MAX_LENGHT : title.length();
        return "AppLogEvent{" +
                "appVersionCode=" + appVersionCode +
                ", tag='" + tag + '\'' +
                ", title='" + title.substring(0, maxLength) + '\'' +
                ", serverKey='" + serverKey + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                "} " + super.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.tag);
        dest.writeString(this.title);
        dest.writeString(this.serverKey);
        dest.writeString(this.deviceName);
        dest.writeString(this.deviceId);
        dest.writeInt(this.appVersionCode);
    }

    protected AppLogEvent(Parcel in) {
        super(in);
        this.tag = in.readString();
        this.title = in.readString();
        this.serverKey = in.readString();
        this.deviceName = in.readString();
        this.deviceId = in.readString();
        this.appVersionCode = in.readInt();
    }

    public static final Creator<AppLogEvent> CREATOR = new Creator<AppLogEvent>() {
        public AppLogEvent createFromParcel(Parcel source) {
            return new AppLogEvent(source);
        }

        public AppLogEvent[] newArray(int size) {
            return new AppLogEvent[size];
        }
    };
}

