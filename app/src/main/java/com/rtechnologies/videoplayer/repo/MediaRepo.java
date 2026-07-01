package com.rtechnologies.videoplayer.repo;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.rtechnologies.videoplayer.interfaces.MediaAdapter.GetMedia;
import com.rtechnologies.videoplayer.room.schema.MediaModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MediaRepo {
    private final ExecutorService executorService;
    private final Context context;
    private final Uri collection;
    private final String SortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";
    private final String[] audioProjection = {
            MediaStore.Audio.AudioColumns._ID,
            MediaStore.Audio.AudioColumns.DISPLAY_NAME,
            MediaStore.Audio.AudioColumns.DATE_ADDED,
            MediaStore.Audio.AudioColumns.DURATION,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.AudioColumns.ARTIST
    };
    private final String[] videoProjection = {
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATE_ADDED,
            MediaStore.Video.VideoColumns.DURATION
    };

    public MediaRepo(Context context) {
        this.context = context;
        collection = MediaStore.Files.getContentUri("external");
        this.executorService= Executors.newSingleThreadExecutor();
    }

    public void getMusic(GetMedia<ArrayList<MediaModel>> mediaCallback) {
        executorService.execute(() -> {
            ArrayList<MediaModel> temp = getAudios(getMediaFilter("audio"));
            mediaCallback.mediaCallback(temp);
        });


    }
    public void getVideo(GetMedia<ArrayList<MediaModel>> mediaCallback) {
        executorService.execute(() -> {
            ArrayList<MediaModel> temp = getVideos(getMediaFilter("video"));
            mediaCallback.mediaCallback(temp);
        });
    }

    private String getMediaFilter(String type) {
        if (type.equals("video")) {
            return MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        } else return MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
                MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
    }

    private ArrayList<MediaModel> getAudios(String filter) {
        ArrayList<MediaModel> temp = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(collection, audioProjection, filter, null, SortOrder);
        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID);
            int fileNameColumn =cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME);
            int durationColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION);
            int dateColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATE_ADDED);
            int albumColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM);
            int artistColumn=cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                String name=cursor.getString(fileNameColumn);
                long dateAdded=cursor.getLong(dateColumn);
                String album=cursor.getString(albumColumn);
                String artist=cursor.getString(artistColumn);
                long duration= cursor.getLong(durationColumn);
                Uri uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                temp.add(new MediaModel(id,name,false,uri.toString(),duration,album,artist,dateAdded,new Date().getTime(),0));
            }
            cursor.close();

        }
        return temp;
    }
    private ArrayList<MediaModel> getVideos(String filter){
        ArrayList<MediaModel> temp = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(collection,videoProjection,filter, null, SortOrder);
        if(cursor!=null){
            int idColumn=cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns._ID);
            int nameColumn=cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DISPLAY_NAME);
            int dateColumn=cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DATE_ADDED);
            int durationColumn=cursor.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION);
            while (cursor.moveToNext()){
                long id=cursor.getLong(idColumn);
                String name=cursor.getString(nameColumn);
                long dateAdded=cursor.getLong(dateColumn);
                long duration=cursor.getLong(durationColumn);
                Uri uri=ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,id);
                temp.add(new MediaModel(id,name,false,uri.toString(),duration,null,null,dateAdded,new Date().getTime(),0));
            }

            cursor.close();
        }
        return temp;
    }


}

