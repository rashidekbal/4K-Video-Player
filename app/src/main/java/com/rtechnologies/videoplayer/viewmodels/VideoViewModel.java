package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtechnologies.videoplayer.room.schema.MediaModel;

import com.rtechnologies.videoplayer.repo.MediaRepo;

import java.util.ArrayList;

public class VideoViewModel extends AndroidViewModel {
    private final MediaRepo mediaRepo;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        this.mediaRepo = new MediaRepo(application);
    }

    MutableLiveData<ArrayList<MediaModel>> mutableLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<MediaModel>> getVideos() {
        if (mutableLiveData.getValue() == null || mutableLiveData.getValue().isEmpty()) {
            fetchVideo();
        }
        return mutableLiveData;

    }

    private void fetchVideo() {
        mediaRepo.getVideo(data -> mutableLiveData.postValue(data));

    }
}
