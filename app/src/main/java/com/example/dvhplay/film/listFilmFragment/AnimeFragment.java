package com.example.dvhplay.film.listFilmFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.dvhplay.Api.iVideoService;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentAnimeBinding;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.io.Serializable;
import java.util.List;


public class AnimeFragment extends Fragment {
    FragmentAnimeBinding binding;
    APIUtils APIUtils = new APIUtils();
    AdapterVideo adapterVideo;
    List<VideoUlti> videoUtilList;
    iVideoService videoService;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_anime, container, false);
        getVideosList();
        return binding.getRoot();
    }
    public void getVideosList(){
        videoService = RetrofitClient.getRetrofitClient(APIUtils.getBASE_API()).create(iVideoService.class);
        Call<List<VideoUlti>> call = videoService.getVideo2();
        call.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUtilList = response.body();
                    adapterVideo = new AdapterVideo(videoUtilList);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) videoUtilList);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager GridlayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                    binding.rvAnime.setLayoutManager(GridlayoutManager);
                    binding.rvAnime.setAdapter(adapterVideo);
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