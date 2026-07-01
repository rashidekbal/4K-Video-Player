package com.rtechnologies.videoplayer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rtechnologies.videoplayer.room.schema.MediaModel;

import java.util.List;

@Dao
public interface PlayHistory {
    @Insert
    public void insertHistory(MediaModel media);
    @Query("select * from playHistory order by lastPlayed desc")
    public LiveData<List<MediaModel>> getHistory();
    @Query("update playHistory set lastPlayed=:lastPlayed and lastPlayedPosition=:lastPlayedPosition where id=:id")
    public void updateHistory(int id,long lastPlayed,long lastPlayedPosition);
    @Query("select * from playHistory where mediaId=:mediaId")
    public MediaModel getHistoryById(long mediaId);
}
