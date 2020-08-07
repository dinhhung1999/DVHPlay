package com.example.dvhplay.PlaceSearch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.dvhplay.Api.APIUtils;
import com.example.dvhplay.Api.RetrofitClient;
import com.example.dvhplay.Api.iVideoService;
import com.example.dvhplay.Models.SearchHistory;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.ActivityPlaceSearchBinding;
import com.example.dvhplay.helper.SQLHelper;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PlaceSearchActivity extends AppCompatActivity {
    ActivityPlaceSearchBinding binding;
    private static final String TAG = "PlacesListSearchActivity";
    SQLHelper sqlHelper;
    public ArrayAdapter adapter;
    List <SearchHistory> listHistorySearch;
    List <SearchHistory> lastHistorySearch ;
    com.example.dvhplay.Api.APIUtils APIUtils = new APIUtils();
    AdapterVideo adapterVideo;
    iVideoService videoService;
    List<VideoUlti> videoUtilList1, videoUltiList2, listSearch, videoUltiList;
    SearchView searchView;
    Handler handler = new Handler();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_search);
        sqlHelper = new SQLHelper(getBaseContext());
        listHistorySearch = new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setCustomView(R.layout.search_view_layout);
        searchView = actionBar.getCustomView().findViewById(R.id.svSearch);
        getVideosList();
        runnable.run();
        getHistory();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listSearch = new ArrayList<>();
                for (VideoUlti v : videoUltiList ){
                        if (v.getTitle().toLowerCase().contains(query.toLowerCase())) {
                            listSearch.add(v);
                        }
                    }
                if (listSearch.size() == 0) Toast.makeText(getBaseContext(), getText(R.string.noMatchFound), Toast.LENGTH_SHORT).show();
                else {
                    adapterVideo = new AdapterVideo(listSearch);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) listSearch);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(),2, RecyclerView.VERTICAL, false);
                    binding.rvSearchVideo.setLayoutManager(layoutManager);
                    binding.rvSearchVideo.setAdapter(adapterVideo);
                }
                sqlHelper.insertHistory(query);
                getHistory();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                    listSearch = new ArrayList<>();
                    for (VideoUlti v : videoUltiList ) {
                        if (v.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            listSearch.add(v);
                        }
                    }
                    adapterVideo = new AdapterVideo(listSearch);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) listSearch);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(),2, RecyclerView.VERTICAL, false);
                    binding.rvSearchVideo.setLayoutManager(layoutManager);
                    binding.rvSearchVideo.setAdapter(adapterVideo);
                return false;
            }
        });
        binding.listHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchView.setQuery(lastHistorySearch.get(position).toString(),true);
            }
        });
    }
    public void getVideosList() {
        videoUltiList = new ArrayList<>();
        videoService = RetrofitClient.getRetrofitClient(APIUtils.getBASE_API()).create(iVideoService.class);
        Call<List<VideoUlti>> call = videoService.getVideo();
        call.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()) {
                    videoUtilList1 = new ArrayList<>();
                    videoUtilList1 = response.body();
                    for (VideoUlti v : videoUtilList1) {
                        videoUltiList.add(v);
                    }
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
        Call<List<VideoUlti>> call2 = videoService.getVideo3();
        call2.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUltiList2 = new ArrayList<>();
                    videoUltiList2 = response.body();
                    for (VideoUlti v : videoUltiList2) {
                        videoUltiList.add(v);
                    }
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
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (searchView.getQuery().toString().trim().length()!=0){
                binding.llResults.setVisibility(View.VISIBLE);
                binding.llHistory.setVisibility(View.GONE);
            } else {
                binding.llResults.setVisibility(View.GONE);
                binding.llHistory.setVisibility(View.VISIBLE);
            }
            handler.postDelayed(this,0);
        }
    };
    public void getHistory(){
        listHistorySearch = sqlHelper.getAllHistoryAdvanced();
        if (listHistorySearch.size()>0) {
            lastHistorySearch = new ArrayList();
            int i =0;
            do {
                lastHistorySearch.add(listHistorySearch.get(listHistorySearch.size()-1-i));
                i++;
            } while ( i<=9 && listHistorySearch.size()>i);
            adapter = new ArrayAdapter<SearchHistory>(getBaseContext(),
                    android.R.layout.simple_dropdown_item_1line, lastHistorySearch);
            binding.listHistory.setAdapter(adapter);
        }
    }
}