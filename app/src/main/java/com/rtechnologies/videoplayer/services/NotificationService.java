package com.rtechnologies.videoplayer.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

public class NotificationService {
    public static NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public static void createNotificationChannel(NotificationManager nm,String channelId, String channelName, int importance){
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setSound(null, null);
        channel.setLockscreenVisibility(android.app.Notification.VISIBILITY_PUBLIC);
        nm.createNotificationChannel(channel);
    }
}
