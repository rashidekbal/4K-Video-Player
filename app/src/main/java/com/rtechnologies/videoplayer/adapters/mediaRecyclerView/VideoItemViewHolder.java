package com.rtechnologies.videoplayer.adapters.mediaRecyclerView;

import android.content.Context;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.interfaces.MediaAdapter.OnItemSelected;
import com.rtechnologies.videoplayer.room.schema.MediaModel;
import com.rtechnologies.videoplayer.utils.TextFormatUtil;

public class VideoItemViewHolder extends RecyclerView.ViewHolder {
    MaterialTextView name, sub, duration;

    public VideoItemViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        sub = itemView.findViewById(R.id.sub);
        duration = itemView.findViewById(R.id.duration);

    }

    public static void bind(Context context, VideoItemViewHolder viewHolder, MediaModel media, OnItemSelected callback) {
        int position = viewHolder.getLayoutPosition();
        viewHolder.name.setText(media.getFileName());
        viewHolder.duration.setText(TextFormatUtil.getDurationFormatted(media.getDuration()));
        viewHolder.itemView.setOnClickListener(v -> callback.onItemSelected(position));


    }


}
