package com.rtechnologies.videoplayer.repo;

import android.content.Context;
import android.net.Uri;

import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.utils.MediaProvider;

import java.util.ArrayList;

public class RecentsRepo {
    private final Context context;
    MediaProvider mediaProvider;
    private final ArrayList<MediaModel> media=new ArrayList<>();

    public RecentsRepo(Context context) {
        this.context = context;
        mediaProvider=new MediaProvider(context);
    }

    public ArrayList<MediaModel> getMedia(){
        if(media.isEmpty()){
            fetchMedia();
        }
        return media;
    }

    private void fetchMedia() {
        media.addAll(new ArrayList<>());
    }


}
