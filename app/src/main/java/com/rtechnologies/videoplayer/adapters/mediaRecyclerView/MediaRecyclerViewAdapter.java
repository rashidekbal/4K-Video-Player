package com.rtechnologies.videoplayer.adapters.mediaRecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.interfaces.MediaAdapter.OnItemSelected;
import com.rtechnologies.videoplayer.room.schema.MediaModel;

import java.util.ArrayList;


public class MediaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<MediaModel> mediaList;
    Context context;
    OnItemSelected onItemSelected;
    private final int MEDIA_VIDEO=0;
    private final int MEDIA_AUDIO=1;

    public MediaRecyclerViewAdapter(ArrayList<MediaModel> mediaList, Context context, OnItemSelected onItemSelected) {
        this.mediaList = mediaList;
        this.context = context;
        this.onItemSelected=onItemSelected;
    }

    @Override
    public int getItemViewType(int position) {
        if(mediaList.get(position).isVideo())return MEDIA_VIDEO;
        return MEDIA_AUDIO;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mediaList.get(position).getId();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater=LayoutInflater.from(context);
        if(viewType==MEDIA_VIDEO) {
            v = inflater.inflate(R.layout.video_card, parent, false);
            return new VideoItemViewHolder(v);

        }
        v=inflater.inflate(R.layout.music_card,parent,false);
        return new MusicItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VideoItemViewHolder) {
            VideoItemViewHolder.bind(context,(VideoItemViewHolder) holder,mediaList.get(position),onItemSelected);
            return;
        }
        MusicItemViewHolder.bind(context,(MusicItemViewHolder) holder,mediaList.get(position),onItemSelected);

    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }
}
