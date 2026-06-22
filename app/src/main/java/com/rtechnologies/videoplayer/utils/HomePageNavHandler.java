package com.rtechnologies.videoplayer.utils;

import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.FragmentManager;
import com.rtechnologies.videoplayer.activities.HomeActivity;
import com.rtechnologies.videoplayer.constants.FragmentsId;
import com.rtechnologies.videoplayer.databinding.ActivityHomeBinding;
import com.rtechnologies.videoplayer.fragments.MusicFragment;
import com.rtechnologies.videoplayer.fragments.RecentsFragment;
import com.rtechnologies.videoplayer.fragments.VideoFragment;

import java.util.Objects;

public class HomePageNavHandler {
    private final ActivityHomeBinding binding;
    private final HomeActivity activity;
    private String currentFragmentTag="";
    public HomePageNavHandler(HomeActivity activity,ActivityHomeBinding binding){
        this.binding=binding;
        this.activity=activity;
        this.onPageInit();
        this.handleBackPressNavigation();
        this.handleBackStack();


    }
    private void onPageInit(){
       onRecentBtnClick();
    }
    public void onRecentBtnClick(){
        resetState();
        binding.recentBtnActive.setVisibility(View.VISIBLE);
        binding.recentBtnInActive.setVisibility(View.GONE);
        if(currentFragmentTag.equals(FragmentsId.RECENTS.toString()))return;
        this.currentFragmentTag=FragmentsId.RECENTS.toString();
        if(activity.fragmentManager.getBackStackEntryCount()>1) {
            activity.fragmentManager.popBackStack(FragmentsId.RECENTS.toString(), 0);
            return;
        }
        activity.changeMainFragment(new RecentsFragment(), FragmentsId.RECENTS.toString());
    }
    public void onMusicBtnClick(){
        resetState();
        binding.musicBtnActive.setVisibility(View.VISIBLE);
        binding.musicBtnInActive.setVisibility(View.GONE);
        if(currentFragmentTag.equals(FragmentsId.MUSIC.toString()))return;
        this.currentFragmentTag=FragmentsId.MUSIC.toString();
        if(backStackContains(FragmentsId.MUSIC.toString())){
            activity.fragmentManager.popBackStack(FragmentsId.MUSIC.toString(), 0);
            return;
        }
        activity.changeMainFragment(new MusicFragment(), FragmentsId.MUSIC.toString());
    }



    public void onVideoBtnClick(){
        resetState();
        binding.videoBtnActive.setVisibility(View.VISIBLE);
        binding.videoBtnInActive.setVisibility(View.GONE);
        if(currentFragmentTag.equals(FragmentsId.VIDEO.toString()))return;
        this.currentFragmentTag=FragmentsId.VIDEO.toString();
        if(backStackContains(FragmentsId.VIDEO.toString())){
            activity.fragmentManager.popBackStack(FragmentsId.VIDEO.toString(), 0);
            return;
        }
        activity.changeMainFragment(new VideoFragment(), FragmentsId.VIDEO.toString());
    }
    private void resetState(){
        binding.recentBtnActive.setVisibility(View.GONE);
        binding.recentBtnInActive.setVisibility(View.VISIBLE);
        binding.musicBtnActive.setVisibility(View.GONE);
        binding.musicBtnInActive.setVisibility(View.VISIBLE);
        binding.videoBtnActive.setVisibility(View.GONE);
        binding.videoBtnInActive.setVisibility(View.VISIBLE);

    }
    private void handleBackStack(){
        activity.fragmentManager.addOnBackStackChangedListener(backStackChangedListener);

    }
    private void handleBackPressNavigation(){
        activity.getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(activity.fragmentManager.getBackStackEntryCount()==1)activity.finish();
                else activity.fragmentManager.popBackStack();

            }
        });

    }
    private final FragmentManager.OnBackStackChangedListener backStackChangedListener=new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            int entryCount=activity.fragmentManager.getBackStackEntryCount();
            String entryId=activity.fragmentManager.getBackStackEntryAt(entryCount-1).getName();
           if(entryId!=null){
               if(entryId.equals(FragmentsId.RECENTS.toString())){
                   currentFragmentTag=FragmentsId.RECENTS.toString();
                   onRecentBtnClick();
                   return;
               }
               if(entryId.equals(FragmentsId.MUSIC.toString())){
                   currentFragmentTag=FragmentsId.MUSIC.toString();
                   onMusicBtnClick();
                   return;
               }
               if(entryId.equals(FragmentsId.VIDEO.toString())){
                   currentFragmentTag=FragmentsId.VIDEO.toString();
                   onVideoBtnClick();
               }
           }
        }
    };

    private boolean backStackContains(String fragmentId) {
        for (int i = 0; i < activity.fragmentManager.getBackStackEntryCount(); i++) {
            if (Objects.equals(activity.fragmentManager.getBackStackEntryAt(i).getName(), fragmentId)) {
                return true;
            }
        }
        return false;

    }


}
