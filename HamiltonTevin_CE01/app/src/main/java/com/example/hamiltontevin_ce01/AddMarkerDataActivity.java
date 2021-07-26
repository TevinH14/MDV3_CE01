package com.example.hamiltontevin_ce01;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 * AddMarkerDataActivity
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.example.hamiltontevin_ce01.fragment.AddMarkerDataFragment;
import com.example.hamiltontevin_ce01.modal.LocationData;

public class AddMarkerDataActivity extends AppCompatActivity {
    public static final String LOCATION_DATA_OBJECT = "location data";
    private LocationData mLocationData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker_data);
        if(getIntent() != null){
            Intent startingIntent = getIntent();
            mLocationData = (LocationData) startingIntent.getSerializableExtra(LOCATION_DATA_OBJECT);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout_dataContainer, AddMarkerDataFragment.newInstance(mLocationData))
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout_dataContainer);
        if(fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
    /**
     * Setup Option menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add_marker_data, menu);
        return true;
    }


}
