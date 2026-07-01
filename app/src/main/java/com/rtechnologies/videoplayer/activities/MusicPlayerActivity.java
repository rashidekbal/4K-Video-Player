package com.rtechnologies.videoplayer.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.HapticFeedbackConstants;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.session.MediaController;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.databinding.ActivityMusicPlayerBinding;
import com.rtechnologies.videoplayer.utils.TextFormatUtil;
import com.rtechnologies.videoplayer.viewmodels.MusicSessionViewModel;

public class MusicPlayerActivity extends AppCompatActivity {
    ActivityMusicPlayerBinding binding;
    MusicSessionViewModel viewmodel;
    Handler handler;
    Runnable progressRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        observeMusicSession();
        handleEvents();
    }

    private void init() {
        handler = new Handler(Looper.getMainLooper());
        this.viewmodel = new ViewModelProvider(this).get(MusicSessionViewModel.class);
        this.viewmodel.connect(this);
    }

    private void handleEvents() {
        binding.closeBtn.setOnClickListener(this::handleCloseBtnClick);
        binding.playPauseBtn.setOnClickListener(this::handlePlayPause);
        binding.forwardBtn.setOnClickListener(this::handleSeekForward);
        binding.backwardBtn.setOnClickListener(this::handleSeekBackward);
        binding.nextBtn.setOnClickListener(this::handleNextMediaBtnClicked);
        binding.prevBtn.setOnClickListener(this::handlePrevMediaBtnClicked);
        binding.progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (viewmodel.getController() == null) return;
                MediaController controller = viewmodel.getController();
                if (!fromUser) return;
                long progressChange = (long) progress * controller.getDuration() / seekBar.getMax();
                controller.seekTo(progressChange);
                binding.currentProgress.setText(TextFormatUtil.getDurationFormatted(progressChange));


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });

    }

    private void handleCloseBtnClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        view.playSoundEffect(SoundEffectConstants.CLICK);
        finish();
    }

    private void handlePrevMediaBtnClicked(View view) {
        if (viewmodel.getController() == null) return;

        MediaController controller = viewmodel.getController();
        controller.seekToPreviousMediaItem();
    }

    private void handleNextMediaBtnClicked(View view) {
        if (viewmodel.getController() == null) return;

        MediaController controller = viewmodel.getController();
        controller.seekToNextMediaItem();
    }

    private void handleSeekBackward(View view) {
        if (viewmodel.getController() == null) return;
        MediaController controller = viewmodel.getController();
        controller.seekBack();
        binding.progressBar.setProgress((int) controller.getCurrentPosition());
        binding.currentProgress.setText(TextFormatUtil.getDurationFormatted(controller.getCurrentPosition()));
    }

    private void handleSeekForward(View view) {
        if (viewmodel.getController() == null) return;
        MediaController controller = viewmodel.getController();
        controller.seekForward();
        binding.progressBar.setProgress((int) controller.getCurrentPosition());
        binding.currentProgress.setText(TextFormatUtil.getDurationFormatted(controller.getCurrentPosition()));
    }

    private void handlePlayPause(View view) {
        view.playSoundEffect(SoundEffectConstants.CLICK);
        if (viewmodel.getController() == null) return;
        MediaController controller = viewmodel.getController();
        if (controller.isPlaying()) {
            stopProgressListener();
            controller.pause();
            return;
        }
        controller.play();
        handleProgressListener();

    }


    private void observeMusicSession() {
        this.viewmodel.getCurrentMediaItem().observe(this, this::handleMediaItemChange);
        this.viewmodel.isPlaying().observe(this, this::handleIsPlaying);

    }

    private void handleIsPlaying(Boolean isPlaying) {
        if (isPlaying == null) return;
        if (isPlaying) {
            binding.playPauseBtn.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.pause_icon));

            return;
        }
        binding.playPauseBtn.setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.play_icon));


    }

    private void handleMediaItemChange(MediaItem mediaItem) {
        if (mediaItem == null) return;
        binding.progressBar.setProgress(0);
        binding.currentProgress.setText("0:00");
        binding.fileName.setText(mediaItem.mediaMetadata.title);
        binding.album.setText(mediaItem.mediaMetadata.artist);
        this.handleProgressListener();


    }

    private void handleProgressListener() {
        progressRunnable = () -> {
            if (viewmodel.getController() == null) return;
            MediaController controller = viewmodel.getController();
            if (controller.getDuration() >= 0) {
                binding.progressBar.setMax((int) controller.getDuration());
                binding.maxProgress.setText(TextFormatUtil.getDurationFormatted(controller.getDuration()));
                binding.progressBar.setProgress((int) controller.getCurrentPosition());
                binding.currentProgress.setText(TextFormatUtil.getDurationFormatted(controller.getCurrentPosition()));
            }
            handler.postDelayed(progressRunnable, 50);
        };
        handler.post(progressRunnable);
    }

    private void stopProgressListener() {
        if (progressRunnable != null) {
            handler.removeCallbacks(progressRunnable);
            progressRunnable = null;
        }
    }


}