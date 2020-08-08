package com.example.dvhplay.more;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.FragmentListFavoriteBinding;
import com.example.dvhplay.helper.SQLHelper;
import com.example.dvhplay.helper.VFMSharePreference;
import com.example.dvhplay.video.iItemOnClickVideo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListFavoriteFragment extends Fragment {
    FragmentListFavoriteBinding binding;
    VFMSharePreference sharePreference ;
    SQLHelper sqlHelper;
    int user_id =0;
    List<VideoUlti> favoriteVideos;
    AdapterListFavorite adapterListFavorite;
    public ListFavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list_favorite, container, false);
        sharePreference = new VFMSharePreference(container.getContext());
        sqlHelper = new SQLHelper(container.getContext());
        user_id = sharePreference.getIntValue("user_id",0);
        favoriteVideos = new ArrayList<>();
        favoriteVideos = sqlHelper.getALlFavorite(user_id);
        binding.tvListReuslts.setText(getText(R.string.listFavorie)+": "+favoriteVideos.size());
        if (favoriteVideos.size()!=0){
            adapterListFavorite = new AdapterListFavorite(favoriteVideos);
            adapterListFavorite.setiItemOnClickVideo(new iItemOnClickVideo() {
                @Override
                public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                    Intent intent = new Intent(getContext(), PlayVideoActivity.class);
                    intent.putExtra("video", (Serializable) videoUlti);
                    intent.putExtra("videoUtilList", (Serializable) favoriteVideos);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
            });
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1,RecyclerView.VERTICAL,false);
            binding.rvListFavorite.setLayoutManager(layoutManager);
            binding.rvListFavorite.setAdapter(adapterListFavorite);
        }
        return binding.getRoot();
    }
}