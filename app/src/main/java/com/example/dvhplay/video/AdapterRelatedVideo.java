package com.example.dvhplay.video;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.R;
import com.example.dvhplay.helper.ScaleTouchListener;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRelatedVideo extends RecyclerView.Adapter<AdapterRelatedVideo.ViewHoder> {
    List<VideoUlti> videoUltiList;
    iItemOnClickVideo iItemOnClickVideo;
    Shimmer shimmer;
    ScaleTouchListener itemViewListner;
    ScaleTouchListener.Config config;

    public AdapterRelatedVideo(List<VideoUlti> videoUltiList) {
        this.videoUltiList = videoUltiList;
    }
    public void setiItemOnClickVideo(iItemOnClickVideo itemOnClickVideo){
        this.iItemOnClickVideo = itemOnClickVideo;
    }

    @NonNull
    @Override
    public AdapterRelatedVideo.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video_in_related_list_layout,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final AdapterRelatedVideo.ViewHoder holder, int position) {
        final VideoUlti videoUlti = videoUltiList.get(position);
        holder.tvTitle.setText(videoUlti.getTitle());
        holder.tvTimeDate.setText(videoUlti.getDate_published());
        Glide.with(holder.imAvatar).load(videoUlti.getAvatar()).into(holder.imAvatar);
        config = new ScaleTouchListener.Config(100,0.90f,0.5f);
        itemViewListner = new ScaleTouchListener(config){
            @Override
            public void onClick(View v) {
                super.onClick(v);
                iItemOnClickVideo.setItemOnClickVideo(videoUlti, holder.getLayoutPosition());
            }
        };
        holder.itemView.setOnTouchListener(itemViewListner);
        if (position == 0) {
            holder.rlVideo.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
            holder.itemView.setEnabled(false);
            holder.avi.show();
            shimmer = new Shimmer();
            shimmer.start(holder.tv);
        }
    }

    @Override
    public int getItemCount() {
        return videoUltiList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        ImageView imAvatar;
        TextView tvTitle,tvTimeDate;
        RelativeLayout rlVideo;
        AVLoadingIndicatorView avi;
        ShimmerTextView tv;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            imAvatar = itemView.findViewById(R.id.imAvatar);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTimeDate = itemView.findViewById(R.id.tvTimeDate);
            rlVideo = itemView.findViewById(R.id.rlVideo);
            avi = itemView.findViewById(R.id.avi);
            tv = itemView.findViewById(R.id.tvShimmer);
        }
    }
}