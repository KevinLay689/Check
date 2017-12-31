package com.example.kevinlay.check.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Base64;

import com.example.kevinlay.check.MainActivity;
import com.example.kevinlay.check.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by kevinlay on 12/27/17.
 */

public class Util {

    private static final int NOTIFICATION_O_ID = 1;
    private static final int NOTIFICATION_PRE_O_ID = 0;
    private static final String NOTIFICATION_KEY = "notifications";

    public static Bitmap getProfileImage(int sampleSize, String image) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        InputStream is = new ByteArrayInputStream(decodedString);
        Bitmap decodedByte = BitmapFactory.decodeStream(is, null, options);
        Bitmap.createScaledBitmap(decodedByte, 100, 100, false);

        return decodedByte;
    }

    public static void createNotification(String title, String notification, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isAcceptingNotifications = prefs.getBoolean(NOTIFICATION_KEY, true);

        if(isAcceptingNotifications) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                Intent intent = new Intent(context, MainActivity.class);
                int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
                int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
                PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("myChannelId", "My Channel", importance);
                channel.setDescription("Reminders");
                // Register the channel with the notifications manager
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(channel);

                NotificationCompat.Builder mBuilder =
                        // Builder class for devices targeting API 26+ requires a channel ID
                        new NotificationCompat.Builder(context, "myChannelId")
                                .setContentTitle(title)
                                .setSmallIcon(R.drawable.ic_local_dining_black_96dp)
                                .setContentText(notification)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true);

                mNotificationManager.notify(NOTIFICATION_O_ID, mBuilder.build());

            } else {

                Intent intent = new Intent(context, MainActivity.class);
                int requestID = (int) System.currentTimeMillis(); //unique requestID to differentiate between various notification with same NotifId
                int flags = PendingIntent.FLAG_CANCEL_CURRENT; // cancel old intent and create new one
                PendingIntent pIntent = PendingIntent.getActivity(context, requestID, intent, flags);

                NotificationCompat.Builder mBuilder =
                        // this Builder class is deprecated
                        new NotificationCompat.Builder(context)
                                .setContentTitle(title)
                                .setSmallIcon(R.drawable.ic_local_dining_black_96dp)
                                .setContentText(notification)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true);

                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(NOTIFICATION_PRE_O_ID, mBuilder.build());
            }
        }
    }
}
