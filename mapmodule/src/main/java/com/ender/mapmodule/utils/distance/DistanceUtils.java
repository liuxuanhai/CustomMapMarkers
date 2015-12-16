package com.ender.mapmodule.utils.distance;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ender Erol.
 */
public class DistanceUtils {

    public static double getDistance(double latitude, double longitude) {

        double currentLatitude = 38.005773;
        double currentLongitude = -1.138576;

        //Device location
        Location currentLocation = new Location("point A");
        currentLocation.setLatitude(currentLatitude);
        currentLocation.setLongitude(currentLongitude);

        //Object Location
        Location modelLocation = new Location("point B");
        modelLocation.setLatitude(latitude);
        modelLocation.setLongitude(longitude);

        //Distance
        return currentLocation.distanceTo(modelLocation);
    }

    public static String getDistanceStringByCoordinates(LatLng coordinates) {

        double distance = getDistance(coordinates.latitude, coordinates.longitude);

        if (distance > 1000) {
            distance = distance / 1000;
            String s = String.format("%.1f", distance);
            return s + " km";
        } else {
            return String.format("%.1f", distance) + " m";
        }
    }
}
