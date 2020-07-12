package com.example.dvhplay.home;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.Api.APIUtils;
import com.example.dvhplay.Api.RetrofitClient;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.video.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;
import com.example.dvhplay.Api.iVideoService;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import com.example.dvhplay.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    APIUtils APIUtils = new APIUtils();
    String json = "";
    AdapterVideo adapterVideo;
    VideoUlti videoUlti;
    iVideoService videoService;
    List<VideoUlti> videoUtilList = new ArrayList<>();

    private static final String TAG = "HomeFragment" ;

    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        getVideosList();
        return binding.getRoot();
    }
    public void getVideosList(){
        videoService = RetrofitClient.getRetrofitClient(APIUtils.getBASE_API()).create(iVideoService.class);
        Call<List<VideoUlti>> call = videoService.getVideo();
        call.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUtilList = response.body();
                    adapterVideo = new AdapterVideo(videoUtilList);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            startActivity(intent);
                        }
                    });
                    binding.llHotvideo.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerHotVideo = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvHotvideo.setLayoutManager(layoutManagerHotVideo);
                    binding.rvHotvideo.setAdapter(adapterVideo);

                    binding.llProposed.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerSynthetic = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvSynthetic.setLayoutManager(layoutManagerSynthetic);
                    binding.rvSynthetic.setAdapter(adapterVideo);

                    binding.llSynthetic.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerrvProposed = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvProposed.setLayoutManager(layoutManagerrvProposed);
                    binding.rvProposed.setAdapter(adapterVideo);
                    binding.prBar.setVisibility(View.GONE);
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoUlti>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
}