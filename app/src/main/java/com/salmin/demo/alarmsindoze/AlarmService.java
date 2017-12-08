package com.salmin.demo.alarmsindoze;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by aminnella on 12/6/17.
 */

public class AlarmService extends IntentService {

    public AlarmService() {
        super("DisplayNotification");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("AlarmService", "onHandleIntent: ");

        sendNotification("Time to wake up");
    }

    private void sendNotification(String msg) {
        initNotificationChannels();
        Log.d("AlarmService", "sendNotification: was called");
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_01")
                .setContentTitle("Alarm It")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(msg)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

        private void initNotificationChannels() {
        Log.d("AlarmService", "initNotificationChannels: was called");
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        NotificationManager mNotifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String chanId = "channel_01";
        CharSequence name = "default_channel";
        String description = "main notification channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel(chanId, name, importance);
        mChannel.setDescription(description);
        if (mNotifManager != null) {
            mNotifManager.createNotificationChannel(mChannel);
        }
    }
}
