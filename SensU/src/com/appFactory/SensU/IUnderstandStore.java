package com.appFactory.SensU;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class IUnderstandStore {
    // The name of the resulting SharedPreferences
    private static final String SHARED_PREFERENCE_NAME = "IUnderstandStore";

    // The SharedPreferences object in which geofences are stored
    private final SharedPreferences mPrefs;

    public IUnderstandStore(Context context) {
        mPrefs =
                context.getSharedPreferences(
                        SHARED_PREFERENCE_NAME,
                        Context.MODE_PRIVATE);

    }

    void setpickupmode(boolean b) {
        Editor editor = mPrefs.edit();
        editor.putBoolean("pickupmode", b);
        editor.commit();

    }

    boolean getpickupmode() {
        return mPrefs.getBoolean("pickupmode", false);
    }

    void setpocketpmode(boolean b) {
        Editor editor = mPrefs.edit();
        editor.putBoolean("pocketpmode", b);
        editor.commit();

    }

    boolean getpocketpmode() {
        return mPrefs.getBoolean("pocketpmode", false);
    }

    void setmagicmode(boolean b) {
        Editor editor = mPrefs.edit();
        editor.putBoolean("magicmode", b);
        editor.commit();
    }

    boolean getmagicmode() {
        return mPrefs.getBoolean("magicmode", false);
    }


}
