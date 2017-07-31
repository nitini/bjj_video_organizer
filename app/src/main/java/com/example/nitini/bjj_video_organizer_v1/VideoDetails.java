package com.example.nitini.bjj_video_organizer_v1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VideoDetails extends AppCompatActivity {

    String videoTitleString;
    String videoThumbnailString;
    String videoLinkString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Intent intent = getIntent();
        videoTitleString = intent.getStringExtra("videoTitle");
        videoThumbnailString = intent.getStringExtra("thumbnail");
        videoLinkString = intent.getStringExtra("link");

        TextView videoTitle = (TextView) findViewById(R.id.videoTitle);

        videoTitle.setText(videoTitleString);

        ImageView videoThumbnail = (ImageView) findViewById(R.id.videoThumbnail);

        Glide.with(this)
                .load(videoThumbnailString)
                .into(videoThumbnail);

    }

    public void playVideoFromDetails(View v) {
        this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(videoLinkString)));
    }
}
