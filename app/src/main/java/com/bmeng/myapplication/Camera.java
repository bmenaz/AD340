package com.bmeng.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class Camera {
    String description;
    String url;
    String type;
    LatLng latLng;

    public Camera(String description, String url, String type, LatLng latLng) {
        this.description = description;
        this.url = url;
        this.type = type;
        this.latLng = latLng;
    }
}
