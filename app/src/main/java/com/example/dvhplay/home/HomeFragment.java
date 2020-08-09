package com.example.dvhplay.home;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dvhplay.Api.APIUtils;
import com.example.dvhplay.Api.RetrofitClient;
import com.example.dvhplay.GPSLocation.GPSLocation;
import com.example.dvhplay.PlayVideo.PlayVideoActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.home.sliderImage.SliderImageAdapter;
import com.example.dvhplay.home.sliderImage.iItemOnClickSlider;
import com.example.dvhplay.video.AdapterVideo;
import com.example.dvhplay.Models.VideoUlti;
import com.example.dvhplay.video.iItemOnClickVideo;
import com.example.dvhplay.Api.iVideoService;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.example.dvhplay.databinding.FragmentHomeBinding;
import com.romainpiel.shimmer.Shimmer;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    APIUtils APIUtils = new APIUtils();
    AdapterVideo adapterVideo;
    iVideoService videoService;
    List<VideoUlti> videoUtilList1, videoUltiList2, videoUltiList3;
    List<VideoUlti> sliderItemList = new ArrayList<>();
    List<String> listAds = new ArrayList<>();
    List<String> listDisttrict = new ArrayList<>();
    SliderImageAdapter sliderImageAdapter;
    private static final String TAG = "HomeFragment" ;
    String district ="";
    String ads = "";
    Shimmer shimmer;
    public HomeFragment() {
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        listAds.add("DevPro đào tạo lập trình số 1 tại Hà Nội");
        listAds.add("Trường Đại học Thủy lợi tuyển sinh 2020 - Đón đầu xu thế");
        listAds.add("KFC Nguyễn Trãi: Giá Siêu Khủng - chỉ 18k/1 miếng gà đã trở lại!");
        listAds.add("Trường Đại học Thủy lợi tuyển sinh 2020 - Đón đầu xu thế");
        listAds.add("Trường Đại học Thủy lợi tuyển sinh 2020 - Đón đầu xu thế");
        listDisttrict.add("Cầu Giấy");
        listDisttrict.add("Đống Đa");
        listDisttrict.add("Từ Liêm");
        listDisttrict.add("Hà Đông");
        listDisttrict.add("Thanh Xuân");
        getSliderImage();
        getVideosList();
        getAds();
        binding.imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rlAds.setVisibility(View.GONE);
            }
        });
        return binding.getRoot();
    }public void getSliderImage() {
        videoService = RetrofitClient.getRetrofitClient(APIUtils.getBASE_API()).create(iVideoService.class);
        Call<List<VideoUlti>> call = videoService.getSlider();
        call.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()) {
                    sliderItemList = response.body();
                    sliderImageAdapter = new SliderImageAdapter(sliderItemList);
                    sliderImageAdapter.setiItemOnClickSlider(new iItemOnClickSlider() {
                        @Override
                        public void setItemOnClickSlider(VideoUlti sliderItem,int position) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) sliderItem);
                            intent.putExtra("videoUtilList", (Serializable) sliderItemList);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    binding.cvSlider.setVisibility(View.VISIBLE);
                    binding.imageSilde.setSliderAdapter(sliderImageAdapter);
                    binding.imageSilde.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                    binding.imageSilde.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                    binding.imageSilde.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                    binding.imageSilde.setIndicatorSelectedColor(Color.WHITE);
                    binding.imageSilde.setIndicatorUnselectedColor(Color.GRAY);
                    binding.imageSilde.setIndicatorMarginCustom(10,20,30,450);
                    binding.imageSilde.setScrollTimeInSec(3);
                    binding.imageSilde.setAutoCycle(true);
                    binding.imageSilde.startAutoCycle();
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoUlti>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    public void getVideosList(){
        videoService = RetrofitClient.getRetrofitClient(APIUtils.getBASE_API()).create(iVideoService.class);
        Call<List<VideoUlti>> call = videoService.getVideo();
        call.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUtilList1= new ArrayList<>();
                    videoUtilList1 = response.body();
                    adapterVideo = new AdapterVideo(videoUtilList1);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) videoUtilList1);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    binding.llHotvideo.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerHotVideo = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvHotvideo.setLayoutManager(layoutManagerHotVideo);
                    binding.rvHotvideo.setAdapter(adapterVideo);
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoUlti>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
        Call<List<VideoUlti>> call2 = videoService.getVideo2();
        call2.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUltiList2 = new ArrayList<>();
                    videoUltiList2 = response.body();
                    adapterVideo = new AdapterVideo(videoUltiList2);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) videoUltiList2);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    binding.llProposed.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerSynthetic = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvSynthetic.setLayoutManager(layoutManagerSynthetic);
                    binding.rvSynthetic.setAdapter(adapterVideo);
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoUlti>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
        Call<List<VideoUlti>> call3 = videoService.getVideo3();
        call3.enqueue(new Callback<List<VideoUlti>>() {
            @Override
            public void onResponse(Call<List<VideoUlti>> call, Response<List<VideoUlti>> response) {
                if (response.isSuccessful()){
                    videoUltiList3 = new ArrayList<>();
                    videoUltiList3 = response.body();
                    adapterVideo = new AdapterVideo(videoUltiList3);
                    adapterVideo.setiItemOnClickVideo(new iItemOnClickVideo() {
                        @Override
                        public void setItemOnClickVideo(VideoUlti videoUlti, int position) {
                            Intent intent = new Intent(getActivity().getBaseContext(), PlayVideoActivity.class);
                            intent.putExtra("video", (Serializable) videoUlti);
                            intent.putExtra("videoUtilList", (Serializable) videoUltiList3);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                    });
                    binding.llSynthetic.setVisibility(View.VISIBLE);
                    RecyclerView.LayoutManager layoutManagerrvProposed = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    binding.rvProposed.setLayoutManager(layoutManagerrvProposed);
                    binding.rvProposed.setAdapter(adapterVideo);
                    binding.prBar.setVisibility(View.GONE);
                } else {
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoUlti>> call, Throwable t) {
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    public static int countChar(String str, String substr) {
        int count =0;
        for (int i =0 ; i< str.length();i++) {
            if (substr.charAt(0) == str.charAt(i)) {
                count ++;
            }
        }
        return count;
    }
    public static int ordinalIndexOf(String str, String substr, int n) {

        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
    public void getAds(){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity().getBaseContext(),Locale.getDefault());
        final GPSLocation gpsLocation = new GPSLocation(getActivity().getBaseContext());
        try {
            addresses = geocoder.getFromLocation(gpsLocation.getLatitude(), gpsLocation.getLongitude(), 1);
            if (addresses.size()!=0) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                int count = countChar(address,",");
                district = address.substring(ordinalIndexOf(address,",",count-2)+2,ordinalIndexOf(address,",",count-1));
                for (int i =listDisttrict.size()-1; i<listDisttrict.size();i++){
                    if (listDisttrict.get(i).trim().equalsIgnoreCase(district.trim())){
                        ads = listAds.get(i);
                    } else ads = listAds.get(0);
                }
                showAds();
            } else binding.rlAds.setVisibility(View.GONE);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAds(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run(){
                    binding.rlAds.setVisibility(View.VISIBLE);
                    binding.tvAds.setText(ads);
                    shimmer = new Shimmer();
                    shimmer.start(binding.tvAds);
            }
        }, 4000);
    }
}