package com.example.moragame;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

public class MusicService extends Service {
    public static int serviceCount = 0;
    private MediaPlayer player;
    private final String TAG = "MusicService";

    @Override
    public void onCreate() {
        if(serviceCount==0) {
            super.onCreate();
            player = MediaPlayer.create(this, R.raw.background_music);
            player.setLooping(true); // Set looping
            player.setVolume(100, 100);
            Log.d(TAG, "onCreate: " + serviceCount);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(serviceCount==0)
            player.start();
        serviceCount++;
        Log.d(TAG, "onStartCommand: "+serviceCount);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceCount--;
        if(serviceCount==0) {
            player.pause();
            super.onDestroy();
            Log.d(TAG, "onDestroy: "+serviceCount);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved: "+serviceCount);
        if(serviceCount==0)
            super.onTaskRemoved(rootIntent);
    }
}
