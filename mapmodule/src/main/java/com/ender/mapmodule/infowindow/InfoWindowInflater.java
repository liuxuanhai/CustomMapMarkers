package com.ender.mapmodule.infowindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ender.mapmodule.map.InfoWindowMapFragment;
import com.ender.mapmodule.model.MapMarkerObject;
import com.ender.mapmodule.utils.map.OnInfoWindowElemTouchListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Ender Erol.
 */
public abstract class InfoWindowInflater<T extends MapMarkerObject> implements GoogleMap.InfoWindowAdapter {

    protected Context context;
    protected InfoWindowMapFragment mapFragment;

    private double latitude;
    private double longitude;

    private OnInfoWindowElemTouchListener infoWindowRouteTouchListener;

    public InfoWindowInflater(Context context, InfoWindowMapFragment mapFragment) {
        this.context = context;
        this.mapFragment = mapFragment;
        setInfoWindowViews();
    }

    public abstract void setInfoWindowData(T mapMarkerObject);

    public abstract void setInfoWindowViews();

    public abstract ViewGroup getInfoWindow();

    protected OnInfoWindowElemTouchListener addRouteButton(ImageView routeButton, Drawable bgDrawableNormal,
                                                           Drawable bgDrawablePressed) {

        latitude = mapFragment.getCurrentCoordinates().latitude;
        longitude = mapFragment.getCurrentCoordinates().longitude;

        infoWindowRouteTouchListener = new OnInfoWindowElemTouchListener(routeButton, bgDrawableNormal, bgDrawablePressed) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=" + latitude + "," + longitude +
                                "&daddr=" + marker.getPosition().latitude + "," + marker.getPosition().longitude));
                context.startActivity(intent);
            }
        };
        return infoWindowRouteTouchListener;

    }

    @Override
    public View getInfoWindow(Marker marker) {

        //Position of marker in the array.
        int markerPosition = mapFragment.getMarkers().indexOf(marker);

        //MapMarkerObject that corresponds to the marker.
        MapMarkerObject mapMarkerObject = mapFragment.getMarkerObjects().get(markerPosition);

        //Sets fields of info window
        setInfoWindowData((T) mapMarkerObject); //TODO avoid casting

        // We must call this to set the current marker and infoWindow references to the MapWrapperLayout
        mapFragment.getMapWrapperLayout().setMarkerWithInfoWindow(marker, getInfoWindow());

        if (infoWindowRouteTouchListener != null) {
            infoWindowRouteTouchListener.setMarker(marker);
        }

        return getInfoWindow();
    }

    @Override
    public View getInfoContents(Marker marker) {
        return getInfoWindow(marker);
    }

}
