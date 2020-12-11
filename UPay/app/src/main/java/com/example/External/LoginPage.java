package com.example.External;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.upay.HomePage;
import com.example.upay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    ImageButton imageButton;
    ImageButton imageButton2;
    //
    EditText editText;
    EditText editText2;
    TextView textView;
    //
    public ProgressBar pgsBar;
    //
    private String val_username;
    private String val_password;
    //
    private FirebaseAuth mAuth;
    //
    Animation animation;
    //
    VideoView VideoView;
    FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // User is signed in
                Intent i = new Intent(LoginPage.this, HomePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            } else {

        }
        //
        setContentView(R.layout.activity_login_page);
        pgsBar = findViewById(R.id.progressBar);
        // welcome banner;
        textView = findViewById(R.id.textView3);
        Shader shader = new LinearGradient(180,220,0,textView.getLineHeight(),
                Color.parseColor("#2196F3"), Color.parseColor("#D267E4"), Shader.TileMode.REPEAT);
        textView.getPaint().setShader(shader);
        // FireBase Dta connect;
        mAuth = FirebaseAuth.getInstance();
        // Login button;
        imageButton = findViewById(R.id.imageButton7);
        imageButton2 = findViewById(R.id.imageButton2);
        // Credentials;
        // Username;
        editText = findViewById(R.id.editText);
        //Password;
        editText2 = findViewById(R.id.editText2);
        //
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_animation);
        animation.setDuration(800);
        textView.startAnimation(animation);
        //
        intro_vid();
        //
        VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //
        pgsBar.setVisibility(View.INVISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty() && editText2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), " Please fill empty fields. ", Toast.LENGTH_LONG).show();
                }
                else if (editText2.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), " Please fill Password field. ", Toast.LENGTH_LONG).show();
                }
                else if (editText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), " Please fill Email field. ", Toast.LENGTH_LONG).show();
                }
                else{
                    pgsBar.setVisibility(View.VISIBLE);
                    login();
                }
            }
        });
    }
    public void go_reset_password (View view){
        Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
        startActivity(i);
    }

    private void updateUI(FirebaseUser account) {
        if (account != null) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show();
        } else {
            pgsBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Something went wrong. Please try again", Toast.LENGTH_LONG).show();
        }
    }

    public void create_new_button(View view) {
        startActivity(new Intent(LoginPage.this, CreateNewAccount.class));
       // overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
        VideoView.stopPlayback();
    }
    // login authentication;
    public void login() {
        val_username = editText.getText().toString();
        val_password = editText2.getText().toString();
        mAuth.signInWithEmailAndPassword(val_username, val_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            //
                            pgsBar.setVisibility(View.INVISIBLE);
                            //
                            Intent i = new Intent(getApplicationContext(), HomePage.class);
                            startActivity(i);
                        } else {
                            updateUI(null);
                        }
                    }
                });

    }

    public void intro_vid (){
        VideoView = findViewById(R.id.VideoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid1);
        VideoView.setVideoURI(uri);
       // VideoView.start();
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}