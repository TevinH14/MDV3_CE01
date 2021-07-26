package com.example.hamiltontevin_ce01.fragment;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 * MapFragment
 */
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.hamiltontevin_ce01.AddMarkerDataActivity;
import com.example.hamiltontevin_ce01.MarkerDataDisplayActvity;
import com.example.hamiltontevin_ce01.modal.LocationData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapFragment extends com.google.android.gms.maps.MapFragment implements OnMapReadyCallback,
GoogleMap.OnInfoWindowClickListener{
    public static final String MAP_TAG = "MMapFragment.Tag";

    private static final int REQUEST_LOCATION_PERMISSIONS = 0X01001;
    private final LocationData mLocationData = new LocationData();
    private GoogleMap mMap;
    private static Cursor mCursor;
    private final HashMap<String,LocationData> mLocationDataHashMap = new HashMap<>();

    public static MapFragment newInstance(Cursor cursor) {
        mCursor = cursor;
        return new MapFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);

        // Get our location manager.
        LocationManager locationManger = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        // Check if we have permissions.
        if(ContextCompat.checkSelfPermission( getActivity() , Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Get our last known location and check if it's a valid location.
            Location lastKnow = locationManger.getLastKnownLocation(
                    LocationManager.NETWORK_PROVIDER);

            if(lastKnow != null){

                // set latitude and longitude members.
                mLocationData.setLatitude(lastKnow.getLatitude());
                mLocationData.setLongitude(lastKnow.getLongitude());
                getMapAsync(this);
            }
        } else {
            // Request permissions if we don't have them.
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    //send user to AddMarkerDataActivity and pass position data
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent addDataIntent = new Intent(getContext(), AddMarkerDataActivity.class);
        addDataIntent.putExtra(AddMarkerDataActivity.LOCATION_DATA_OBJECT,mLocationData);
        startActivity(addDataIntent);
        return true;
    }

    /**
     * Setup map
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapLongClick(LatLng position) {
                mLocationData.setLatitude(position.latitude);
                mLocationData.setLongitude(position.longitude);
                toAddMarkerDataActivity(getContext());
            }
        });
        zoomInCamera();
        addMapMarker();
    }

    // zoom on user current location
    private void zoomInCamera(){
        if(mMap == null){
            return;
        }
        LatLng officeLocation = new LatLng(mLocationData.getLatitude(),mLocationData.getLongitude());
        CameraUpdate cameraMovement = CameraUpdateFactory.newLatLngZoom(officeLocation,16);
        mMap.animateCamera(cameraMovement);
    }
    //add marker data to map and add data to hash map
    private void addMapMarker(){
        if(mMap == null){
            return;
        }
        if(mCursor != null){
            while (mCursor.moveToNext()){

                int id = mCursor.getInt(0);
                String title = mCursor.getString(1);
                String description  =mCursor.getString(2);
                double latitude = mCursor.getDouble(3);
                double longitude = mCursor.getDouble(4);
                byte[] image = mCursor.getBlob(5);

                LocationData ld = new LocationData(id,title,description,latitude,longitude,image);
                MarkerOptions options = new MarkerOptions();
                options.title(title);
                options.snippet(description);

                LatLng markerPosition = new LatLng
                        (latitude,longitude);

                options.position(markerPosition);
                mLocationDataHashMap.put(title,ld);
                mMap.addMarker(options);

            }
            mCursor.moveToFirst();
        }
    }
    //send user to the DMarkerDataDisplayActivity and pass selected data
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onInfoWindowClick(Marker marker){

        LocationData ld = mLocationDataHashMap.get(marker.getTitle());

        Intent displayIntent = new Intent(getContext(), MarkerDataDisplayActvity.class);
        displayIntent.putExtra(MarkerDataDisplayActvity.LOCATION_DATA_OBJECT_DISPLAY,ld);
        startActivity(displayIntent);
    }

    /**
     * custom methods
     */
    private void toAddMarkerDataActivity(Context _context){
        Intent addDataIntent = new Intent(_context, AddMarkerDataActivity.class);
        addDataIntent.putExtra(AddMarkerDataActivity.LOCATION_DATA_OBJECT,mLocationData);
        startActivity(addDataIntent);
    }


}

