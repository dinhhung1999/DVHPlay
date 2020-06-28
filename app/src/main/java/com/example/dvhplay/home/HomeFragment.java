package com.example.dvhplay.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.Api.Api;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.video.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;

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
    Api api = new Api();
    String json = "";
    AdapterVideo adapterVideo;
    VideoUlti videoUlti;
    List<VideoUlti> videoUtilList;

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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        binding.rfHomeFragment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.rfHomeFragment.setRefreshing(false);
            }
        });
        class DoGetVideo extends AsyncTask<Void, Void, Void> {
            String result = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                binding.tvHotVideo.setVisibility(View.GONE);
                binding.tvListProposed.setVisibility(View.GONE);
                binding.tvListSynthetic.setVisibility(View.GONE);
                binding.prBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(api.getApiHome());
                    URLConnection urlConnection = url.openConnection();
                    InputStream is = urlConnection.getInputStream();
                    int byteCharacter;
                    while ((byteCharacter = is.read()) != -1) {
                        result += (char) byteCharacter;
                    }
                    Log.d(TAG, "doInBackground" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                json = result;
                getJsonArray(json);
                binding.prBar.setVisibility(View.GONE);
                binding.tvHotVideo.setVisibility(View.VISIBLE);
                binding.tvListProposed.setVisibility(View.VISIBLE);
                binding.tvListSynthetic.setVisibility(View.VISIBLE);
            }

            public void getJsonArray(String json) {
                try {
                    videoUtilList = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(json);
                    int lenght = jsonArray.length();
                    for (int i = 0; i < lenght; i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        String title = jo.getString("title");
                        String url = jo.getString("file_mp4");
                        String urlImage = jo.getString("avatar");
                        videoUlti = new VideoUlti(null, null, null, null, null, title, urlImage, null, url, null, null, null, null, null, null, null, null);

                        videoUtilList.add(videoUlti);
                    }
                    adapterVideo = new AdapterVideo(videoUtilList);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            startActivity(intent);
                        }
                    });
                            RecyclerView.LayoutManager layoutManagerHotVideo = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                            binding.rvHotvideo.setLayoutManager(layoutManagerHotVideo);
                            binding.rvHotvideo.setAdapter(adapterVideo);

                            RecyclerView.LayoutManager layoutManagerSynthetic = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                            binding.rvSynthetic.setLayoutManager(layoutManagerSynthetic);
                            binding.rvSynthetic.setAdapter(adapterVideo);

                            RecyclerView.LayoutManager layoutManagerrvProposed = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                            binding.rvProposed.setLayoutManager(layoutManagerrvProposed);
                            binding.rvProposed.setAdapter(adapterVideo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        new DoGetVideo().execute();
        return binding.getRoot();
    }
}