package com.example.dvhplay.Api;

import com.example.dvhplay.home.sliderImage.SliderItem;
import com.example.dvhplay.video.VideoUlti;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface iVideoService   {
//    @GET("")
//    Call<List<VideoUlti>> getVideos();
//
//    @POST("")
//    Call<VideoUlti> addVideo(@Body VideoUlti videoUlti);
//
//    @PUT("update/{id}")
//    Call updateVideo(@Path("id") int id, @Body VideoUlti videoUlti);
//
//    @DELETE("delete/{id}")
//    Call deleteVideo(@Path("id") int id);

    @GET("getVideoHot")
    Call<List<VideoUlti>> getVideo();
    @GET("getVideoHot")
    Call<List<SliderItem>> getSlider();
}
