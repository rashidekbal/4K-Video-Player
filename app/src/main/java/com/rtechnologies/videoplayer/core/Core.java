package com.rtechnologies.videoplayer.core;

import android.app.NotificationManager;
import android.content.Context;

import com.rtechnologies.videoplayer.constants.NotificationChannelId;

import com.rtechnologies.videoplayer.services.NotificationService;

public class Core {
    public static void init(Context context){
        NotificationService.createNotificationChannel(NotificationService.getNotificationManager(context), NotificationChannelId.MUSIC_NOTIFICATION_CHANNEL_ID.toString(), "Music", NotificationManager.IMPORTANCE_HIGH);

    }

}
