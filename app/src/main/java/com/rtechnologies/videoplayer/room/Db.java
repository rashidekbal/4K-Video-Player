package com.rtechnologies.videoplayer.room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rtechnologies.videoplayer.Application;
import com.rtechnologies.videoplayer.room.dao.PlayHistory;
import com.rtechnologies.videoplayer.room.schema.PlayHistorySchema;


@Database(entities = {PlayHistorySchema.class},version = 1,exportSchema = false)
public abstract class Db extends RoomDatabase {
    public static final String DB_NAME = "PlayerDb";
    public static Db instance;

    public static synchronized Db getInstance() {
        if(instance == null) {
            instance= Room.databaseBuilder(Application.getContext(),Db.class,DB_NAME).build();

        }
        return instance;
    }
    public abstract PlayHistory playHistoryDao();
}
