package com.rtechnologies.videoplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.databinding.ActivityVideoPlayerBinding;
import com.rtechnologies.videoplayer.utils.ExoplayerUtil;
import com.rtechnologies.videoplayer.utils.TextFormatUtil;
import com.rtechnologies.videoplayer.utils.ToastUtil;

public class VideoPlayerActivity extends AppCompatActivity {
    ActivityVideoPlayerBinding binding;
    Handler handler=new Handler(Looper.getMainLooper());
    Player.Listener playerListener =new Player.Listener() {
        @Override
        public void onPlaybackStateChanged(int playbackState) {
            if(playbackState==Player.STATE_READY){
                handleReadyToPlay();

            }



        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            if(isPlaying){
                binding.playPauseButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.pause_icon_no_bg,null));
                setPlayProgressListener();
                return;
            }
            binding.playPauseButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.play_icon_no_bg,null));
            removePlayProgressListener();
        }


    };
    SeekBar.OnSeekBarChangeListener seekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(!fromUser)return;
            if(ExoplayerUtil.getExoPlayer()==null)return;
            long progressChange=(long)progress*ExoplayerUtil.getExoPlayer().getDuration()/seekBar.getMax();
            if(ExoplayerUtil.getExoPlayer().getDuration()-5000>progressChange){
//                a 5 sec window for not accidentally changing the mediaItem
                ExoplayerUtil.getExoPlayer().seekTo(progressChange);
            }
            binding.currentDuration.setText(TextFormatUtil.getDurationFormatted(progressChange));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.binding=ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setEventListeners();

    }




    private void init() {
        if(ExoplayerUtil.getExoPlayer()==null){
            handleExoPlayerError();
            return;
        }
        if(ExoplayerUtil.getExoPlayer().getMediaItemCount()==0){
            handleExoPlayerError();
            return;
        }
        ExoplayerUtil.getExoPlayer().prepare();
        ExoplayerUtil.getExoPlayer().addListener(playerListener);
    }

    private void handleExoPlayerError() {
        ToastUtil.toastShort(this,"Something went wrong");
    }


    @Override
    protected void onPause() {
        super.onPause();
        ExoplayerUtil.getExoPlayer().pause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExoplayerUtil.getExoPlayer().stop();
    }
    private void handleReadyToPlay() {
        MediaItem currentMediaItem=ExoplayerUtil.getExoPlayer().getCurrentMediaItem();
        if(currentMediaItem==null)return;
        binding.playerView.setVisibility(View.VISIBLE);
        binding.bufferView.setVisibility(View.GONE);
        binding.playerView.setPlayer(ExoplayerUtil.getExoPlayer());
        binding.overlay.setVisibility(View.VISIBLE);
        binding.seekbar.setMax((int)ExoplayerUtil.getExoPlayer().getDuration());
        binding.currentDuration.setText(TextFormatUtil.getDurationFormatted(ExoplayerUtil.getExoPlayer().getCurrentPosition()));
        binding.maxDuration.setText(TextFormatUtil.getDurationFormatted(ExoplayerUtil.getExoPlayer().getDuration()));
        binding.titleText.setText(currentMediaItem.mediaMetadata.title);
        ExoplayerUtil.getExoPlayer().play();
        handleControlsHiding();
    }

    private void setPlayProgressListener() {
        handler.post(handlePlayDurationTracking());
    }
    private void removePlayProgressListener(){
        handler.removeCallbacks(handlePlayDurationTracking());
    }


    private void handleControlsHiding(){
        handler.removeCallbacks(hideControls());
        handler.postDelayed(hideControls(),10000);
    }

    private Runnable hideControls() {
        return () -> binding.overlay.setVisibility(View.GONE);
    }
    private void showControls() {
        binding.overlay.setVisibility(View.VISIBLE);
        handleControlsHiding();
    }
    private void setEventListeners() {
        binding.playerView.setOnClickListener(this::handlePlayerViewClick);
        binding.overlay.setOnClickListener(this::handleOverLayClick);
        binding.playPauseButton.setOnClickListener(this::handlePlayPauseBtnClick);
        binding.backBtn.setOnClickListener(v->finish());
        binding.seekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        binding.nextBtn.setOnClickListener(v -> ExoplayerUtil.getExoPlayer().seekToNextMediaItem());
        binding.prevBtn.setOnClickListener(v->ExoplayerUtil.getExoPlayer().seekToPreviousMediaItem());

    }

    private void handlePlayPauseBtnClick(View view) {
        if(ExoplayerUtil.getExoPlayer().isPlaying()){
            ExoplayerUtil.getExoPlayer().pause();
            return;
        }
        ExoplayerUtil.getExoPlayer().setPlayWhenReady(true);
    }

    private void handleOverLayClick(View view) {
        binding.overlay.setVisibility(View.GONE);
    }

    private void handlePlayerViewClick(View view) {
        if(ExoplayerUtil.getExoPlayer().getPlaybackState()==Player.STATE_BUFFERING)return;
        showControls();

    }
    private Runnable handlePlayDurationTracking(){
        return ()->{
            binding.seekbar.setProgress((int)ExoplayerUtil.getExoPlayer().getCurrentPosition());
            binding.currentDuration.setText(TextFormatUtil.getDurationFormatted(ExoplayerUtil.getExoPlayer().getCurrentPosition()));
            handler.postDelayed(handlePlayDurationTracking(),50);
        };
    }

}