package com.saadullahkhan.i190474_i190409;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CallActivity extends AppCompatActivity {
    String roomID;
    SystemDataBase systemDataBase;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        back = findViewById(R.id.callBackButton);
        systemDataBase = new SystemDataBase(CallActivity.this);
        if(Integer.parseInt(systemDataBase.getId()) < Integer.parseInt(getIntent().getStringExtra("id"))){
            roomID = systemDataBase.getId()+getIntent().getStringExtra("id");
        }else{
            roomID = getIntent().getStringExtra("id")+systemDataBase.getId();
        }
        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(roomID)
                    .setAudioMuted(false)
                    .setVideoMuted(false)
                    .setAudioOnly(false)
                    .build();
            JitsiMeetActivity.launch(CallActivity.this, options);
//            notificationDialog();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
//    private void notificationDialog(){
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANNEL_ID  = "JitsiOngoingConferenceChannel";
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            @SuppressWarnings("WrongConstant")
//            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"xyz",NotificationManager.IMPORTANCE_MAX);
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
//            notificationChannel.enableVibration(true);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(CallActivity.this,NOTIFICATION_CHANNEL_ID);
//        notificationBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setWhen(System.currentTimeMillis()).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Call").setContentText("none").setContentInfo("xyz");
//        notificationManager.notify(1,notificationBuilder.build());
//    }

}