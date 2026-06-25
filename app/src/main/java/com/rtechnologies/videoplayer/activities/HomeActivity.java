package com.rtechnologies.videoplayer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.MediaItem;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.databinding.ActivityHomeBinding;
import com.rtechnologies.videoplayer.utils.HomePageNavHandler;
import com.rtechnologies.videoplayer.utils.PermissionUtil;
import com.rtechnologies.videoplayer.viewmodels.MusicSessionViewModel;

import org.jspecify.annotations.Nullable;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    HomePageNavHandler navHandler;
    public FragmentManager fragmentManager;
    MusicSessionViewModel musicSessionViewModel;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
        init();
        handlePermission();
        setEventHandler();
        observeMusicSessionViewModel();
    }




    private void handlePermission() {
        if(!PermissionUtil.hasPermissions(this,PermissionUtil.MediaPermissions)){
            PermissionUtil.requestPermissions(this,PermissionUtil.MediaPermissions,PermissionUtil.MEDIA_PERMISSION_CODE);
            return;
        }
        musicSessionViewModel.connect(this);
    }

    private void setEventHandler() {
        binding.recentBtnInActive.setOnClickListener(v->navHandler.onRecentBtnClick());
        binding.musicBtnInActive.setOnClickListener(v->navHandler.onMusicBtnClick());
        binding.videoBtnInActive.setOnClickListener(v->navHandler.onVideoBtnClick());
        binding.currentPlayingCard.setOnClickListener(v->startActivity(new Intent(this,MusicPlayerActivity.class)));
        binding.playPauseBtn.setOnClickListener(this::handlePlayPauseBtnClick);
    }

    private void handlePlayPauseBtnClick(View view) {
        if(musicSessionViewModel.getController()==null)return;
        boolean isPlaying=musicSessionViewModel.getController().isPlaying();
        if(isPlaying){
            musicSessionViewModel.getController().pause();
            return;
        }
        musicSessionViewModel.getController().play();
    }

    private void init(){
        this.fragmentManager=getSupportFragmentManager();
        navHandler=new HomePageNavHandler(this,binding);
        this.musicSessionViewModel=new ViewModelProvider(this).get(MusicSessionViewModel.class);
    }
    private void observeMusicSessionViewModel() {
        musicSessionViewModel.getCurrentMediaItem().observe(this,this::handleMediaItemChange);
        musicSessionViewModel.isPlaying().observe(this,this::handlePlayingStateChange);
    }
    public void changeMainFragment(Fragment fragment, @Nullable String tag){
        fragmentManager.beginTransaction().replace(binding.contentFragment.getId(),fragment).addToBackStack(tag).commit();

    }
    private void handleMediaItemChange(MediaItem mediaItem){
        if(mediaItem==null){binding.miniPlayer.setVisibility(View.GONE);return;}
        binding.miniPlayer.setVisibility(View.VISIBLE);
        binding.currentMusicName.setText(mediaItem.mediaMetadata.title);
    }
    private  void handlePlayingStateChange(Boolean isPlaying){
        if(isPlaying==null){binding.miniPlayer.setVisibility(View.GONE);return;}
        binding.miniPlayer.setVisibility(View.VISIBLE);
        if(isPlaying){
            binding.playPauseBtn.setBackgroundDrawable(AppCompatResources.getDrawable(this,R.drawable.pause_icon));
            return;
        }
        binding.playPauseBtn.setBackgroundDrawable(AppCompatResources.getDrawable(this,R.drawable.play_icon));


    }

}