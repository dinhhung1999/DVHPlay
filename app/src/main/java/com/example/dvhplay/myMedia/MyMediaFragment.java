package com.example.dvhplay.myMedia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentMyMediaBinding;
import com.example.dvhplay.helper.ScaleTouchListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class MyMediaFragment extends Fragment {
    FragmentMyMediaBinding binding;
    VideoInDevice videoInDevice;
    AdapterVideoInDevice adapterVideoInDevice;
    List<VideoInDevice> videoInDeviceList;

    public static MyMediaFragment newInstance() {
        return new MyMediaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_media, container, false);
        videoInDeviceList = new ArrayList<>();
        videoInDeviceList = getAllMedia();
        adapterVideoInDevice = new AdapterVideoInDevice(videoInDeviceList);
        adapterVideoInDevice.setItemViewListner(new iItemVideoInDeviceClick() {
            @Override
            public void setItemOnClickVideo(VideoInDevice video, int position) {
                videoInDevice = videoInDeviceList.get(position);
                Intent intent = new Intent(getActivity().getBaseContext(),PlayVideoActivity.class);
                intent.setFlags(-1);
                intent.putExtra("path",videoInDevice.getPath());
                intent.putExtra("video", (Serializable) videoInDevice);
                intent.putExtra("videoUtilList", (Serializable) videoInDeviceList);
                intent.putExtra("title",  videoInDevice.getName());
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        binding.rvVideoInDevice.setLayoutManager(layoutManager);
        binding.rvVideoInDevice.setAdapter(adapterVideoInDevice);
        return binding.getRoot();
    }
    public List<VideoInDevice> getAllMedia() {
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoInDevice = new VideoInDevice(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)),cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)));
                videoInDeviceList.add(videoInDevice);
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoInDeviceList;
    }
}