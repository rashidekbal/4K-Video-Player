package com.rtechnologies.videoplayer.services;

import android.content.Context;
import androidx.media3.exoplayer.ExoPlayer;

import com.rtechnologies.videoplayer.model.MediaModel;

import java.util.ArrayList;

public class ExoplayerService {
    private static ArrayList<MediaModel> mediaModels;
    private static int currentIndex;
    private static ExoPlayer exoPlayer;
    public static void init(Context context){
        mediaModels=new ArrayList<>();
        currentIndex=-1;
        if(exoPlayer==null){
            exoPlayer=new ExoPlayer.Builder(context).build();
            exoPlayer.setHandleAudioBecomingNoisy(true);
            exoPlayer.setSeekBackIncrementMs(15000);
            exoPlayer.setSeekForwardIncrementMs(15000);
        }
    }

    public static ExoPlayer getExoPlayer(){
        return exoPlayer;
    }
    public static void playMusic(){


    }
    public static void playVideo(){

    }
    public static void pause(){
        if(exoPlayer==null)return;
        exoPlayer.setPlayWhenReady(false);
        exoPlayer.pause();

    }
    public static void resume(){
        if(exoPlayer==null)return;
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.play();
    }
    public static void seekTo(long position){
        if(exoPlayer==null)return;
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.seekTo(position);

    }
    public static void next(){
        if(exoPlayer==null)return;
        exoPlayer.seekToNextMediaItem();

    }
    public static void prev(){
        if(exoPlayer==null)return;
        exoPlayer.seekToPreviousMediaItem();


    }
    public static void setRepeatMode(int repeatMode){
        if(exoPlayer==null)return;
        exoPlayer.setRepeatMode(repeatMode);
    }



}
