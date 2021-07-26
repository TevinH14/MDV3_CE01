package com.example.hamiltontevin_ce01;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 *Marker Data Display Activity
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.hamiltontevin_ce01.fragment.DisplayMarkerDataFragment;
import com.example.hamiltontevin_ce01.modal.LocationData;

public class MarkerDataDisplayActvity extends AppCompatActivity {
    public static final String LOCATION_DATA_OBJECT_DISPLAY = "location data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_data_display_actvity);
        LocationData locationData =  null;
        if(getIntent() != null){
            Intent startingIntent = getIntent();
            locationData = (LocationData) startingIntent.getSerializableExtra(LOCATION_DATA_OBJECT_DISPLAY);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout_ContainerDisplayData, DisplayMarkerDataFragment
                        .newInstance(locationData))
                .commit();

    }
    /**
     * Setup Option menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_marker_display, menu);
        return true;
    }
}
