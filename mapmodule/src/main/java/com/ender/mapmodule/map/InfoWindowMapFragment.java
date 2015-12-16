package com.ender.mapmodule.map;

import android.content.Context;
import android.view.View;

import com.ender.mapmodule.R;
import com.ender.mapmodule.utils.map.MapWrapperLayout;
import com.ender.mapmodule.utils.map.MyMapFragment;
import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Ender Erol.
 */
public abstract class InfoWindowMapFragment extends BaseMapFragment implements MyMapFragment.OnMapReadyListener {

    private MapWrapperLayout mapWrapperLayout;

    @Override
    protected void setViews(View view) {
        super.setViews(view);
        //Gets the instance of mapWrapperLayout, which permits to add custom info windows.
        mapWrapperLayout = (MapWrapperLayout) view.findViewById(R.id.mapWrapperLayout);
    }

    @Override
    public void onMapReady() {
        super.onMapReady();

        //Starts wrapperLayout.
        mapWrapperLayout.init(map, getPixelsFromDp(getActivity(), 50));

        //InfoWindowAdapter provided by subclass.
        map.setInfoWindowAdapter(setInfoWindowAdapter());

        //InfoWindowClickListener provided by subclass.
        map.setOnInfoWindowClickListener(setOnInfoWindowClickListener());
    }

    @Override
    protected int getLayout() {
        return R.layout.info_window_map_fragment;
    }

    public abstract GoogleMap.InfoWindowAdapter setInfoWindowAdapter();

    public abstract GoogleMap.OnInfoWindowClickListener setOnInfoWindowClickListener();

    private int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public MapWrapperLayout getMapWrapperLayout() {
        return mapWrapperLayout;
    }
}