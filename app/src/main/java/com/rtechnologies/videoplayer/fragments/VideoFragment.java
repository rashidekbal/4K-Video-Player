package com.rtechnologies.videoplayer.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtechnologies.videoplayer.activities.VideoPlayerActivity;
import com.rtechnologies.videoplayer.adapters.mediaRecyclerView.MediaRecyclerViewAdapter;
import com.rtechnologies.videoplayer.databinding.FragmentVideoBinding;
import com.rtechnologies.videoplayer.room.schema.MediaModel;
import com.rtechnologies.videoplayer.utils.ExoplayerUtil;
import com.rtechnologies.videoplayer.utils.PermissionUtil;
import com.rtechnologies.videoplayer.viewmodels.VideoViewModel;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {
    FragmentVideoBinding binding;
    VideoViewModel viewModel;
    ArrayList<MediaModel> mediaList;
    MediaRecyclerViewAdapter adapter;


    public VideoFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding=FragmentVideoBinding.inflate(inflater,container,false);
        init();
        setupRecyclerView();
        handleMediaLoad();
        return binding.getRoot();

    }

    private void handleMediaLoad() {
        if(!PermissionUtil.hasPermissions(requireActivity(),PermissionUtil.MediaPermissions)){
            return ;
        }
        observeMedia();
    }

    private void init() {
        this.viewModel= new ViewModelProvider(requireActivity()).get(VideoViewModel.class);
        this.mediaList=new ArrayList<>();
        adapter=new MediaRecyclerViewAdapter(mediaList,requireActivity(),this::handleMediaItemClicked);
    }
    @SuppressLint("NotifyDataSetChanged")
    private void observeMedia() {
        viewModel.getVideos().observe(requireActivity(),media->{
            if(!media.isEmpty()){
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.noRecentlyPlayedMedia.setVisibility(View.GONE);
                mediaList.clear();
                mediaList.addAll(media);
                adapter.notifyDataSetChanged();
            }
        });

    }
    private void setupRecyclerView(){
        LinearLayoutManager lm=new LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false);
        binding.recyclerView.setLayoutManager(lm);
        binding.recyclerView.setAdapter(adapter);
    }
    private void handleMediaItemClicked(int position){
        List<MediaItem> mediaItems= ExoplayerUtil.prepareMediaItem(requireActivity(),mediaList);
        long startPosition=0;
        ExoplayerUtil.setMediaList(mediaItems,position,startPosition);
        startActivity(new Intent(requireActivity(), VideoPlayerActivity.class));

    }
}