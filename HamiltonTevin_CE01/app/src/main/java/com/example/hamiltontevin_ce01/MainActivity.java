package com.example.hamiltontevin_ce01;
/*
 * Tevin Hamilton
 * MD3 term 2005
 * CE01
 * Main Activity
 */
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.example.hamiltontevin_ce01.fragment.MapFragment;
import com.example.hamiltontevin_ce01.mapDatabase.DataBaseHelper;

public class MainActivity extends AppCompatActivity {
    /**
     * Setup MainActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Cursor cursor = getDataBaseData();
        MapFragment frag = MapFragment.newInstance(cursor);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_MapContainer,frag, MapFragment.MAP_TAG)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor cursor = getDataBaseData();
        MapFragment frag = MapFragment.newInstance(cursor);
        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_MapContainer,frag, MapFragment.MAP_TAG)
                .commit();
    }

    /**
     * Setup Option menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }
    /**
     * custom method
     */
    //get database data to be used to pass to map fragment
    private Cursor getDataBaseData(){
        DataBaseHelper helper =  DataBaseHelper.getInstance(this);
        Cursor cursor = null;
        if(helper != null) {
            cursor = DataBaseHelper.getAllData();
            Log.i("count", String.valueOf(cursor.getCount()));
        }
        return cursor;
    }
}
