package com.example.dvhplay.ServiceAndBroadcast;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.support.v4.media.session.IMediaSession;

import com.example.dvhplay.R;
import com.example.dvhplay.helper.VFMSharePreference;
import com.example.dvhplay.home.sliderImage.SliderItem;
import com.example.dvhplay.video.VideoUlti;

import androidx.core.app.NotificationCompat;

public class NotificationBroadcast extends BroadcastReceiver {
    @SuppressLint("ResourceType")
    @Override
    public void onReceive(Context context, Intent intent) {
        VFMSharePreference sharePreference = new VFMSharePreference(context);
        String title = sharePreference.getAppName();
        String body = sharePreference.getStringValue("title");
        showNotification(context,title,body,intent);
    }
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
                .setSmallIcon(R.drawable.ic_round_play_arrow_24)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            mBuilder.setContentIntent(resultPendingIntent);
        }


        notificationManager.notify(notificationId, mBuilder.build());
    }
}
