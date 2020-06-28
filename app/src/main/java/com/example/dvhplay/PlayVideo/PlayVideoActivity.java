package com.example.dvhplay.PlayVideo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.dvhplay.R;
import com.example.dvhplay.databinding.ActivityPlayVideoBinding;
import com.example.dvhplay.video.VideoUlti;

public class PlayVideoActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    ActivityPlayVideoBinding binding;
    VideoUlti videoUlti;
    String url = "";
    Utilities utilities;
    Handler handler = new Handler();
    int currentPosition = 0;
    boolean fullscreen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_play_video);
        Intent intent =getIntent();
        setInvisibility();
        videoUlti = (VideoUlti) intent.getSerializableExtra("video");
        binding.tvTitleVideo.setText(videoUlti.getTitle());
        binding.vvPlayVideo.requestFocus();
        initOnjects();
        playVideo();
        binding.vvPlayVideo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onShowing();
                return false;
            }
        });
        binding.imPauseOrResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.vvPlayVideo.isPlaying()){
                    binding.imPauseOrResume.setImageResource(R.drawable.ic_round_play_arrow_24);
                    onPause();
                }else {
                    binding.imPauseOrResume.setImageResource(R.drawable.ic_baseline_pause_24);
                    onResume();
                }
            }
        });
        binding.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullscreen) {
                    binding.svBottom.setVisibility(View.VISIBLE);
                    binding.imFullScreen.setImageResource(R.drawable.ic_round_fullscreen_24);
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.WRAP_CONTENT;
                    binding.getRoot().setLayoutParams(params);
                    fullscreen = false;
                }else {
                    binding.vvPlayVideo.stopPlayback();
                    finish();
                }
            }
        });
        binding.imReplay10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = binding.vvPlayVideo.getCurrentPosition();
                currentPosition = currentPosition - 10000;
                binding.vvPlayVideo.seekTo(currentPosition);
                if (binding.vvPlayVideo.isPlaying()){
                    binding.vvPlayVideo.start();
                }else {
                    binding.vvPlayVideo.pause();
                }
            }
        });
        binding.imForward10s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition = binding.vvPlayVideo.getCurrentPosition();
                currentPosition = currentPosition + 10000;
                binding.vvPlayVideo.seekTo(currentPosition);
                if(binding.vvPlayVideo.isPlaying()){
                    binding.vvPlayVideo.start();
                }else {
                    binding.vvPlayVideo.pause();
                }
            }
        });
        binding.imFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            binding.svBottom.setVisibility(View.GONE);
            if (fullscreen){
                binding.svBottom.setVisibility(View.VISIBLE);
                binding.imFullScreen.setImageResource(R.drawable.ic_round_fullscreen_24);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.WRAP_CONTENT;
                binding.getRoot().setLayoutParams(params);
                fullscreen = false;
            }else {
                binding.svBottom.setVisibility(View.GONE);
                binding.imFullScreen.setImageResource(R.drawable.ic_round_fullscreen_exit_24);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                }
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) binding.getRoot().getLayoutParams();
                params.width = params.MATCH_PARENT;
                params.height = params.MATCH_PARENT;
                binding.getRoot().setLayoutParams(params);
                fullscreen = true;
            }
            }
        });
        binding.llBrightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initOnjects(){
        binding.nbVideo.setOnSeekBarChangeListener(this);
        utilities = new Utilities();
    }
    private void playVideo(){
        url = videoUlti.getUrlVideo();
        binding.vvPlayVideo.setVideoURI(Uri.parse(url));
        binding.nbVideo.setProgress(0);
        binding.nbVideo.setMax(100);
        binding.vvPlayVideo.start();
        updateProgreeBar();
    }
    private void updateProgreeBar(){
        handler.postDelayed(updateTimeTask,0);
    }
    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            binding.nbVideo.setProgress(binding.vvPlayVideo.getCurrentPosition());
            binding.nbVideo.setMax(binding.vvPlayVideo.getDuration());
            binding.tvCurrentTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getCurrentPosition()));
            binding.tvTotalTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getDuration()));
            if (binding.vvPlayVideo.getCurrentPosition()==binding.vvPlayVideo.getDuration()){
                binding.imForward10s.setImageResource(R.drawable.ic_round_forward_10_hide_24);
            }else {
                binding.imForward10s.setImageResource(R.drawable.ic_round_forward_10_24);
            }
            handler.postDelayed(this,0);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int position, boolean isHold) {
        if(isHold)
        {
            binding.vvPlayVideo.seekTo(position);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        binding.vvPlayVideo.pause();
        handler.removeCallbacks(updateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(updateTimeTask);
//        // forward or backward to certain seconds
        binding.vvPlayVideo.seekTo(binding.nbVideo.getProgress());
        // update timer progress again
        updateProgreeBar();
        binding.vvPlayVideo.start();
    }
    public void setVisibility(){
        binding.llBackandSetting.setVisibility(View.VISIBLE);
        binding.llController.setVisibility(View.VISIBLE);
        binding.llTimeAndScreen.setVisibility(View.VISIBLE);
        binding.nbVideo.setVisibility(View.VISIBLE);
    }
    public void setInvisibility(){
        binding.llBackandSetting.setVisibility(View.INVISIBLE);
        binding.llController.setVisibility(View.INVISIBLE);
        binding.llTimeAndScreen.setVisibility(View.INVISIBLE);
        binding.nbVideo.setVisibility(View.INVISIBLE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        currentPosition = binding.vvPlayVideo.getCurrentPosition();
        binding.vvPlayVideo.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        binding.vvPlayVideo.seekTo(currentPosition);
        binding.vvPlayVideo.start();
    }
    public void onShowing(){
        if (binding.llController.getVisibility()==View.INVISIBLE){
            setVisibility();
            handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {
                    setInvisibility();
                    handler.postDelayed(this, 8000);
                }
            };handler.postDelayed(r, 8000);
        }else {
            setInvisibility();
        }
    }
}