package com.rtechnologies.videoplayer.repo;

import android.content.Context;

import com.rtechnologies.videoplayer.model.MediaModel;

import java.util.ArrayList;

public class VideosRepo {
    private final Context context;
    private final ArrayList<MediaModel> media=new ArrayList<>();

    public VideosRepo(Context context) {
        this.context = context;
    }

    public ArrayList<MediaModel> getMedia(){
        if(media.isEmpty()){
            fetchMedia();
        }
        return media;
    }

    private void fetchMedia() {
        media.add(new MediaModel(1,"media 1",true));
        media.add(new MediaModel(2,"media 2",true));
        media.add(new MediaModel(3,"media 3",true));
        media.add(new MediaModel(4,"media 4",true));
        media.add(new MediaModel(5,"media 5",true));
        media.add(new MediaModel(6,"media 6",true));
        media.add(new MediaModel(7,"media 7",true));
    }

}
