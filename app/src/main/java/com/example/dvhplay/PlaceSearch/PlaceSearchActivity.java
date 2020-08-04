package com.example.dvhplay.PlaceSearch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.dvhplay.Api.APIUtils;
import com.example.dvhplay.Api.RetrofitClient;
import com.example.dvhplay.Api.iVideoService;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.ActivityPlaceSearchBinding;
import com.example.dvhplay.helper.SQLHelper;
import com.example.dvhplay.home.sliderImage.SliderImageAdapter;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.video.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.example.dvhplay.R.xml.searchable;

public class PlaceSearchActivity extends AppCompatActivity {
    ActivityPlaceSearchBinding binding;
    private static final String TAG = "PlacesListSearchActivity";
    SQLHelper sqlHelper;
    public ArrayAdapter adapter;
    ArrayList listHistorySearch = new ArrayList();
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setCustomView(R.layout.search_view_layout);
        searchView = actionBar.getCustomView().findViewById(R.id.svSearch);

        getVideosList();
        runnable.run();
        listHistorySearch = (ArrayList) sqlHelper.getAllHistoryAdvanced();
        if (listHistorySearch != null) {
            adapter = new ArrayAdapter<String>(getBaseContext(),
                    android.R.layout.simple_dropdown_item_1line, listHistorySearch);
            binding.listHistory.setAdapter(adapter);
        }
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
                adapter.notifyDataSetChanged();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
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
                searchView.setQuery(listHistorySearch.get(position).toString(),false);
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
                binding.listHistory.setVisibility(View.GONE);
                binding.rvSearchVideo.setVisibility(View.VISIBLE);
                binding.rvSearchVideo.setBackgroundColor(getResources().getColor(R.color.colorBackground));
            } else {
                binding.rvSearchVideo.setBackgroundColor(getResources().getColor(R.color.colorBackgroundDefaul));
                binding.listHistory.setVisibility(View.VISIBLE);
                binding.rvSearchVideo.setVisibility(View.GONE);
            }
            handler.postDelayed(this,0);
        }
    };
}