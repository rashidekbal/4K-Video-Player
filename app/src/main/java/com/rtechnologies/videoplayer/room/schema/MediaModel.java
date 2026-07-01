package com.rtechnologies.videoplayer.room.schema;


import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jspecify.annotations.Nullable;
@Entity(tableName = "playHistory")
public class MediaModel {
    @PrimaryKey(autoGenerate = true)
    private  long id;
    @ColumnInfo(name ="mediaId")
    private  long mediaId;
    @ColumnInfo(name = "fileName")
    private String fileName;
    @ColumnInfo(name = "isVideo")
    private boolean isVideo;
    @ColumnInfo(name = "uri")
    private String uri;
    @ColumnInfo(name = "duration")
    private long duration;
    @ColumnInfo(name = "album")
    private String album;
    @ColumnInfo(name = "artist")
    private String artist;
    @ColumnInfo(name = "dateAdded")
    private long dateAdded;
    @ColumnInfo(name = "lastPlayed")
    private long lastPlayed;
    @ColumnInfo(name = "lastPlayedPosition")
    private long lastPlayedPosition;


    public MediaModel(long id,long mediaId ,String fileName, boolean isVideo, String uri, long duration, @Nullable String album, @Nullable String artist, long dateAdded, long lastPlayed,long lastPlayedPosition) {
        this.id = id;
        this.fileName = fileName;
        this.isVideo = isVideo;
        this.uri = uri;
        this.duration = duration;
        this.album = album==null?"unknown":album;
        this.artist = artist==null?"unknown":artist;
        this.dateAdded = dateAdded;
        this.lastPlayed = lastPlayed;
        this.lastPlayedPosition=lastPlayedPosition;
        this.mediaId=mediaId;
    }
    @Ignore
    public MediaModel(long mediaId ,String fileName, boolean isVideo, String uri, long duration, @Nullable String album, @Nullable String artist, long dateAdded, long lastPlayed,long lastPlayedPosition) {
        this.fileName = fileName;
        this.isVideo = isVideo;
        this.uri = uri;
        this.duration = duration;
        this.album = album==null?"unknown":album;
        this.artist = artist==null?"unknown":artist;
        this.dateAdded = dateAdded;
        this.lastPlayed = lastPlayed;
        this.lastPlayedPosition=lastPlayedPosition;
        this.mediaId=mediaId;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getFileName() {
        return fileName;
    }

    public long getId() {
        return id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(@Nullable String artist) {
        this.artist=artist==null?"unknown":artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(@Nullable String album) {
        this.album=album==null?"unknown":album;
    }

    public long getLastPlayedPosition() {
        return lastPlayedPosition;
    }

    public void setLastPlayedPosition(long lastPlayedPosition) {
        this.lastPlayedPosition = lastPlayedPosition;
    }

    public long getMediaId() {
        return mediaId;
    }

    public void setMediaId(long mediaId) {
        this.mediaId = mediaId;
    }
}
