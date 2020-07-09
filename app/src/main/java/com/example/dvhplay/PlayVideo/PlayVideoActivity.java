package com.example.dvhplay.PlayVideo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dvhplay.MainActivity;
import com.example.dvhplay.R;
import com.example.dvhplay.databinding.ActivityPlayVideoBinding;
import com.example.dvhplay.helper.CheckNetwork;
import com.example.dvhplay.video.VideoUlti;
import com.facebook.stetho.common.StringUtil;

public class PlayVideoActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "PlayVideoActivity";

    ActivityPlayVideoBinding binding;
    private int WITDH_SCREEN;
    VideoUlti videoUlti;
    String url = "";
    String path = "";
    Utilities utilities;
    // Cập nhật thời gian phát
    private static final int UPDATE_PLAY_TIME = 0x01;
    private static final int UPDATE_TIME = 800;
    // ẩn thanh điều khiển
    private static final int HIDE_CONTROL_BAR = 0x02;

    // Hiển thị điều khiển trung gian
    private static final int SHOW_CENTER_CONTROL = 0x03;
    private static final int SHOW_CONTROL_TIME = 1000;

    // kiểm tra có khóa không
    private boolean isLock = false;

    //độ nhạy trượt
    private static final double SLIP_SENSITIVITY = 0.88;


    // code tăng giảm
    private final static int ADD_FLAG = 1;
    private final static int SUB_FLAG = -1;

    // Khoảng cách trượt tối thiểu

    private static final int MIN_SLIP_DISTANCE = 100;

    // Khoảng cách trượt tối đa

    private static final int MAX_SLIP_DISTANCE = 10000;

    // Âm lượng tối đa của thiết bị
    private static final int MAX_VOLUME = 100;

    // Âm lượng tối thiểu của thiết bị
    private static final int MIN_VOLUME = 0;

    // Độ sáng tối đa của thiết bị
    private static final int MAX_LIGHTNESS = 255;

    // Độ sáng tối thiểu của thiết bị
    private static final int MIN_LIGHTNESS = 0;

    // Độ rộng màn hình
    private int mScreenWidth = 0;

    // Kiểm tra full màn hình
    boolean fullscreen = false;

    // kiểm tra controller ẩn hiện
    private static boolean isShow = false;

    // Tổng thời gian video
    private long mVideoTotalTime = 0;

    // Kiểm tra có đang tua video không

    // Có dừng tiến trình không
    private boolean mIsFastFinish = false;

    // Âm lượng tối đa, tối thiểu của video
    private int mMaxVolume;

    // Độ sáng màn hình hiện tại
    private int mShowLightness;

    AudioManager audioManager;
    int currentPosition;
    int currentVolume;
    int changeVolume;
    boolean isPlaying = true;
    // kiểm tra load
    private boolean isLoad = true;
    Intent intent;
    Handler handler = new Handler();
    CheckNetwork checkNetwork = new CheckNetwork();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case UPDATE_PLAY_TIME:
                    long currentPosition = binding.vvPlayVideo.getCurrentPosition();
                    if (currentPosition <= mVideoTotalTime) {
                        binding.tvCurrentTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getCurrentPosition()));
                        binding.tvTotalTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getDuration()));
                        int progress = (int) ((currentPosition * 1.0 / mVideoTotalTime) * 100);
                        binding.nbVideo.setProgress(progress);
                        mHandler.sendEmptyMessageDelayed(UPDATE_PLAY_TIME, UPDATE_TIME);
                    }
                    break;
                case HIDE_CONTROL_BAR:
                    setVisibility();
                    break;
                case SHOW_CENTER_CONTROL:
                    binding.llMessage.setVisibility(View.GONE);
                    binding.tvChangeTime.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
            return false;
        }
    });
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_video);
        setInvisibility();
         intent = getIntent();
        if (intent.getFlags()==-1){
            path = intent.getStringExtra("path");
        }else {
            videoUlti = (VideoUlti) intent.getSerializableExtra("video");
            binding.tvTitleVideo.setText(videoUlti.getTitle());
            url = videoUlti.getUrlVideo();
        }
        binding.vvPlayVideo.requestFocus();
        initOnjects();
        playVideo();
        binding.vvPlayVideo.setOnTouchListener(new View.OnTouchListener() {
            private int CLICK_ACTION_THRESHOLD = 200;
            private float startX, startY, endX, endY, deltaX, deltaY;
            long eventTime, downTime;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (!fullscreen) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
//                            startX = event.getX();
//                            startY = event.getY();
                            downTime = event.getDownTime();
                            return true;
                        case MotionEvent.ACTION_UP:
//                            endX = event.getX();
//                            endY = event.getY();
                            eventTime = event.getEventTime();
                            long durationHold = eventTime - downTime;
                                if (durationHold < CLICK_ACTION_THRESHOLD && isLoad ){
                                onShowing();
                                break;
                            } else {
                                return true;
                            }
                    }
                    return false;
                } else {
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN:
                            startX = event.getRawX();
                            startY = event.getRawY();
                            downTime = event.getDownTime();
                            return true;
                        case MotionEvent.ACTION_UP:
//                            endX = event.getX();
//                            endY = event.getY();
                            eventTime = event.getEventTime();
                            long duration = eventTime - downTime;
                            if (duration < CLICK_ACTION_THRESHOLD) {
                                onShowing();
                                break;
                            }else {
                                return true;
                            }
                        case MotionEvent.ACTION_MOVE:
                            endX = event.getRawX();
                            endY = event.getRawY();
                            deltaX = startX - endX;
                            deltaY = startY - endY;
                            WITDH_SCREEN = v.getWidth();
                            if (Math.abs(deltaY) > Math.abs(deltaX)) {
                                if (!isLock){
                                    if (deltaY>0)  {  //  bottom to top
                                        if (startX < WITDH_SCREEN/2) { // left or right
                                            setChangeVolume(ADD_FLAG);
                                        } else  {
                                            setChangeLightness(ADD_FLAG);
                                        }
                                    }
                                    if (deltaY<0){
                                        if (startX <WITDH_SCREEN/2) { //left or right
                                            setChangeVolume(SUB_FLAG);
                                        } else if (Math.abs(deltaY) > Math.abs(deltaX)){
                                            setChangeLightness(SUB_FLAG);
                                        }
                                    }
                                }else {
                                    onShowing();
                                }
                                return false;
                            } else {
                                if (!isLock ){
                                    setInvisibility();
                                    onSeekChange((int) startX,(int) endX);
                                    return false;
                                }
                                else {
                                    onShowing();
                                }
                                return false;
                            }
                    }
                }
                return false;
            }
        });
        binding.imPauseOrResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.vvPlayVideo.isPlaying()) {
                    binding.imPauseOrResume.setImageResource(R.drawable.ic_round_play_arrow_24);
                    onPause();
                    isPlaying = false;
                    isLoad = false;
                } else {
                    binding.imPauseOrResume.setImageResource(R.drawable.ic_baseline_pause_24);
                    onResume();
                    isPlaying = true;
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
                } else {
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
                if (binding.vvPlayVideo.isPlaying()) {
                    binding.vvPlayVideo.start();
                } else {
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
                if (binding.vvPlayVideo.isPlaying()) {
                    binding.vvPlayVideo.start();
                } else {
                    binding.vvPlayVideo.pause();
                }
            }
        });
        binding.imFullScreen.setOnClickListener(new View.OnClickListener() {
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
                } else {
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
        binding.imLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLock) {
                    binding.imLock.setImageResource(R.drawable.ic_round_lock_open_24);
                    isLock = false;
                    onShowing();
                } else {
                    binding.imLock.setImageResource(R.drawable.ic_round_lock_24);
                    isLock = true;
                    onShowing();
                }
            }
        });
        binding.imRepPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vvPlayVideo.seekTo(0);
                binding.vvPlayVideo.start();
            }
        });
    }

    private void initOnjects() {
        binding.nbVideo.setOnSeekBarChangeListener(this);
        if (intent.getFlags()==-1){
            binding.vvPlayVideo.setVideoPath(path);
        }else {
            binding.vvPlayVideo.setVideoURI(Uri.parse(url));
        }
        binding.nbVideo.setProgress(0);
        binding.nbVideo.setMax(100);
        initVolumeWithLight();
        utilities = new Utilities();

    }
    private void playVideo() {
        isLoad = false;
        binding.vvPlayVideo.start();
        binding.prBar.setVisibility(View.VISIBLE);
        binding.vvPlayVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                binding.prBar.setVisibility(View.GONE);
                isLoad = true;
            }
        });
        updateProgreeBar();
    }

    private void updateProgreeBar() {
        handler.postDelayed(updateTimeTask, 0);
    }

    private Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            binding.nbVideo.setProgress(binding.vvPlayVideo.getCurrentPosition());
            binding.nbVideo.setMax(binding.vvPlayVideo.getDuration());
            binding.tvCurrentTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getCurrentPosition()));
            binding.tvTotalTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getDuration()));
            onLoading();
            if (binding.nbVideo.getProgress()==100) {
                binding.imForward10s.setImageResource(R.drawable.ic_round_forward_10_hide_24);
                binding.imPauseOrResume.setVisibility(View.GONE);
                binding.imRepPlay.setVisibility(View.VISIBLE);
            } else {
                binding.imForward10s.setImageResource(R.drawable.ic_round_forward_10_24);
                binding.imPauseOrResume.setVisibility(View.VISIBLE);
                binding.imRepPlay.setVisibility(View.GONE);
            }
            handler.postDelayed(this, 0);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int position, boolean isHold) {
        if (isHold) {
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
        if (isPlaying) {
            binding.vvPlayVideo.start();
        } else {
            onPause();
        }
    }
    public void setVisibility() {
            if (fullscreen) {
            if (isLock) {
                binding.imLock.setVisibility(View.VISIBLE);
                binding.llBackandSetting.setVisibility(View.INVISIBLE);
                binding.llController.setVisibility(View.INVISIBLE);
                binding.llTimeAndScreen.setVisibility(View.INVISIBLE);
                binding.nbVideo.setVisibility(View.INVISIBLE);
            } else {
                binding.imLock.setVisibility(View.VISIBLE);
                binding.llBackandSetting.setVisibility(View.VISIBLE);
                binding.llController.setVisibility(View.VISIBLE);
                binding.llTimeAndScreen.setVisibility(View.VISIBLE);
                binding.nbVideo.setVisibility(View.VISIBLE);
            }
        } else {
            binding.llBackandSetting.setVisibility(View.VISIBLE);
            binding.llController.setVisibility(View.VISIBLE);
            binding.llTimeAndScreen.setVisibility(View.VISIBLE);
            binding.nbVideo.setVisibility(View.VISIBLE);
        }
    }

    public void setInvisibility() {
        binding.imLock.setVisibility(View.INVISIBLE);
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
    private Runnable makeHidden = new Runnable() {
        public void run() {
            setInvisibility();
        }
    };
    public void onShowing() {
        if (binding.llController.getVisibility() == View.INVISIBLE) {
                binding.tvChangeTime.setVisibility(View.GONE);
                binding.llMessage.setVisibility(View.GONE);
                setVisibility();
                handler.postDelayed(makeHidden, 8000);
        } else {
            handler.removeCallbacks(makeHidden);
            setInvisibility();
            isShow = false;
        }
    }
    public void onLoading(){
        if (!binding.vvPlayVideo.isPlaying() && binding.nbVideo.getProgress() <100 && isLoad )
        {
            binding.prBar.setVisibility(View.VISIBLE);
            binding.imPauseOrResume.setVisibility(View.INVISIBLE);
            checkInternetWhilePlay();
        }else {
            binding.prBar.setVisibility(View.GONE);
            binding.imPauseOrResume.setVisibility(View.VISIBLE);
        }
    }
    private int getScreenBrightness(){
        int screenBrightness = MAX_LIGHTNESS;
        try {
        screenBrightness = Settings.System.getInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS);
        }catch (Exception e){
            e.printStackTrace();
        }
        return screenBrightness;

    }

    private void initVolumeWithLight() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume;
        mShowLightness = getScreenBrightness();

    }

    private void setChangeVolume(int flag) {
        currentVolume += flag;
        if (currentVolume > MAX_VOLUME) {
            currentVolume = MAX_VOLUME;
        } else if (currentVolume < MIN_VOLUME) {
            currentVolume = MIN_VOLUME;
        }
        changeVolume = currentVolume * mMaxVolume / 100;
        if (currentVolume != 0) {
            if (flag == 1) {
                binding.imMessage.setImageResource(R.drawable.ic_round_volume_up_24);
            } else if (flag == -1) {
                binding.imMessage.setImageResource(R.drawable.ic_round_volume_down_24);
            }
        } else {
            binding.imMessage.setImageResource(R.drawable.ic_round_volume_mute_24);
        }
        setInvisibility();
        binding.tvMessage.setText(String.format("%s%%", currentVolume));
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, changeVolume, 0);
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        binding.tvChangeTime.setVisibility(View.GONE);
        binding.llMessage.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL, SHOW_CONTROL_TIME);
    }
    private void setChangeLightness(int flag){
        mShowLightness += flag;
        if (mShowLightness > MAX_LIGHTNESS){
            mShowLightness = MAX_LIGHTNESS;
        }else if(mShowLightness < MIN_LIGHTNESS){
            mShowLightness = MIN_LIGHTNESS;
        }
        setInvisibility();
        binding.imMessage.setImageResource(R.drawable.ic_round_wb_sunny_24);
        binding.tvMessage.setText(String.format("%s%%",mShowLightness*100/255));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = mShowLightness/255f;
        getWindow().setAttributes(lp);
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        binding.tvChangeTime.setVisibility(View.GONE);
        binding.llMessage.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL,SHOW_CONTROL_TIME);
    }
    private void setFlashText(){
        binding.tvChangeTime.setText(utilities.milliSecondToTimer(binding.vvPlayVideo.getCurrentPosition())+" / "+utilities.milliSecondToTimer(binding.vvPlayVideo.getDuration()));
        setInvisibility();
        mHandler.removeMessages(SHOW_CENTER_CONTROL);
        binding.llMessage.setVisibility(View.GONE);
        binding.tvChangeTime.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(SHOW_CENTER_CONTROL,SHOW_CONTROL_TIME);
    }
    private void onSeekChange(int x1, int x2) {
        int currentPosition = binding.vvPlayVideo.getCurrentPosition();
        if (x1 - x2 > MIN_SLIP_DISTANCE) {
            if (currentPosition < MIN_SLIP_DISTANCE) {
                currentPosition = 0;
                setFlashText();
                binding.vvPlayVideo.seekTo(currentPosition);
            } else {
                int distance = (x1 - x2);
                binding.vvPlayVideo.seekTo(currentPosition - distance * 10);
                setFlashText();
            }
        } else if (x2 - x1 > MIN_SLIP_DISTANCE) {
                if (currentPosition + MAX_SLIP_DISTANCE > binding.vvPlayVideo.getDuration()) {
                    currentPosition = binding.vvPlayVideo.getDuration();
                    binding.vvPlayVideo.seekTo(currentPosition);
                    setFlashText();
                } else {
                    int distance = (x2 - x1);
                    binding.vvPlayVideo.seekTo(currentPosition + distance * 10);
                    setFlashText();
                }
            }
        }
    public void checkInternetWhilePlay() {
        if (!checkNetwork.isNetworkConnected(getBaseContext()) || !checkNetwork.isInternetAvailable(getBaseContext())) {
            AlertDialog alertDialog = new AlertDialog.Builder(PlayVideoActivity.this)
                    .setCancelable(false)
//                    .setTitle(R.string.tilte_dialog_check_network)
                    .setMessage(R.string.checkNetworkPlayVideo)
                    .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getBaseContext(),PlayVideoActivity.class));
                        }
                    })
                    .create();
            alertDialog.show();
        }
    }
}