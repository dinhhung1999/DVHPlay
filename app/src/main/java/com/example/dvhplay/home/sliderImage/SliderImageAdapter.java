package com.example.dvhplay.home.sliderImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dvhplay.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderImageAdapter extends SliderViewAdapter<SliderImageAdapter.SliderAdapterVH> {

    List<SliderItem> SliderItems = new ArrayList<>();
    iItemOnClickSlider iItemOnClickSlider;

    public void setiItemOnClickSlider(iItemOnClickSlider iItemOnClickSlider) {
        this.iItemOnClickSlider = iItemOnClickSlider;
    }

    public SliderImageAdapter(List<SliderItem> sliderItems) {
        SliderItems = sliderItems;
    }

    public void renewItems(List<SliderItem> sliderItems) {
        this.SliderItems = sliderItems;
        notifyDataSetChanged();
    }
    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        final SliderItem sliderItem = SliderItems.get(position);

        viewHolder.textViewTitle.setText(sliderItem.getTitle());
        Glide.with(viewHolder.imageViewBackground).load(sliderItem.getAvatar()).into(viewHolder.imageViewBackground);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iItemOnClickSlider.setItemOnClickSlider(sliderItem);
            }
        });
    }
    @Override
    public int getCount() {
        return SliderItems.size();
    }

    public class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;
        TextView textViewTitle;
        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_image_slider);
            textViewTitle = itemView.findViewById(R.id.tv_title_slider);
            this.itemView = itemView;
        }
    }
}

