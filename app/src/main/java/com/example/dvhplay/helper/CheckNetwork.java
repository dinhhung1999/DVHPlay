package com.example.dvhplay.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.example.dvhplay.MainActivity;
import com.example.dvhplay.R;

import androidx.appcompat.app.AlertDialog;

public class CheckNetwork {
    public boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            return true;
        }
        else {
            return false;
        }
//        if (context == null) return false;
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
//            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
//            if (capabilities == null){
//                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
//                    return true;
//                }else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
//                    return true;
//                }else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
//                    return true;
//                }
//            }
//        }
//        else {
//            try {
//                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
//                    return true;
//                }
//            } catch (Exception e) {
//                Log.i("update_statut", "" + e.getMessage());
//            }
//        }
//        Log.i("update_statut","Network is available : FALSE ");
//        return false;
    }
    public boolean isInternetAvailable(Context context) {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            Log.i("update_statut","Network is not available");
            return false;
        }
    }
}
