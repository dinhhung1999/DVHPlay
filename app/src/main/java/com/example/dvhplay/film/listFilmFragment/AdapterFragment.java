package com.example.dvhplay.film.listFilmFragment;

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
                TvSerieFragment tvSerieFragment = new TvSerieFragment();
                return tvSerieFragment;
            case 1:
                MovieFragment movieFragment = new MovieFragment();
                return movieFragment;
            case 2:
                AnimeFragment animeFragment = new AnimeFragment();
                return animeFragment;
            case 3:
                SportFragment sportFragment = new SportFragment();
                return sportFragment;
        }
        return null;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
