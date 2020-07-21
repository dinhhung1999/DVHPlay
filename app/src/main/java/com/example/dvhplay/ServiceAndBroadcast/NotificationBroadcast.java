package com.example.dvhplay.ServiceAndBroadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.dvhplay.R;
import com.example.dvhplay.helper.VFMSharePreference;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationBroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        VFMSharePreference sharePreference = new VFMSharePreference(context);
        String title = sharePreference.getAppName();
        String body = sharePreference.getStringValue("title");
        showNotification(context,title,body,intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showNotification(Context context, String title, String body, Intent intent){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId =1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_round_play_arrow_24)
                .setColor(context.getResources().getColor(R.color.colorBackgroundMain))
                .setContentTitle(title)
                .setContentText(body);
        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            mBuilder.setContentIntent(resultPendingIntent);
        }
        notificationManager.notify(notificationId, mBuilder.build());
    }
}
