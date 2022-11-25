package com.saadullahkhan.i190474_i190409;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class CallActivity extends AppCompatActivity {
    String roomID;
    SystemDataBase systemDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}