package com.example.appvendas.Helpers.Singleton;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.appvendas.Entity.User;

public class PreferencesSingleton {

    private static PreferencesSingleton INSTANCE;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private final String sharedFile = "com.example.appvendas.loggedUserData";


    private PreferencesSingleton(Application application) {
        mSharedPreferences = application.getSharedPreferences(sharedFile, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        resetPreferences();
    }

    public static PreferencesSingleton getInstance(Application application) {
        if(INSTANCE == null)
            INSTANCE = new PreferencesSingleton(application);
        return INSTANCE;
    }

    public void storeUserData(User user) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString("userName", user.getUserName());
        mEditor.putString("userEmail", user.getUserEmail());
        mEditor.putString("userGender", user.getUserGender());
        mEditor.putString("userPhoneNumber", user.getUserPhoneNumber());
        mEditor.putString("userBirthday", user.getUserBirthday());
        mEditor.putBoolean("isLoggedIn", true);
        mEditor.apply();
    }

    public boolean isLoggedIn() {
        return mSharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void resetPreferences() {
        mEditor.clear();
        mEditor.apply();
    }
}
