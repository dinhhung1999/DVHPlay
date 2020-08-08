package com.example.dvhplay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.dvhplay.PlaceSearch.PlaceSearchActivity;
import com.example.dvhplay.User.LoginActivity;
import com.example.dvhplay.databinding.ActivityMainBinding;
import com.example.dvhplay.film.FilmFragment;
import com.example.dvhplay.helper.CheckNetwork;
import com.example.dvhplay.helper.ScaleTouchListener;
import com.example.dvhplay.home.HomeFragment;
import com.example.dvhplay.more.MoreFragment;
import com.example.dvhplay.myMedia.MyMediaFragment;
import com.example.dvhplay.tv.TVFragment;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * //                       _ooOoo_
 * //                      o8888888o
 * //                      88" . "88
 * //                      (| -_- |)
 * //                       O\ = /O
 * //                   ____/`---'\____
 * //                 .   ' \\| |// `.
 * //                  / \\||| : |||// \
 * //                / _||||| -:- |||||- \
 * //                  | | \\\ - /// | |
 * //                | \_| ''\---/'' | |
 * //                 \ .-\__ `-` ___/-. /
 * //              ______`. .' /--.--\ `. . __
 * //           ."" '< `.___\_<|>_/___.' >'"".
 * //          | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //            \ \ `-. \_ __\ /__ _/ .-` / /
 * //    ======`-.____`-.___\_____/___.-`____.-'======
 * //                       `=---='
 * //
 * //    .............................................
 * //                    Pray for no Bugs
 * =====================================================
 * Name：DVHHung
 * Create on：2020-06-21
 * =====================================================
 */
public class MainActivity extends AppCompatActivity {
    private int FLAG_FRAGMENT = 0;
    int back = 0;
    private static final int MAINACTIVITY = 2000;
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
    CheckNetwork checkNetwork = new CheckNetwork();
    ScaleTouchListener llHomeListener, llFilmListener, llTVListener, llMyMediaListener, llMoreListener;
    ScaleTouchListener.Config config;
    ActionBar actionBar;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toobar_layout);
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.bgactionbar));
        actionBar.getCustomView().findViewById(R.id.imSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PlaceSearchActivity.class);
                startActivity(intent);
            }
        });
        if (FLAG_FRAGMENT == 0) CheckInternet();
        setRefresh();
        setupViews();
        binding.sRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.sRefresh.setRefreshing(false);
                setRefresh();
            }
        });
    }

    public void setupViews() {
        config = new ScaleTouchListener.Config(100, 0.90f, 0.5f);
        llHomeListener = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 1;
                CheckInternetInRefresh();
                getFragment(HomeFragment.newInstance());
                actionBar.show();
            }
        };
        llFilmListener = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                actionBar.hide();
                setColorDefault();
                binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_if_click_24);
                binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 2;
                CheckInternetInRefresh();
                getFragment(FilmFragment.newInstance());
            }
        };
        llTVListener = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                actionBar.hide();
                setColorDefault();
                binding.imTV.setImageResource(R.drawable.ic_round_tv_if_click_24);
                binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 3;
                CheckInternetInRefresh();
                getFragment(TVFragment.newInstance());
            }
        };
        llMyMediaListener = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                actionBar.hide();
                setColorDefault();
                binding.imMyMedia.setImageResource(R.drawable.ic_round_video_library_if_click_24);
                binding.tvMyMedia.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 4;
                getFragment(MyMediaFragment.newInstance());
            }
        };
        llMoreListener = new ScaleTouchListener(config) {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                actionBar.hide();
                setColorDefault();
                binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24_ifclick);
                binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                FLAG_FRAGMENT = 5;
                CheckInternetInRefresh();
                getFragment(MoreFragment.newInstance());
            }
        };
        binding.llHome.setOnTouchListener(llHomeListener);
        binding.llFilm.setOnTouchListener(llFilmListener);
        binding.llTV.setOnTouchListener(llTVListener);
        binding.llMyMedia.setOnTouchListener(llMyMediaListener);
        binding.llMore.setOnTouchListener(llMoreListener);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toobar_layout,menu);
//        return true;
//    }

    private void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setColorDefault() {

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

    public void CheckInternet() {
        if (!checkNetwork.isNetworkConnected(getBaseContext()) || !checkNetwork.isInternetAvailable(getBaseContext())) {
            binding.fullScreen.setVisibility(View.GONE);
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setTitle(R.string.tilte_dialog_check_network)
                    .setMessage(R.string.message_dialog)
                    .setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_SETTINGS), MAINACTIVITY);
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(getBaseContext(), MainActivity.class), MAINACTIVITY);
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
        } else binding.fullScreen.setVisibility(View.VISIBLE);
    }

    public void CheckInternetInRefresh() {
        if (!checkNetwork.isNetworkConnected(getBaseContext()) || !checkNetwork.isInternetAvailable(getBaseContext())) {
            final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setCancelable(false)
                    .setTitle(R.string.tilte_dialog_check_network)
                    .setMessage(R.string.checkNetworkPlayVideo)
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getBaseContext(), MainActivity.class));
                        }
                    })
                    .setNeutralButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finishAffinity();
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

    private void setRefresh() {
        switch (FLAG_FRAGMENT) {
            case 1:
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(HomeFragment.newInstance());
                FLAG_FRAGMENT = 1;
                break;
            case 2:
                setColorDefault();
                binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_if_click_24);
                binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(FilmFragment.newInstance());
                FLAG_FRAGMENT = 2;
                break;
            case 3:
                setColorDefault();
                binding.imTV.setImageResource(R.drawable.ic_round_tv_if_click_24);
                binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(TVFragment.newInstance());
                FLAG_FRAGMENT = 3;
                break;
            case 4:
                setColorDefault();
                binding.imMyMedia.setImageResource(R.drawable.ic_round_video_library_if_click_24);
                binding.tvMyMedia.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(MyMediaFragment.newInstance());
                FLAG_FRAGMENT = 4;
                break;
            case 5:
                setColorDefault();
                binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24_ifclick);
                binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                CheckInternetInRefresh();
                getFragment(MoreFragment.newInstance());
                FLAG_FRAGMENT = 5;
                break;
            default:
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(HomeFragment.newInstance());
                if (FLAG_FRAGMENT != 0) CheckInternetInRefresh();
                else CheckInternet();
                FLAG_FRAGMENT = 1;
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            back++;
            if (back == 1) {
                Toast.makeText(getBaseContext(), getText(R.string.back), Toast.LENGTH_SHORT).show();
                runnable.run();
            }
            if (back == 2) {
                back = 0;
                finishAffinity();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    private Runnable runnable = new Runnable() {
        @SuppressLint("ResourceAsColor")
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    back =0;
                }
            },2000);
        }
    };

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}