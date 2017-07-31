package com.example.nitini.bjj_video_organizer_v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class VideoDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Intent intent = getIntent();
        String videoTitle = intent.getStringExtra("videoTitle");
        Log.d("TAGGY", videoTitle);
    }
}
