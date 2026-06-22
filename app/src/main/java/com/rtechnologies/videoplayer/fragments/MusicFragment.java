package com.rtechnologies.videoplayer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtechnologies.videoplayer.adapters.mediaRecyclerView.MediaRecyclerViewAdapter;
import com.rtechnologies.videoplayer.databinding.FragmentMusicBinding;
import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.viewmodels.MusicViewModel;

import java.util.ArrayList;


public class MusicFragment extends Fragment {
    FragmentMusicBinding binding;
    ArrayList<MediaModel> mediaList;
    MediaRecyclerViewAdapter adapter;
    MusicViewModel viewModel;



    public MusicFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMusicBinding.inflate(inflater,container,false);
        init();
        setupRecyclerView();
        observeMedia();
        return binding.getRoot();
    }
    private void init() {
        this.viewModel= new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        this.mediaList=new ArrayList<>();
        adapter=new MediaRecyclerViewAdapter(mediaList,requireActivity());
    }
    private void observeMedia() {
        viewModel.getMusic().observe(requireActivity(),media->{
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
}