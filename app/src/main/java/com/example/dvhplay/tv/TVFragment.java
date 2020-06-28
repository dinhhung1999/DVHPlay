package com.example.dvhplay.tv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.R;
import com.example.dvhplay.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVFragment extends Fragment {

    public TVFragment() {
        // Required empty public constructor
    }


    public static TVFragment newInstance() {
        return new TVFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_v, container, false);
    }
}