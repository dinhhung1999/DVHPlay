package com.example.dvhplay.film.listFilmFragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.Api.Api;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentMovieBinding;
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

public class MovieFragment extends Fragment {
    FragmentMovieBinding binding;
    Api api = new Api();
    String json = "";
    AdapterVideo adapterVideo;
    VideoUlti videoUlti;
    List<VideoUlti> videoUtilList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie, container, false);
        class DoGetVideo extends AsyncTask<Void, Void, Void> {
            String result = "";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
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
                    RecyclerView.LayoutManager GridlayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
                    binding.rvMovie.setLayoutManager(GridlayoutManager);
                    binding.rvMovie.setAdapter(adapterVideo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        new DoGetVideo().execute();
        return binding.getRoot();
    }
}