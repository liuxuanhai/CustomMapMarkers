package com.ender.mapmodule.map;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ender.mapmodule.R;
import com.ender.mapmodule.model.MapMarkerObject;
import com.ender.mapmodule.utils.map.MyMapFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ender Erol.
 */
public abstract class BaseMapFragment extends BaseLocationFragment implements MyMapFragment.OnMapReadyListener {

    protected MyMapFragment mapFragment;
    protected GoogleMap map;

    protected List<Marker> markers;
    private List<? extends MapMarkerObject> markerObjects;
    private LatLngBounds bounds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //List of markers.
        markers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getLayout(), container, false);

        //Creates instance of map fragment and inflates it.
        mapFragment = MyMapFragment.newInstance();

        setViews(view);

        super.onCreateView(inflater, container, savedInstanceState);

        return view;
    }

    protected void setViews(View view) {
        getChildFragmentManager().beginTransaction().replace(R.id.frameMap, mapFragment).commit();
    }

    @Override
    public void onMapReady() {

        map = mapFragment.getMap();
        if (map == null) {
            try {
                Thread.sleep(100);
                mapFragment = MyMapFragment.newInstance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(true);

        map.getUiSettings().setMapToolbarEnabled(false);

        map.setMyLocationEnabled(true);
    }

    @LayoutRes
    protected int getLayout() {
        return R.layout.map_fragment;
    }

    /**
     * Moves camera to current location of the device.
     */
    protected void centerInMyPosition() {
        LatLng myPosition = getCurrentCoordinates();
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myPosition).zoom(14).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.animateCamera(cameraUpdate);
    }

    /**
     * Moves the camera to an area that contains all the visible markers.
     *
     * @param addDeviceLocation, Boolean value to add current location of the device in the bounds.
     */
    protected void centerInBounds(boolean addDeviceLocation) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (Marker m : markers) {
            builder.include(m.getPosition());
        }

        if (addDeviceLocation) {
            LatLng myPosition = getCurrentCoordinates();
            builder.include(myPosition);
        }

        try {
            bounds = builder.build();

            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    int padding = 100; //TODO parameterize padding.
                    map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                }
            });
        } catch (IllegalStateException ex) {
            Log.e("ERROR", ex.getMessage());
        }
    }

    /**
     * @param mapMarkerObjects List of objects to add to the map.
     * @param markerIcon       Drawable resource which used as marker icon, 0 indicates no custom icon.
     */
    protected void addMapMarkers(List<? extends MapMarkerObject> mapMarkerObjects, @DrawableRes int markerIcon) {

        markerObjects = mapMarkerObjects;

        for (MapMarkerObject mapMarkerObject : markerObjects) {

            Marker m = map.addMarker(new MarkerOptions().position(mapMarkerObject.getCoordinates()));

            if (markerIcon != 0) {
                m.setIcon(BitmapDescriptorFactory.fromResource(markerIcon));
            }

            markers.add(m);
        }
    }

    public void disableMapGesturse() {
        map.getUiSettings().setScrollGesturesEnabled(false);
    }

    public void disableMapZoom() {
        map.getUiSettings().setZoomControlsEnabled(false);
        map.getUiSettings().setZoomGesturesEnabled(false);
    }

    /**
     * @return List of markers added to the map.
     */
    public List<Marker> getMarkers() {
        return markers;
    }

    /**
     * @return List of the models added to the map.
     */
    public List<? extends MapMarkerObject> getMarkerObjects() {
        return markerObjects;
    }
}