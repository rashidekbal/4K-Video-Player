package com.rtechnologies.videoplayer.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.rtechnologies.videoplayer.R;
import com.rtechnologies.videoplayer.databinding.ActivityHomeBinding;
import com.rtechnologies.videoplayer.utils.HomePageNavHandler;
import com.rtechnologies.videoplayer.utils.PermissionUtil;

import org.jspecify.annotations.Nullable;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;
    HomePageNavHandler navHandler;
    public FragmentManager fragmentManager;

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


    }

    private void handlePermission() {
        if(!PermissionUtil.hasPermissions(this,PermissionUtil.MediaPermissions)){
            PermissionUtil.requestPermissions(this,PermissionUtil.MediaPermissions,PermissionUtil.MEDIA_PERMISSION_CODE);
        }
    }

    private void setEventHandler() {
        binding.recentBtnInActive.setOnClickListener(v->navHandler.onRecentBtnClick());
        binding.musicBtnInActive.setOnClickListener(v->navHandler.onMusicBtnClick());
        binding.videoBtnInActive.setOnClickListener(v->navHandler.onVideoBtnClick());
        binding.currentPlayingCard.setOnClickListener(v->startActivity(new Intent(this,MusicPlayerActivity.class)));
    }

    private void init(){
        this.fragmentManager=getSupportFragmentManager();
        navHandler=new HomePageNavHandler(this,binding);
    }
    public void changeMainFragment(Fragment fragment, @Nullable String tag){
        fragmentManager.beginTransaction().replace(binding.contentFragment.getId(),fragment).addToBackStack(tag).commit();

    }

}