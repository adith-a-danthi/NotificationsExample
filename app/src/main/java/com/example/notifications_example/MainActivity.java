package com.example.notifications_example;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button notifyBtn;
    private Button updateBtn;
    private Button cancelBtn;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotifcatoinChannel();

        notifyBtn = findViewById(R.id.notifyBtn);
        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });

        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });

        setNotificationButtonState(true,false,false);
    }

    public void sendNotification(){
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        setNotificationButtonState(false,true,true);

    }

    public void updateNotification(){
        Bitmap androidImage = BitmapFactory.decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(androidImage)
                        .setBigContentTitle("Dummy Update!!"));

        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());

        setNotificationButtonState(false,false,true);

    }

    public void cancelNotification(){
        mNotifyManager.cancel(NOTIFICATION_ID);

        setNotificationButtonState(true,false,false);

    }

    private void createNotifcatoinChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Creating notification channel
            NotificationChannel primaryChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification",NotificationManager.IMPORTANCE_HIGH);
            primaryChannel.enableLights(true);
            primaryChannel.setLightColor(Color.CYAN);
            primaryChannel.enableVibration(true);
            primaryChannel.setDescription("Notification for Mascot");
            mNotifyManager.createNotificationChannel(primaryChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        //To switch to the app when the notification is clicked on
        Intent notificationIntent = new Intent(this,MainActivity.class);
        PendingIntent notificaitonPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                .setContentTitle("You Have a Notification!")
                .setContentText("It's a dummy notification")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificaitonPendingIntent)
                // To remove the notification when clicked on
                .setAutoCancel(true);
        return notifyBuilder;
    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled){
        notifyBtn.setEnabled(isNotifyEnabled);
        updateBtn.setEnabled(isUpdateEnabled);
        cancelBtn.setEnabled(isCancelEnabled);
    }

}
