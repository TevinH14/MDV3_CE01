package com.example.hamiltontevin_ce01.modal;
/*
 * Tevin Hamilton
 * MD3 term 2005
 *  CE01
 * LocationData
 */
import java.io.Serializable;

public class LocationData implements Serializable {
    private int mID;
    private  String mTitle;
    private  String mDescription;
    private  double mLatitude;
    private  double mLongitude;
    private  byte[] mImage;


    public LocationData(int mID, String mTitle, String mDescription, double mLatitude, double mLongitude, byte[] mImage) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mImage = mImage;
    }

    public LocationData() {

    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public byte[] getImage() {
        return mImage;
    }
    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }
    public  void setTitle(String data){
        this.mTitle = data;
    }
    public  void setDescription(String data){
        mDescription = data;
    }
    public  void setLatitude(double data){
        mLatitude = data;
    }
    public  void setLongitude(double data){
        mLongitude = data;
    }
    public  void setImage(byte[] data){
        mImage = data;
    }
}

