package com.rtechnologies.videoplayer.fragments;

import static com.google.common.reflect.Reflection.getPackageName;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;
import androidx.media3.common.MediaMetadata;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.adapters.mediaRecyclerView.MediaRecyclerViewAdapter;
import com.rtechnologies.videoplayer.databinding.FragmentMusicBinding;
import com.rtechnologies.videoplayer.model.MediaModel;
import com.rtechnologies.videoplayer.utils.PermissionUtil;
import com.rtechnologies.videoplayer.utils.ToastUtil;
import com.rtechnologies.videoplayer.viewmodels.MusicSessionViewModel;
import com.rtechnologies.videoplayer.viewmodels.MusicViewModel;

import java.util.ArrayList;
import java.util.List;


public class MusicFragment extends Fragment {
    FragmentMusicBinding binding;
    ArrayList<MediaModel> mediaList;
    MediaRecyclerViewAdapter adapter;
    MusicViewModel viewModel;
    MusicSessionViewModel musicSessionViewModel;



    public MusicFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentMusicBinding.inflate(inflater,container,false);
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
        this.viewModel= new ViewModelProvider(requireActivity()).get(MusicViewModel.class);
        this.mediaList=new ArrayList<>();
        adapter=new MediaRecyclerViewAdapter(mediaList, requireActivity(),this::handleMediaItemClicked);
        this.musicSessionViewModel=new ViewModelProvider(requireActivity()).get(MusicSessionViewModel.class);
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
    private void handleMediaItemClicked(int position){
        if(musicSessionViewModel.getController()==null){
            ToastUtil.toastShort(requireActivity(),"Controller is null");
            return;
        }
        List<MediaItem> mediaItems=formMediaItems();
        long startPosition=0;
        musicSessionViewModel.getController().setMediaItems(mediaItems,position,startPosition);
        musicSessionViewModel.getController().prepare();
        musicSessionViewModel.getController().setPlayWhenReady(true);
        musicSessionViewModel.getController().play();


    }

    private List<MediaItem> formMediaItems() {
        int drawableId = R.drawable.bg;
        Uri arkWorkUri = Uri.parse("android.resource://" +requireActivity().getPackageName() + "/" + drawableId);
        List<MediaItem> mediaItems=new ArrayList<>();
        for(MediaModel media:mediaList){
            MediaItem item=new MediaItem.Builder()
                    .setUri(media.getUri())
                    .setMediaId(String.valueOf(media.getId()))
                    .setMediaMetadata(new MediaMetadata.Builder()
                            .setTitle(media.getFileName())
                            .setArtworkUri(arkWorkUri)
                            .build())
                    .build();
            mediaItems.add(item);
        }
        return mediaItems;
    }
}
