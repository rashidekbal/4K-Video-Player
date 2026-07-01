package com.rtechnologies.videoplayer.room;

import com.rtechnologies.videoplayer.interfaces.MediaAdapter.GetMedia;
import com.rtechnologies.videoplayer.room.schema.MediaModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbHelper {
   private static final ExecutorService executorService=Executors.newSingleThreadExecutor();


    public static void insertHistory(MediaModel media) {
        executorService.execute(()->Db.getInstance().playHistoryDao().insertHistory(media));
    }
    public static void updateHistory(int id, long lastPlayed, long lastPlayedPosition) {
        executorService.execute(()->Db.getInstance().playHistoryDao().updateHistory(id,lastPlayed,lastPlayedPosition));
    }
    public static void getHistoryById(long mediaId, GetMedia<MediaModel> mediaCallback){
        executorService.execute(()->{
            MediaModel mediaModel=Db.getInstance().playHistoryDao().getHistoryById(mediaId);
            mediaCallback.mediaCallback(mediaModel);
        });

    }
}
