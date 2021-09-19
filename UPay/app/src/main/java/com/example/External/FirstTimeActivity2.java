package com.example.External;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.upay.HomePage;
import com.example.upay.R;

public class FirstTimeActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time2);
        //
     getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        VideoView videoView = findViewById(R.id.videoView4);
        //
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(false);
            }
        });
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+"/"+R.raw.intro);
        videoView.setZOrderOnTop(true);
        videoView.setBackgroundColor(Color.TRANSPARENT);
        videoView.setVideoURI(uri);
        videoView.start();
        //
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            public void onCompletion(MediaPlayer mp)
            {
                Intent i = new Intent(getApplicationContext(), FirstTimeActivity.class);
                startActivity(i);
            }
        });
    }
}