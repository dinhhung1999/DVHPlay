package com.example.dvhplay.myMedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dvhplay.R;

import java.util.List;

public class AdapterVideoInDevice extends BaseAdapter {
    List<VideoInDevice> videoInDeviceList;

    public AdapterVideoInDevice(List<VideoInDevice> videoInDeviceList) {
        this.videoInDeviceList = videoInDeviceList;
    }

    @Override
    public int getCount() {
        return videoInDeviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoInDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v =inflater.inflate(R.layout.item_video_in_device,parent,false);
        TextView tvPath = v.findViewById(R.id.tvPath);
        VideoInDevice videoInDevice = videoInDeviceList.get(position);
//        tvSTT.setText(position);
        tvPath.setText(videoInDevice.getPath());
        return v;
    }
}
