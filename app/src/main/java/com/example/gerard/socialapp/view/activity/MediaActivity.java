package com.example.gerard.socialapp.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.gerard.socialapp.GlideApp;
import com.example.gerard.socialapp.R;

public class MediaActivity extends AppCompatActivity {

    VideoView mVideoView;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);


        mVideoView = findViewById(R.id.videoView);
        mImageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        String mediaType = intent.getStringExtra(PerfilActivity.MEDIA_TYPE);
        String mediaUrl = intent.getStringExtra(PerfilActivity.MEDIA_URL);

        if ("video".equals(mediaType) || "audio".equals(mediaType)) {
            MediaController mc = new MediaController(this);
                mc.setAnchorView(mVideoView);

            mVideoView.setVideoPath(mediaUrl);
            mVideoView.setMediaController(mc);
            mVideoView.start();
        } else if ("image".equals(mediaType)) {
            GlideApp.with(this).load(mediaUrl).into(mImageView);
        }
    }
}
