package com.example.dvhplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

 import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.dvhplay.databinding.ActivityMainBinding;
import com.example.dvhplay.film.FilmFragment;
import com.example.dvhplay.home.HomeFragment;
import com.example.dvhplay.more.MoreFragment;
import com.example.dvhplay.tv.TVFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG ="";
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
        binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
        getFragment(HomeFragment.newInstance());
        binding.llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imHome.setImageResource(R.drawable.ic_round_home_24_if_click);
                binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(HomeFragment.newInstance());
            }
        });
        binding.llFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_if_click_24);
                binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(FilmFragment.newInstance());
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });
        binding.llTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imTV.setImageResource(R.drawable.ic_round_tv_if_click_24);
                binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(TVFragment.newInstance());
            }
        });
        binding.llMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorDefault();
                binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24_ifclick);
                binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundMain));
                getFragment(MoreFragment.newInstance());
            }
        });
    }
    public void getFragment(Fragment fragment) {
        try {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getFragment: " + e.getMessage());
        }
    }
    public void setColorDefault(){
        binding.imHome.setImageResource(R.drawable.ic_round_home_24);
        binding.imFilm.setImageResource(R.drawable.ic_round_local_movies_24);
        binding.imTV.setImageResource(R.drawable.ic_round_tv_24);
        binding.imMore.setImageResource(R.drawable.ic_round_more_horiz_24);
        binding.imUpload.setImageResource(R.drawable.ic_round_arrow_upward_24);
        binding.tvUpload.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvHome.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvFilm.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvTV.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
        binding.tvMore.setTextColor(getResources().getColor(R.color.colorBackgroundDefaul));
    }
}