package com.ender.mapfragment.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ender.mapfragment.R;
import com.ender.mapfragment.view.ExampleMapFragment;

/**
 * Created by Ender Erol.
 */
public class MapActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        getSupportFragmentManager().beginTransaction().replace(R.id.mapFrame, new ExampleMapFragment()).commit();

    }
}