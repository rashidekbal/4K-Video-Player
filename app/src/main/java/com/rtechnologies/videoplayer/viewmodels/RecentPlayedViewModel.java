package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.repo.RecentsRepo;

import java.util.ArrayList;

public class RecentPlayedViewModel extends AndroidViewModel {
    private final RecentsRepo repo;
    public RecentPlayedViewModel(@NonNull Application application) {
        super(application);
        this.repo=new RecentsRepo(application);
    }
    MutableLiveData<ArrayList<MediaModel>> mutableLiveData =new MutableLiveData<>();

    public LiveData<ArrayList<MediaModel>> getRecents(){
        if(mutableLiveData.getValue()==null||mutableLiveData.getValue().isEmpty()){
            fetchRecents();
        }
        return mutableLiveData;

    }

    private void fetchRecents() {
        ArrayList<MediaModel> temp=new ArrayList<>();
        if(mutableLiveData.getValue()!=null)temp.addAll(mutableLiveData.getValue());
        temp.addAll( repo.getMedia());
        mutableLiveData.postValue(temp);
    }
}
