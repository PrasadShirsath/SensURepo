package com.appFactory.SensU;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.prasad.SensU.R;
import com.prasad.viewpager.ViewPagerStyle1Activity;

public class MainActivity extends Activity {
    public static MainActivity me;
    GlobalDataApplication gdata = null;
    IUnderstandStore mPrefs = null;
    private ToggleButton switch_pickmode = null;
    private ToggleButton switch_pocketmode = null;
    private ToggleButton switch_magicmode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        me = this;
        mPrefs = new IUnderstandStore(this);
        gdata = (GlobalDataApplication) GlobalDataApplication.getInstance();

        startService(new Intent(this, SensorService.class));
        switch_pickmode = (ToggleButton) findViewById(R.id.Pick_Up);
        switch_pocketmode = (ToggleButton) findViewById(R.id.Pocket_mode);
        switch_magicmode = (ToggleButton) findViewById(R.id.Magic_mode);

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(0, 0, 0, 0)));

        switch_pickmode.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    registerSensor();
                    mPrefs.setpickupmode(true);
                } else {
                    mPrefs.setpickupmode(false);
                    unregisterSensor();
                }
                // TODO Auto-generated method stub
            }

        });

        switch_pocketmode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    registerSensor();
                    mPrefs.setpocketpmode(true);

                } else {
                    mPrefs.setpocketpmode(false);
                    unregisterSensor();
                }
                // TODO Auto-generated method stub
            }
        });
        switch_magicmode.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    registerSensor();
                    mPrefs.setmagicmode(true);
                } else {
                    mPrefs.setmagicmode(false);
                    unregisterSensor();
                }
                // TODO Auto-generated method stub
            }
        });

        if (!new AppDataStore(this).getFirstUsed()) {
            startActivity(new Intent(this, ViewPagerStyle1Activity.class));

        }


    }


    protected void registerSensor() {
        if ((!mPrefs.getpickupmode()) && (!mPrefs.getmagicmode()) && (!mPrefs.getpocketpmode())) {
            SensorService.registerAllSensors();
            //Toast.makeText(this,"Services enabled!", Toast.LENGTH_SHORT).show();
        }

    }


    protected void unregisterSensor() {
        if ((!mPrefs.getpickupmode()) && (!mPrefs.getmagicmode()) && (!mPrefs.getpocketpmode())) {
            SensorService.unregisterAllSensors();
            //Toast.makeText(this,"All services disabled!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        switch_pickmode.setChecked(mPrefs.getpickupmode());
        switch_pocketmode.setChecked(mPrefs.getpocketpmode());
        switch_magicmode.setChecked(mPrefs.getmagicmode());
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        // Handle item selection
        switch (item.getItemId()) {

            // Request to clear the geofence1 settings in the UI
            case R.id.action_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.prasad.SensU");
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this cool App!");
                startActivity(Intent.createChooser(intent, "Share"));

                return true;

            case R.id.action_rate:
                Intent intentrate = new Intent(Intent.ACTION_VIEW);
                intentrate.setData(Uri.parse("market://details?id=com.prasad.SensU"));
                startActivity(intentrate);

                return true;

            // Pass through any other request
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
