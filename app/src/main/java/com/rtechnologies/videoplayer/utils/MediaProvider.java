package com.rtechnologies.videoplayer.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.rtechnologies.videoplayer.model.MediaModel;

import java.util.ArrayList;

public class MediaProvider {
    Context context;
    private final Uri collection;
    String SortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";
    private final String[] projection = {
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.DURATION
    };

    public MediaProvider(Context context) {
        this.context = context;
        collection = MediaStore.Files.getContentUri("external");
    }

    public ArrayList<MediaModel> getMusic() {
        return getMedia(getMediaFilter("music"));
    }
    public ArrayList<MediaModel> getVideo() {
        return getMedia(getMediaFilter("video"));
    }

    public String getMediaFilter(String type) {
        if (type.equals("video")) {
            return MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
                    MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        } else return MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
                MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
    }

    public ArrayList<MediaModel> getMedia(String filter) {
        ArrayList<MediaModel> temp = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(collection, projection, filter, null, SortOrder);
        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
            int fileNameColumn =cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME);
            int mediaTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MEDIA_TYPE);
            int durationColumn=cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DURATION);
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                int mediaType = cursor.getInt(mediaTypeColumn);
                long duration= cursor.getLong(durationColumn);
                Uri uri = (mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO) ?
                        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
                        : ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                temp.add(new MediaModel(id, uri, cursor.getString(fileNameColumn), mediaType == MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO,duration));
            }
            cursor.close();

        }
        return temp;
    }


}

