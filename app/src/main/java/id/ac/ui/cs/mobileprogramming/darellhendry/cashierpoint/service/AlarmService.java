package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.MainActivity;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.R;
import id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.receiver.AlarmReceiver;

public class AlarmService extends Service {
    private static final String TAG = "MyService";
    private static final int NOTIFICATION_ID = 0;
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;
    private static boolean everyMorning;
    private static boolean everyHour;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String period = intent.getExtras().getString("period");
        new NotifyAsyncTask().execute(period);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("test", "onDestroy");
        if (alarmManager != null) {
            alarmManager.cancel(notifyPendingIntent);
        }
        everyMorning = false;
        everyHour = false;
    }

    public static boolean isEveryMorningRunning() {
        return everyMorning;
    }

    public static boolean isEveryHourRunning(){
        return everyHour;
    }

    public void every_morning() {
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID , notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, notifyPendingIntent);
        everyMorning = true;
    }

    public void every_1_hour() {
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID , notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//        long repeatInterval = AlarmManager.INTERVAL_HOUR;
        long repeatInterval = 500L;
        long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerTime, repeatInterval, notifyPendingIntent);
        everyHour = true;
    }

    private class NotifyAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... types) {
            if (types[0].equals("every_morning")) {
                Log.d("test", "morning");
                every_morning();
            } else if (types[0].equals("one_hour")) {
                Log.d("test", "one hour");
                every_1_hour();
            }
            return null;
        }
    }
}
