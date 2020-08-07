package com.example.dvhplay.Api;

import com.example.dvhplay.Models.VideoUlti;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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
    @GET("GetItemCategoryOne")
    Call<List<VideoUlti>> getSlider();
    @GET("GetItemCategoryOne")
    Call<List<VideoUlti>> getVideo2();
    @GET("GetItemCategoryTwo")
    Call<List<VideoUlti>> getVideo3();
    @GET("GetCategory")
    Call<List<VideoUlti>> getCategory();
}
