package com.example.dvhplay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;

import com.example.dvhplay.databinding.ActivityMainBinding;
import com.example.dvhplay.film.FilmFragment;
import com.example.dvhplay.helper.CheckNetwork;
import com.example.dvhplay.home.HomeFragment;
import com.example.dvhplay.more.MoreFragment;
import com.example.dvhplay.myMedia.MyMediaFragment;
import com.example.dvhplay.tv.TVFragment;
import com.facebook.stetho.inspector.protocol.module.Network;
import com.google.android.gms.maps.model.LatLng;

import java.net.InetAddress;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    private int FLAG_FRAGMENT = 0;
    private static final int MAINACTIVITY = 2000;

    private static final String TAG ="MainActivity";
    ActivityMainBinding binding;
    CheckNetwork checkNetwork = new CheckNetwork();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        if (!checkRequiredPermissions()) checkRequiredPermissions();
        if (FLAG_FRAGMENT ==0) CheckInternet();
        setRefresh();
        binding.llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT =1;
                CheckInternetInRefresh();
                getFragment(HomeFragment.newInstance());
            }
        });
        binding.llFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_if_click_24);
                binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT =2;
                CheckInternetInRefresh();
                getFragment(FilmFragment.newInstance());

            }
        });
        binding.llTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imTV.setImageResource(R.drawable.ic_round_tv_if_click_24);
                binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 3;
                CheckInternetInRefresh();
                getFragment(TVFragment.newInstance());
            }
        });
        binding.llMyMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imMyMedia.setImageResource(R.drawable.ic_round_video_library_if_click_24);
                binding.tvMyMedia.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 4;
                getFragment(MyMediaFragment.newInstance());
            }
        });
        binding.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24_ifclick);
                binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 5;
                CheckInternetInRefresh();
                getFragment(MoreFragment.newInstance());
            }
        });
        binding.sRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.sRefresh.setRefreshing(false);
                setRefresh();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar_layout,menu);
        return true;
    }

    private void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setColorDefault(){

        binding.imHome.setImageResource(R.drawable.ic_round_home_24);
        binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_24);
        binding.imTV.setImageResource(R.drawable.ic_round_tv_24);
        binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24);
        binding.imMyMedia.setImageResource(R.drawable.ic_round_video_library_24);
        binding.tvMyMedia.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
    }
    private boolean checkRequiredPermissions(){
        String[] perms ={Manifest.permission.CHANGE_CONFIGURATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this,perms)){
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this,getString(R.string.message_request_permission_read_phone_state),MAINACTIVITY,perms);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    public void CheckInternet(){
        if (!checkNetwork.isNetworkConnected(getBaseContext())|| !checkNetwork.isInternetAvailable(getBaseContext())){
            binding.fullScreen.setVisibility(View.GONE);
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setTitle(R.string.tilte_dialog_check_network)
                    .setMessage(R.string.message_dialog)
                    .setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS),MAINACTIVITY);
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(getBaseContext(),MainActivity.class),MAINACTIVITY);
                        }
                    })
                    .create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getColor(R.color.colorButtonDialog));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorButtonDialog));
                }
            });
            alertDialog.show();
        }
        else binding.fullScreen.setVisibility(View.VISIBLE);
    }
    public void CheckInternetInRefresh(){
        if (!checkNetwork.isNetworkConnected(getBaseContext())|| !checkNetwork.isInternetAvailable(getBaseContext())){
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setTitle(R.string.tilte_dialog_check_network)
                    .setMessage(R.string.checkNetworkPlayVideo)
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getBaseContext(),MainActivity.class));
                        }
                    })
                    .setNeutralButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getColor(R.color.colorButtonDialog));
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.colorButtonDialog));
                }
            });
            alertDialog.show();
        }
    }
    private void setRefresh(){
        switch (FLAG_FRAGMENT ){
            case 1:
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(HomeFragment.newInstance());
                break;
            case 2:
                setColorDefault();
                binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_if_click_24);
                binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(FilmFragment.newInstance());
                break;
            case 3:
                setColorDefault();
                binding.imTV.setImageResource(R.drawable.ic_round_tv_if_click_24);
                binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(TVFragment.newInstance());
                break;
            case 4:
                setColorDefault();
                binding.imMyMedia.setImageResource(R.drawable.ic_round_video_library_if_click_24);
                binding.tvMyMedia.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(MyMediaFragment.newInstance());
                break;
            case 5:
                setColorDefault();
                binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24_ifclick);
                binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(MoreFragment.newInstance());
                break;
            default:
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(HomeFragment.newInstance());
                if (FLAG_FRAGMENT !=0)CheckInternetInRefresh();
                else CheckInternet();
                break;
        }
    }
}