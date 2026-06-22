package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.repo.MusicRepo;

import java.util.ArrayList;


public class MusicViewModel extends AndroidViewModel {
    private final MusicRepo repo;
    public MusicViewModel(@NonNull Application application) {
        super(application);
        this.repo=new MusicRepo(application);
    }
    MutableLiveData<ArrayList<MediaModel>> mutableLiveData =new MutableLiveData<>();

    public LiveData<ArrayList<MediaModel>> getMusic(){
        if(mutableLiveData.getValue()==null||mutableLiveData.getValue().isEmpty()){
            fetchMusic();
        }
        return mutableLiveData;

    }

    private void fetchMusic() {
        ArrayList<MediaModel> temp=new ArrayList<>();
        if(mutableLiveData.getValue()!=null)temp.addAll(mutableLiveData.getValue());
        temp.addAll( repo.getMedia());
        mutableLiveData.postValue(temp);
    }
}
