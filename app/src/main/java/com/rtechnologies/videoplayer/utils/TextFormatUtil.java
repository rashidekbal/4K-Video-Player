package com.rtechnologies.videoplayer.utils;
public class TextFormatUtil {
    public static String getDurationFormatted(long duration) {
        long sec= duration/1000;
        long min=sec/60;
        sec=sec%60;
        return min+":"+sec;

    }
}
