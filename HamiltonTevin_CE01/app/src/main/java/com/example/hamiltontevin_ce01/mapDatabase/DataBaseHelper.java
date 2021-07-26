// Tevin Hamilton
// JAV2 - 2003
// File - DataBaseHelper
package com.example.hamiltontevin_ce01.mapDatabase;
/*
 * Tevin Hamilton
 * MD3 term 2005
 *  CE01
 * DataBaseHelper
  */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hamiltontevin_ce01.modal.LocationData;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "map.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "markerData";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LATITUDE  = "latitude";
    private static final String COLUMN_LONGITUDE ="longitude";
    private static final String COLUMN_IMAGE = "image";

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_LATITUDE + " REAL, " +
            COLUMN_LONGITUDE + " REAL, " +
            COLUMN_IMAGE + " BOLB);";

    private static SQLiteDatabase mDatabase;
    private static DataBaseHelper mInstance = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private DataBaseHelper(Context _context) {
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);
        mDatabase = getWritableDatabase();
    }
    
    public static DataBaseHelper getInstance(Context _context) {
        if(mInstance == null) {
            mInstance = new DataBaseHelper(_context);
        }
        return mInstance;
    }

    public static void insertMarkerData(LocationData data){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE,data.getTitle());
        cv.put(COLUMN_DESCRIPTION,data.getDescription());
        cv.put(COLUMN_LATITUDE,data.getLatitude());
        cv.put(COLUMN_LONGITUDE,data.getLongitude());
        cv.put(COLUMN_IMAGE,data.getImage());

        mDatabase.insert(TABLE_NAME, null, cv);
    }

    public static void deleteMarker(int markerId){

        mDatabase.delete(TABLE_NAME,COLUMN_ID +" = "+ markerId,null);
    }

    public static Cursor getAllData() {
        return mDatabase.query(TABLE_NAME, null, null, null, null, null, null);
    }
}
