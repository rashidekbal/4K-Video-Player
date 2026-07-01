package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtechnologies.videoplayer.room.schema.MediaModel;
import com.rtechnologies.videoplayer.repo.MediaRepo;

import java.util.ArrayList;


public class MusicViewModel extends AndroidViewModel {

    private final MediaRepo mediaRepo;
    public MusicViewModel(@NonNull Application application) {
        super(application);

        this.mediaRepo =new MediaRepo(application);
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
        mediaRepo.getMusic(data->{
            temp.addAll(data);
            mutableLiveData.postValue(temp);});

    }
}
