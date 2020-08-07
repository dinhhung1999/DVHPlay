package com.example.dvhplay.myMedia;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentMyMediaBinding;

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
        binding.lvVideoInDevice.setAdapter(adapterVideoInDevice);
        binding.lvVideoInDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                videoInDevice = videoInDeviceList.get(position);
                Intent intent = new Intent(getActivity().getBaseContext(),PlayVideoActivity.class);
                intent.setFlags(-1);
                intent.putExtra("path",videoInDevice.getPath());
                intent.putExtra("video", (Serializable) videoInDevice);
                intent.putExtra("videoUtilList", (Serializable) videoInDeviceList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    public List<VideoInDevice> getAllMedia() {
        String[] projection = {MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME};
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        try {
            cursor.moveToFirst();
            do {
                videoInDevice = new VideoInDevice(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
                videoInDeviceList.add(videoInDevice);
            } while (cursor.moveToNext());

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoInDeviceList;
    }
}