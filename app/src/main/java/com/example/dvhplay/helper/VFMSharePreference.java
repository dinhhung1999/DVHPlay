package com.example.dvhplay.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Collections;
import java.util.Set;

import androidx.annotation.RequiresApi;

@SuppressWarnings({"unused", "WeakerAccess"})
public class VFMSharePreference {

    private static final String PREF_NAME = "VFMPrefs";
    private static final String LOG_TAG = "SharePreference";
    private static final String APP_NAME = "DVH Play";

    public static String getAppName() {
        return APP_NAME;
    }

    private static final String KEY_LANGUAGE = "KEY_LANGUAGE";
    private static final String KEY_ACCOUNT = "KEY_ACCOUNT";
    private static final String KEY_ORDER_BOOK = "KEY_ORDER_BOOK";
    private static final String KEY_CCQMODEL = "KEY_CCQMODEL";
    private static final String KEY_CCQMODEL_LIST = "KEY_CCQMODEL_LIST";
    private static final String KEY_CCQMODEL_SW = "KEY_CCQMODEL_SW";
    private static final String KEY_CATEGORY_SYS = "KEY_CATEGORY_SYS";
    private static final String KEY_LIST_PORTFOLIO = "KEY_LIST_PORTFOLIO";
    private static final String KEY_LIST_ORDER_BOOK = "KEY_LIST_ORDER_BOOK";
    private static final String KEY_ACCOUNT_VERIFIED = "KEY_ACCOUNT_VERIFIED";
    private static final String KEY_OTHER_PRODUCT = "KEY_OTHER_PRODUCT";
    private static final String KEY_NOTIFICATION = "KEY_NOTIFICATION";
    private static final String KEY_NOTIFICATION_READ = "KEY_NOTIFICATION_READ";
    private static final String KEY_TRADE_OTP = "KEY_TRADE_OTP";

    private Context context;

    public VFMSharePreference(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences("FDSPrefs", Context.MODE_PRIVATE);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void putLongValue(String key, long n) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, n);
        editor.apply();
    }

    public long getLongValue(String key) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getLong(key, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void putIntValue(String key, int n) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, n);
        editor.apply();
    }

    public int getIntValue(String key, int intDefault) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getInt(key, intDefault);
    }

    public void putStringValue(String key, String s) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public String getStringValue(String key) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getString(key, "");
    }

    public String getStringValue(String key, String defaultValue) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getString(key, defaultValue);
    }

    public Set<String> getStringSet(String key) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getStringSet(key, Collections.<String>emptySet());
    }

    public void putStringSet(String key, Set<String> values) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, values);
        editor.apply();
    }

    public void putBooleanValue(String key, Boolean b) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getBoolean(key, false);
    }

    public void putFloatValue(String key, float f) {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, f);
        editor.apply();
    }

    public float getFloatValue(String key) {
        SharedPreferences pref = getSharedPreferences();
        return pref.getFloat(key, 0.0f);
    }


    @SuppressWarnings("UnusedReturnValue")
    public boolean remove(String key) {
        SharedPreferences preferences = getSharedPreferences();

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public void clear() {
        SharedPreferences pref = getSharedPreferences();
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }
    // ====================== Shortcut methods =======================

    public void putDefaultLanguage(String lang) {
        putStringValue(KEY_LANGUAGE, lang);
    }

    public String getDefaultLanguage() {
        return getStringValue(KEY_LANGUAGE, "en");
    }


    public void putAccountVerified(boolean verified) {
        putBooleanValue(KEY_ACCOUNT_VERIFIED, verified);
    }

    public boolean isAccountVerified() {
        return getBooleanValue(KEY_ACCOUNT_VERIFIED);
    }


}