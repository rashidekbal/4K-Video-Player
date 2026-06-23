package com.rtechnologies.videoplayer.repo;

import android.content.Context;

import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.utils.MediaProvider;

import java.util.ArrayList;

public class VideosRepo {
    private final MediaProvider mediaProvider;
    private final ArrayList<MediaModel> media=new ArrayList<>();

    public VideosRepo(Context context) {
        this.mediaProvider=new MediaProvider(context);
    }

    public ArrayList<MediaModel> getMedia(){
        if(media.isEmpty()){
            fetchMedia();
        }
        return media;
    }

    private void fetchMedia() {
        media.addAll(mediaProvider.getVideo());

    }

}
