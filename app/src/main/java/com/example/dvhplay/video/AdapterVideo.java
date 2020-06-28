package com.example.dvhplay.video;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.ViewHoder> {
    List<VideoUlti> videoUltiList;
    iItemOnClickVideo iItemOnClickVideo;

    public AdapterVideo(List<VideoUlti> videoUltiList) {
        this.videoUltiList = videoUltiList;
    }
    public void setiItemOnClickVideo(iItemOnClickVideo itemOnClickVideo){
        this.iItemOnClickVideo = itemOnClickVideo;
    }

    @NonNull
    @Override
    public AdapterVideo.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_video_layout,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideo.ViewHoder holder, int position) {
        final VideoUlti videoUlti = videoUltiList.get(position);
        holder.tvTitle.setText(videoUlti.getTitle());
        Glide.with(holder.imAvatar).load(videoUlti.getAvatarUrl()).into(holder.imAvatar);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemOnClickVideo.setItemOnClickVideo(videoUlti);
            }
        });
        holder.imAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemOnClickVideo.setItemOnClickVideo(videoUlti);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoUltiList.size();
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
