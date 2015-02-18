package com.appFactory.SensU;

/*
 * the global class to hold values that are required all over the app..like static vars
 * 
 */

import android.app.Application;
import android.content.Context;


public class GlobalDataApplication extends Application {


    static private GlobalDataApplication gda = null;

    /* sensor data */
    int pitch = 0; // for accelerometer
    int azimuth = 0; // for accelerometer
    int roll = 0; // for accelerometer
    int pitchAfterCall = 0;

    boolean isIncoming = false;
    int proxySensor = 1, proxyAfterCall = 1;

    public GlobalDataApplication() {
        gda = this;
        // TODO Auto-generated constructor stub
    }

    public static Context getInstance() {
        return gda;
    }


}
