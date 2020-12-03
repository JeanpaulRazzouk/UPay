package com.example.External;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.upay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    ImageButton imageButton;
    EditText editText;
    //

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_forgot_password);
        //
        editText = findViewById(R.id.editText6);
        videoView = findViewById(R.id.VideoView4);
        //
        intro_vid();
    }

    public void send_password_email_reset(View view) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = editText.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), " E-mail sent successfully !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void intro_vid (){
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid2);
        videoView.setVideoURI(uri);
        videoView.start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //
        Intent i = new Intent(this,LoginPage.class);
        startActivity(i);
    }
}
