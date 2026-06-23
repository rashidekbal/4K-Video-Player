package com.rtechnologies.videoplayer.model;

import android.net.Uri;

import java.net.URI;

public class MediaModel {
    private final long id;
    private String fileName;
    private boolean isVideo;
    private Uri uri;
    private long duration;

    public MediaModel(long id, String fileName, boolean isVideo) {
        this.id = id;
        this.fileName = fileName;
        this.isVideo = isVideo;

    }
    public MediaModel(long id, Uri uri,String fileName, boolean isVideo,long duration) {
        this.id = id;
        this.fileName = fileName;
        this.isVideo = isVideo;
        this.uri=uri;
        this.duration=duration;

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

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
