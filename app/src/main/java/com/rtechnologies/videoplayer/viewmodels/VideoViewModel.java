package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.repo.VideosRepo;

import java.util.ArrayList;

public class VideoViewModel extends AndroidViewModel {
    VideosRepo repo;
    public VideoViewModel(@NonNull Application application) {
        super(application);
        this.repo=new VideosRepo(application);
    }
    MutableLiveData<ArrayList<MediaModel>> mutableLiveData =new MutableLiveData<>();

    public LiveData<ArrayList<MediaModel>> getVideos(){
        if(mutableLiveData.getValue()==null||mutableLiveData.getValue().isEmpty()){
            fetchVideo();
        }
        return mutableLiveData;

    }

    private void fetchVideo() {
        ArrayList<MediaModel> temp=new ArrayList<>();
        if(mutableLiveData.getValue()!=null)temp.addAll(mutableLiveData.getValue());
        temp.addAll( repo.getMedia());
        mutableLiveData.postValue(temp);
    }
}
