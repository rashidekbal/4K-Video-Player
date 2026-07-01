package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rtechnologies.videoplayer.room.Db;
import com.rtechnologies.videoplayer.room.schema.MediaModel;


import java.util.List;

public class RecentPlayedViewModel extends AndroidViewModel {

    public RecentPlayedViewModel(@NonNull Application application) {
        super(application);

    }
    public LiveData<List<MediaModel>> getRecents(){

        return Db.getInstance().playHistoryDao().getHistory();

    }


}
