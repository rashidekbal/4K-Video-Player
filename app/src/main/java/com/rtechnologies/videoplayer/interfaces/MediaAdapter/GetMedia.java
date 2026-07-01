package com.rtechnologies.videoplayer.interfaces.MediaAdapter;

import com.rtechnologies.videoplayer.room.schema.MediaModel;

public interface GetMedia<T>{
    public void mediaCallback(T data );
}
