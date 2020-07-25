package com.example.dvhplay.tv;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentTVBinding;

public class TVFragment extends Fragment {
    FragmentTVBinding binding;
    public static TVFragment newInstance() {
        return new TVFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_t_v, container, false);
        WebSettings webSettings = binding.wvTv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        WebViewClientImpl webViewClient = new WebViewClientImpl(getActivity());
        binding.wvTv.setWebViewClient(webViewClient);
        binding.wvTv.loadUrl("https://fptplay.vn/xem-truyen-hinh");
        return binding.getRoot();
    }
}