package com.hart.aris.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CheckReceiver extends BroadcastReceiver {
    public CheckReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            Bundle bundle = intent.getExtras();
            createNotification(PastPaperCheckActivity.class,context);
        } catch (Exception e) {
           // Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

        }}

    public void createNotification(Class activity,Context context) {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(context, activity);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,  PendingIntent.FLAG_UPDATE_CURRENT);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Hi . Can we chat?")
                .setContentText("It'll only take a few moments!").setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pIntent).build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, noti);
    }
}
