package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;

public class MusicService extends Service {
    MediaPlayer mp;

    public MusicService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mp =  MediaPlayer.create(this, R.raw.ts);
        mp.setLooping(true);
        mp.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mp.stop();
    }
}
