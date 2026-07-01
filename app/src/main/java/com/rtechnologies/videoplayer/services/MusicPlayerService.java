package com.rtechnologies.videoplayer.services;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.session.MediaSession;
import androidx.media3.session.MediaSessionService;


public class MusicPlayerService extends MediaSessionService {
    private MediaSession mediaSession;

    @Nullable
    @Override
    public MediaSession onGetSession(@NonNull MediaSession.ControllerInfo controllerInfo) {
        return mediaSession;
    }

    @UnstableApi
    @Override
    public void onCreate() {
        super.onCreate();
        ExoPlayer exoPlayer = new ExoPlayer.Builder(this).build();
        exoPlayer.setSeekForwardIncrementMs(10000);
        exoPlayer.setSeekBackIncrementMs(10000);
        this.mediaSession = new MediaSession.Builder(this, exoPlayer).build();
    }


    @Override
    public void onDestroy() {
        mediaSession.getPlayer().release();
        mediaSession.release();
        mediaSession = null;
        super.onDestroy();
    }
}
