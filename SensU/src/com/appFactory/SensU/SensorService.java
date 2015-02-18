package com.appFactory.SensU;
/**
 * get sensor values from accelerometer and proximity sensor..
 *
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class SensorService extends Service implements SensorEventListener {

    //private int sensor = SensorManager.SENSOR_ORIENTATION;
    static SensorManager sensorManager;
    //GlobalDataApplication globaldata=null;
    static AudioManager am = null;
    static SensorEventListener c = null;
    static private Sensor accelerometer, proxy;
    private static int Initvolume = 0;
    public GlobalDataApplication gdata = null;
    public IUnderstandStore mPrefs = null;

    static void registerAllSensors() {
        sensorManager.registerListener(c, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(c, proxy, SensorManager.SENSOR_DELAY_NORMAL);
    }

    static void unregisterAllSensors() {
        sensorManager.unregisterListener(c);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        try {
            //	 Log.d("Ringer", "inside service..");
            c = this;
            mPrefs = new IUnderstandStore(this.getApplicationContext());
            gdata = (GlobalDataApplication) GlobalDataApplication.getInstance();
            am = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

            //globaldata=(GlobalDataApplication)getApplication();
            Initvolume = am.getRingerMode();
            //Log.d("change","Init Ringer mode: "+Initvolume);

            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            proxy = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {

                        if ((mPrefs.getpickupmode()) || (mPrefs.getmagicmode()) || (mPrefs.getpocketpmode())) {
                            SensorService.registerAllSensors();
                            Toast.makeText(getApplicationContext(), "Services enabled!", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        if ((mPrefs.getpickupmode()) || (mPrefs.getmagicmode()) || (mPrefs.getpocketpmode())) {
                            SensorService.unregisterAllSensors();
                            Toast.makeText(getApplicationContext(), "Services disabled!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), "" + am.getRingerMode(), Toast.LENGTH_SHORT).show();
                }
            };
            IntentFilter filter = new IntentFilter(
                    AudioManager.RINGER_MODE_CHANGED_ACTION);
            registerReceiver(receiver, filter);

            registerAllSensors();
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        try {

            if (am.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    if (gdata != null && event != null) {
                        gdata.azimuth = Math.round(event.values[0]);
                        gdata.pitch = Math.round(event.values[1]);
                        gdata.roll = Math.round(event.values[2]);
                    }

                    //	Log.d("sensor","is stable : "+SensUHomeActivity.globalData.stable);

                    if (gdata != null && gdata.isIncoming && gdata.proxyAfterCall > 0) {

                        //this if for phone on table & picked up in hand and making volume low

                        synchronized (this) {


                            int i = Math.abs(gdata.pitchAfterCall - gdata.pitch);

                            if (i > 6) {
                                //if(gdata.isIncoming)
                                //am.adjustStreamVolume(AudioManager.STREAM_RING,AudioManager.ADJUST_LOWER,0);
                                if (mPrefs.getpickupmode()) {
                                    //Initvolume = am.getStreamVolume(AudioManager.STREAM_RING);
                                    Initvolume = am.getRingerMode();
                                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                }

                                //SensUHomeActivity.globalData.isIncoming=false;
                                gdata.pitchAfterCall = 0;

                            }
                        }

                    }
                    if (gdata != null && gdata.isIncoming) {
                        synchronized (this) {

                            if (gdata.proxySensor > 0 && gdata.proxyAfterCall == 0) {
                                //this if for from pocket to outside

                                //am.setStreamVolume(AudioManager.STREAM_RING,volume/2,AudioManager.FLAG_PLAY_SOUND);
                                if (mPrefs.getpocketpmode()) {
                                    Initvolume = am.getRingerMode();
                                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

                                }
                            }
                            if (gdata.proxySensor == 0 && gdata.proxyAfterCall > 0) {
                                Log.d("change", "inside accel");
                                //this if for flipping on table
                                //int volume = am.getStreamVolume(AudioManager.STREAM_RING);
                                //am.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_PLAY_SOUND);
                                if (mPrefs.getmagicmode()) {
                                    Initvolume = am.getRingerMode();
                                    am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                }

                            }
                        }
                    }

                    //for(int i =0;i<event.values.length;i++)
                }

                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

                    //lock screen when we put in pocket
                    if (gdata != null && !gdata.isIncoming) {
                        if (Math.round(event.values[0]) == 0) {


                        }
                    }


                    if (gdata != null)
                        gdata.proxySensor = Math.round(event.values[0]);
                }
            }
        } catch (Exception e) {

        }

    }


}
