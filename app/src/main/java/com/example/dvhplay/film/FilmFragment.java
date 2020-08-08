package com.example.dvhplay.film;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dvhplay.MainActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentFilmBinding;
import com.example.dvhplay.film.listFilmFragment.AdapterFragment;
import com.example.dvhplay.film.listFilmFragment.MovieFragment;
import com.google.android.material.tabs.TabLayout;

public class FilmFragment extends Fragment {
    FragmentFilmBinding binding;
    public FilmFragment() {
    }
    public static FilmFragment newInstance() {
        return new FilmFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_film, container, false);
        binding.tabLayoutTop.setTabGravity(binding.tabLayoutTop.GRAVITY_FILL);
        final AdapterFragment adapterFragment = new AdapterFragment(getActivity().getBaseContext(),getChildFragmentManager(),binding.tabLayoutTop.getTabCount());
        binding.vpFilm.setAdapter(adapterFragment);
//        binding.tabLayoutTop.post(new Runnable() {
//            @Override
//            public void run() {
                binding.tabLayoutTop.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#FFF87E00"));
                binding.tabLayoutTop.setupWithViewPager(binding.vpFilm);
                binding.tabLayoutTop.getTabAt(0).setText(R.string.TV_serie);
                binding.tabLayoutTop.getTabAt(1).setText(R.string.movie);
                binding.tabLayoutTop.getTabAt(2).setText(R.string.anime);
                binding.tabLayoutTop.getTabAt(3).setText(R.string.sport);
                binding.tabLayoutTop.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimaryDark));
//            }
//        });
        setAppBarHeight();
        return binding.getRoot();
    }
    private void setAppBarHeight() {
        binding.tabLayoutTop.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight() + dpToPx(48 + 56)));
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int dpToPx(int dp) {
        float density = getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
}