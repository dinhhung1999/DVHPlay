package com.example.dvhplay.more;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.R;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterListFavorite extends RecyclerView.Adapter<AdapterListFavorite.ViewHoder> {
    List<VideoUlti> favoriteVideos;
    iItemOnClickVideo iItemOnClickVideo;
    public AdapterListFavorite(List<VideoUlti> favoriteVideos) {
        this.favoriteVideos = favoriteVideos;
    }

    public void setiItemOnClickVideo(iItemOnClickVideo iItemOnClickVideo) {
        this.iItemOnClickVideo = iItemOnClickVideo;
    }

    @NonNull
    @Override
    public AdapterListFavorite.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_favorite_layout,parent,false);
        AdapterListFavorite.ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterListFavorite.ViewHoder holder, int position) {
        final VideoUlti favoriteVideo = favoriteVideos.get(position);
        holder.tvTitle.setText(favoriteVideo.getTitle());
        Glide.with(holder.imAvatar).load(favoriteVideo.getAvatar()).into(holder.imAvatar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemOnClickVideo.setItemOnClickVideo(favoriteVideo,holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteVideos.size();
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
