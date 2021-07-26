package com.example.hamiltontevin_ce01.fragment;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 * AddMarkerDataFragment
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce01.AddMarkerDataActivity;
import com.example.hamiltontevin_ce01.R;
import com.example.hamiltontevin_ce01.mapDatabase.DataBaseHelper;
import com.example.hamiltontevin_ce01.modal.LocationData;

import java.io.ByteArrayOutputStream;


public class AddMarkerDataFragment extends Fragment {
    private static final int REQUEST_TAKE_PICTURE = 0x01001;
    private LocationData mLocationData;
    private ImageView mImageDisplay;

    public AddMarkerDataFragment() {
    }

    public static AddMarkerDataFragment newInstance(LocationData data) {

        Bundle args = new Bundle();
        args.putSerializable(AddMarkerDataActivity.LOCATION_DATA_OBJECT,data);
        AddMarkerDataFragment fragment = new AddMarkerDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Fragment setup
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_marker_data,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
          mLocationData =  (LocationData) getArguments().getSerializable(AddMarkerDataActivity.LOCATION_DATA_OBJECT);
        }
        if(getView()!= null) {
            mImageDisplay = getView().findViewById(R.id.displayImage_IV);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        Log.i("result","made it");
        Bundle extras = data.getExtras();
        if(extras != null) {
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if(imageBitmap != null) {
                mLocationData.setImage(bitMapToByteArray(imageBitmap));
                mImageDisplay.setImageBitmap(imageBitmap);
            }
        }
    }
    /**
     * menu setup
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_take_picture){
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(getActivity() != null && getContext() != null) {
                if (photoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(photoIntent, REQUEST_TAKE_PICTURE);
                }
            }
            return true;
        }
        else if(item.getItemId() == R.id.action_save_data){
            if(getView() != null && getActivity() != null){
                EditText et_Title = getView().findViewById(R.id.editText_Title);
                EditText et_Description = getView().findViewById(R.id.editText_Description);
                mLocationData.setTitle(et_Title.getText().toString());
                mLocationData.setDescription(et_Description.getText().toString());
                DataBaseHelper.insertMarkerData(mLocationData);
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * custom methods
     */
    private byte[] bitMapToByteArray(Bitmap data) {
        byte[] byteArray;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        data.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();
       return byteArray;
    }


}


