package com.ender.mapfragment.model;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ender.mapfragment.R;
import com.ender.mapmodule.infowindow.InfoWindowInflater;
import com.ender.mapmodule.map.InfoWindowMapFragment;
import com.ender.mapmodule.model.MapMarkerObject;
import com.ender.mapmodule.utils.distance.DistanceUtils;

/**
 * Created by Ender Erol.
 */
public class MyMarkerInfoWindowInflater extends InfoWindowInflater<MyMarkerInfoWindowInflater.DataProvider> {

    private TextView tvInfoWindowTitle;
    private TextView tvInfoWindowAddress;
    private TextView tvInfoWindowDistance;

    private ViewGroup infoWindow;

    public MyMarkerInfoWindowInflater(Context context, InfoWindowMapFragment mapFragment) {
        super(context, mapFragment);
    }

    @Override
    public void setInfoWindowViews() {

        infoWindow = (ViewGroup) ((Activity) context).getLayoutInflater().inflate(R.layout.my_marker_info_window, null);

        tvInfoWindowDistance = (TextView) infoWindow.findViewById(R.id.tvInfoWindowDistance);
        tvInfoWindowTitle = (TextView) infoWindow.findViewById(R.id.tvInfoWindowTitle);
        tvInfoWindowAddress = (TextView) infoWindow.findViewById(R.id.tvInfoWindowAddress);

        ImageButton routeButton = (ImageButton) infoWindow.findViewById(R.id.btnInfo);

        routeButton.setOnTouchListener(addRouteButton(routeButton,
                ContextCompat.getDrawable(context, R.drawable.pin),
                ContextCompat.getDrawable(context, R.drawable.pin)));

    }

    @Override
    public void setInfoWindowData(DataProvider mapMarkerObject) {
        tvInfoWindowTitle.setText(mapMarkerObject.getTitle());
        tvInfoWindowAddress.setText(mapMarkerObject.getAddress());

        String distanceWithMetric = DistanceUtils.getDistanceStringByCoordinates(mapMarkerObject.getCoordinates());
        tvInfoWindowDistance.setText(distanceWithMetric);
    }

    @Override
    public ViewGroup getInfoWindow() {
        return infoWindow;
    }

    public interface DataProvider extends MapMarkerObject {
        String getTitle();

        String getAddress();
    }

}