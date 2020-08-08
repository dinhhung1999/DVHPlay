package com.example.dvhplay.myMedia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.R;
import com.example.dvhplay.helper.ScaleTouchListener;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterVideoInDevice extends RecyclerView.Adapter<AdapterVideoInDevice.ViewHoder> {
    ScaleTouchListener itemViewListner;
    ScaleTouchListener.Config config;
    List<VideoInDevice> videoInDevices;
    iItemVideoInDeviceClick itemVideoInDeviceClick;
    public AdapterVideoInDevice(List<VideoInDevice> videoInDevices) {
        this.videoInDevices = videoInDevices;
    }

    public void setItemViewListner(iItemVideoInDeviceClick itemViewListner) {
        this.itemVideoInDeviceClick = itemViewListner;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video_layout,parent,false);
        AdapterVideoInDevice.ViewHoder viewHoder = new AdapterVideoInDevice.ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, int position) {
        final VideoInDevice video = videoInDevices.get(position);
        holder.tvTitle.setText(video.getName());
        Glide.with(holder.imAvatar.getContext())
                .load(video.getPath())
                .thumbnail(0.5f)
                .into(holder.imAvatar);
        config = new ScaleTouchListener.Config(100,0.90f,0.5f);
        itemViewListner = new ScaleTouchListener(config){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                itemVideoInDeviceClick.setItemOnClickVideo(video, holder.getLayoutPosition());
            }
        };
        holder.itemView.setOnTouchListener(itemViewListner);
    }

    @Override
    public int getItemCount() {
        return videoInDevices.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView imAvatar;
        TextView tvTitle;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
                imAvatar = itemView.findViewById(R.id.imAvatar);
                tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
