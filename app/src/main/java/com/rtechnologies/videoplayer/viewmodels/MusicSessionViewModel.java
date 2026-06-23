package com.rtechnologies.videoplayer.viewmodels;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.media3.common.MediaItem;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.media3.common.Player;
import androidx.media3.session.MediaController;
import androidx.media3.session.SessionToken;
import com.google.common.util.concurrent.ListenableFuture;
import com.rtechnologies.videoplayer.services.MusicPlayerService;

public class MusicSessionViewModel extends AndroidViewModel {
    private MediaController controller;
    private final MutableLiveData<MediaItem> currentMediaItem=new MutableLiveData<>();
    private final MutableLiveData<Boolean> playing=new MutableLiveData<>();
    public MusicSessionViewModel(@NonNull Application application) {
        super(application);

    }
    public void connect(Context application){
        if(controller!=null)return;
        SessionToken sessionToken=new SessionToken(application,new ComponentName(application, MusicPlayerService.class));
        ListenableFuture<MediaController>future=new MediaController.Builder(application,sessionToken).buildAsync();
        future.addListener(()->{
            try{
                controller=future.get();
                playing.setValue(controller.isPlaying());
                currentMediaItem.setValue(controller.getCurrentMediaItem());
                controller.addListener(new Player.Listener() {
                    @Override
                    public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                        currentMediaItem.postValue(mediaItem);
                    }

                    @Override
                    public void onIsPlayingChanged(boolean isPlaying) {
                        playing.setValue(isPlaying);
                    }
                });


            }catch (Exception e){
                e.printStackTrace();
            }

        }, ContextCompat.getMainExecutor(application));

    }

    public LiveData<MediaItem> getCurrentMediaItem(){
        return currentMediaItem;
    }
    public LiveData<Boolean> isPlaying(){
        return playing;
    }
    public MediaController getController(){
        return controller;
    }


}
