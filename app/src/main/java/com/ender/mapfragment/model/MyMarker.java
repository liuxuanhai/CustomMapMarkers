package com.ender.mapfragment.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ender Erol.
 */
public class MyMarker implements MyMarkerInfoWindowInflater.DataProvider {

    private String title;
    private String address;
    private double latitude;
    private double longitude;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public LatLng getCoordinates() {
        return new LatLng(getLatitude(), getLongitude());
    }

}