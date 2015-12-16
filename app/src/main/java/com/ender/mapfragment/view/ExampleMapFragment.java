package com.ender.mapfragment.view;

import com.ender.mapfragment.R;
import com.ender.mapfragment.model.MyMarker;
import com.ender.mapfragment.model.MyMarkerInfoWindowInflater;
import com.ender.mapmodule.map.ContainerMapFragment;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ender Erol.
 */
public class ExampleMapFragment extends ContainerMapFragment {

    @Override
    public void onMapReady() {
        super.onMapReady();

        addMapMarkers(getSampleMarkers(), R.drawable.pointer);

        centerInBounds(true);

    }

    @Override
    public int getMapIcon() {
        return R.drawable.bt_map;
    }

    @Override
    public int getListIcon() {
        return R.drawable.bt_list;
    }

    @Override
    public GoogleMap.InfoWindowAdapter setInfoWindowAdapter() {
        return new MyMarkerInfoWindowInflater(getActivity(), this);
    }

    private List<MyMarker> getSampleMarkers() {
        MyMarker myMarker = new MyMarker();
        myMarker.setTitle("Catedral de Murcia");
        myMarker.setAddress("Plaza del Cardenal Belluga, 1, 30001 Murcia, Spain");
        myMarker.setLatitude(37.984047);
        myMarker.setLongitude(-1.128575);

        MyMarker myMarker2 = new MyMarker();
        myMarker2.setTitle("Teatro Romea");
        myMarker2.setAddress("Plaza de Julián Romea, s/n, 30001 Murcia, Murcia, Spain");
        myMarker2.setLatitude(37.987085);
        myMarker2.setLongitude(-1.130717);

        List<MyMarker> myMarkers = new ArrayList<>();
        myMarkers.add(myMarker);
        myMarkers.add(myMarker2);

        return myMarkers;
    }
}
