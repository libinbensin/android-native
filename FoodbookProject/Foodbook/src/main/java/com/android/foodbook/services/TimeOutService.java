package com.android.foodbook.services;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.TimerTask;

/**
 * Created by libin on 12/26/13.
 */
public class TimeOutService extends Service
{
    private Handler mHandler = null;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {
        mHandler = new Handler();
        mHandler.postDelayed(runnable,300000);
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        if(mHandler != null)
        {
            mHandler.removeCallbacks(runnable);
            mHandler = null;
        }
    }

    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            sendNotification();
            stopSelf();
        }
    };

    private class TimerTaskExt extends TimerTask
    {
        @Override
        public void run()
        {
            sendNotification();
            stopSelf();
        }
    }

    private void sendNotification()
    {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                        .setContentTitle("FoodMark Notification")
                        .setContentText("Application session timed out!");

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1000, mBuilder.build());
    }


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }


}
