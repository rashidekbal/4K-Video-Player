package com.rtechnologies.videoplayer.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.constants.IntentKeys;
import com.rtechnologies.videoplayer.databinding.ActivityVideoPlayerBinding;
import com.rtechnologies.videoplayer.utils.ExoplayerUtil;
import com.rtechnologies.videoplayer.utils.ToastUtil;

public class VideoPlayerActivity extends AppCompatActivity {
    ActivityVideoPlayerBinding binding;
    int index;
    long currentSeekPosition;

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
        ExoplayerUtil.setPlayerView(binding.playerView);
        ExoplayerUtil.startPlayBack();
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
}