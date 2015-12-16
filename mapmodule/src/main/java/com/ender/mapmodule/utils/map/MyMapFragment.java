package com.ender.mapmodule.utils.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class MyMapFragment extends SupportMapFragment {

    public MyMapFragment() {
        super();
    }

    public static MyMapFragment newInstance() {
        return new MyMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View v = super.onCreateView(arg0, arg1, arg2);
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnMapReadyListener) {
            ((OnMapReadyListener) fragment).onMapReady();
        }
        return v;
    }

    /**
     * Listener interface to tell when the map is ready
     */
    public interface OnMapReadyListener {
        void onMapReady();
    }
}