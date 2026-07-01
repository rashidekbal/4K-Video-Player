package com.rtechnologies.videoplayer.utils;

import android.content.Context;
import android.net.Uri;

import androidx.media3.common.AudioAttributes;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.media3.exoplayer.ExoPlayer;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.room.schema.MediaModel;

import java.util.ArrayList;
import java.util.List;

public class ExoplayerUtil {
    private static ExoPlayer exoPlayer;
    private static final AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.AUDIO_CONTENT_TYPE_UNKNOWN)
            .build();

    public static void init(Context context) {
        if (exoPlayer != null) return;
        exoPlayer = new ExoPlayer.Builder(context).build();
        exoPlayer.setHandleAudioBecomingNoisy(true);
        exoPlayer.setAudioAttributes(audioAttributes, true);
    }

    public static ExoPlayer getExoPlayer() {
        return exoPlayer;
    }

    public static void setMediaList(List<MediaItem> mediaList, int itemToPlay, long startPosition) {
        exoPlayer.setMediaItems(mediaList, itemToPlay, startPosition);
    }

    public static List<MediaItem> prepareMediaItem(Context context, ArrayList<MediaModel> mediaModels) {
        int drawableId = R.drawable.bg;
        Uri arkWorkUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + drawableId);
        List<MediaItem> mediaItems = new ArrayList<>();
        for (MediaModel media : mediaModels) {
            MediaItem item = new MediaItem.Builder()
                    .setUri(media.getUri())
                    .setMediaId(String.valueOf(media.getId()))
                    .setMediaMetadata(new MediaMetadata.Builder()
                            .setTitle(media.getFileName())
                            .setArtist(media.getArtist())
                            .setAlbumTitle(media.getAlbum())
                            .setArtworkUri(arkWorkUri)
                            .build())
                    .build();
            mediaItems.add(item);
        }
        return mediaItems;
    }


}
