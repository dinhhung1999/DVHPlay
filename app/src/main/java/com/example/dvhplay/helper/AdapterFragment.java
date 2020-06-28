package com.example.dvhplay.helper;

import android.content.Context;
import android.util.Log;

import com.example.dvhplay.R;
import com.example.dvhplay.film.FilmFragment;
import com.example.dvhplay.home.HomeFragment;
import com.example.dvhplay.more.MoreFragment;
import com.example.dvhplay.tv.TVFragment;
import com.google.android.material.tabs.TabItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class AdapterFragment extends FragmentPagerAdapter {
    private Context mycontext;
    int totalTabs;
    private static final String TAG = "MainActivity";
    public AdapterFragment(Context context, FragmentManager fm, int totalTabs){
        super(fm);
        mycontext = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                FilmFragment filmFragment = new FilmFragment();
                return filmFragment;
            case 2:
                TVFragment tvFragment = new TVFragment();
                return tvFragment;
            case 3:
                MoreFragment moreFragment = new MoreFragment();
                return moreFragment;
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return getPageTitle(R.string.title_home);
            case 1:
                return getPageTitle(R.string.title_film);
            case 2:
                return getPageTitle(R.string.title_TV);
            case 3:
                return getPageTitle(R.string.title_more);
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

}
