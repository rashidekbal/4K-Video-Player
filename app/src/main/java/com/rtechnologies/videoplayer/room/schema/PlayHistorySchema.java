package com.rtechnologies.videoplayer.room.schema;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playHistory")
public class PlayHistorySchema {
    @PrimaryKey(autoGenerate = true)
     private long entryId;

    public PlayHistorySchema(long entryId) {
        this.entryId = entryId;
    }

    public long getEntryId() {
        return entryId;
    }

    public void setEntryId(long entryId) {
        this.entryId = entryId;
    }
}
