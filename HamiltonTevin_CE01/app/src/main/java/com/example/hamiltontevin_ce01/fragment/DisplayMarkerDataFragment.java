package com.example.hamiltontevin_ce01.fragment;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 * DisplayMarkerDataFragment
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce01.R;
import com.example.hamiltontevin_ce01.mapDatabase.DataBaseHelper;
import com.example.hamiltontevin_ce01.modal.LocationData;

public class DisplayMarkerDataFragment extends Fragment {
    private static final String MARKER_DATA = "MARKER_DATA";

    public static DisplayMarkerDataFragment newInstance(LocationData locationData) {
        Bundle args = new Bundle();
        args.putSerializable(MARKER_DATA,locationData);
        DisplayMarkerDataFragment fragment = new DisplayMarkerDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_marker_data,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        if(getView() != null){
           if(getArguments() != null) {
               LocationData ld = (LocationData) getArguments().getSerializable(MARKER_DATA);

               TextView tv_title = getView().findViewById(R.id.textView_TitleView);
               TextView tv_description = getView().findViewById(R.id.textView_DescriptionView);
               ImageView iv_displayImage = getView().findViewById(R.id.imageView_capturedImageView);
               if(ld != null) {
                   tv_title.setText(ld.getTitle());
                   tv_description.setText(ld.getDescription());

                   Bitmap bmp = BitmapFactory.decodeByteArray(ld.getImage(), 0, ld.getImage().length);
                   iv_displayImage.setImageBitmap(bmp);
               }
           }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_delete){
            if(getArguments() != null && getActivity() != null){
                LocationData ld = (LocationData) getArguments().getSerializable(MARKER_DATA);
                if(ld != null) {
                    DataBaseHelper.deleteMarker(ld.getID());
                    getActivity().setResult(Activity.RESULT_OK);
                    getActivity().finish();
                }
            }
           return true;
        }
        return false;
    }
}
