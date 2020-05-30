package com.example.cashtrack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class ScheduledNotiOwe extends BroadcastReceiver {
    int reqCode = 123;
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getStringExtra("name");
        String cost = intent.getStringExtra("cost");
        String desc = intent.getStringExtra("desc");
        int intCost = Integer.parseInt(cost);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);

            Intent i = new Intent(context, youOwe.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, reqCode, i, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
            bigText.setBigContentTitle("Reminder!");
            bigText.bigText("You owe " + name + " $" + cost + " for " + desc + ". Remember to pay back soon!");
            bigText.setSummaryText("Notification");
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
            builder.setContentTitle("Reminder!");
            builder.setContentText("Pay Back Soon" );
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            builder.setContentIntent(pIntent);
            builder.setStyle(bigText);
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(uri);
            builder.setAutoCancel(true);

            Notification n = builder.build();
            notificationManager.notify(1234, n);
        }
    }
}
