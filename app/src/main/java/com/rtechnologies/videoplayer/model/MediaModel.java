package com.rtechnologies.videoplayer.model;

public class MediaModel {
    private final int id;
    private String fileName;
    private boolean isVideo;

    public MediaModel(int id, String fileName, boolean isVideo) {
        this.id = id;
        this.fileName = fileName;
        this.isVideo = isVideo;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getFileName() {
        return fileName;
    }

    public int getId() {
        return id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
