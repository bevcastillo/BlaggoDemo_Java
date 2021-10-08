package com.beverlycastillo.blaggodemo.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Data {

    public static SharedPreferences sharedPreferences;
    public static final String ERROR_COUNT = "error_count";

    public static void setSharedPreferences(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    //  Setter
    public static void insert(String key, String value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void insert(String key, int value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void insert(String key, boolean value){
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //  Getter
    public static String getString(String key){
        return getString(key, "");
    }

    public static String getString(String key, String defaultString){
        return Data.getSharedPreferences().getString(key, defaultString);
    }

    public static int getInt(String key){
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultInt){
        return Data.getSharedPreferences().getInt(key, defaultInt);
    }

    public static boolean getBoolean(String key){
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultBoolean){
        return Data.getSharedPreferences().getBoolean(key, defaultBoolean);
    }

    public static void clearData(){
        getSharedPreferences()
                .edit()
                .clear()
                .commit();
    }
}
