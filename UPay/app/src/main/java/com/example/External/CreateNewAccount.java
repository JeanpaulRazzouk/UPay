package com.example.External;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.upay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class CreateNewAccount extends AppCompatActivity {
EditText editText;
EditText editText2;
EditText editText4;
//
public ProgressBar pgsBar;
    //
    Animation animation;
    VideoView VideoView;
    //
    TextView textView;
private FirebaseAuth mAuth;
//
    public  String new_val_username;
    public  String new_val_password;
    public  String new_name;
    //
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_create_new_account);
        checkBox = findViewById(R.id.checkBox);
        pgsBar = findViewById(R.id.progressBar2);
        // new Email;
        editText = findViewById(R.id.editText3);
        // username;
        editText4 = findViewById(R.id.editText8);
        // password
        editText2 = findViewById(R.id.editText4);
        VideoView = findViewById(R.id.VideoView2);
        //
        intro_vid();
        //
        VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        // FireBase Dta connect;
        mAuth = FirebaseAuth.getInstance();
        //
        pgsBar.setVisibility(View.INVISIBLE);
    }
    public void create_account_btn(View view) {
        // Email;
        new_val_username = editText.getText().toString();
        // Password;
        new_val_password = editText2.getText().toString();
        //Username;
        new_name = editText4.getText().toString();
        //
        if (checkBox.isChecked()) {

            if (new_val_username.isEmpty() == false && new_val_password.isEmpty() == false && new_name.isEmpty() == false){
                pgsBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(new_val_username, new_val_password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(CreateNewAccount.this, " Account Successfully created ",
                                            Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    CreateNewAccount c = new CreateNewAccount();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                            setDisplayName(" " + new_name).build();

                                    user.updateProfile(profileUpdates);
                                    //
                                    pgsBar.setVisibility(View.INVISIBLE);
                                    //
                                    Intent i = new Intent(getApplicationContext(), LoginPage.class);
                                    startActivity(i);
                                } else  {
                                    // If sign in fails, display a message to the user.
                                        Toast.makeText(CreateNewAccount.this, " Account Already exists ",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    pgsBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }
            else {

                Toast.makeText(CreateNewAccount.this, " please fill out empty fields ",
                        Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(CreateNewAccount.this, " Please accept the terms of services ",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void intro_vid (){
        VideoView = findViewById(R.id.VideoView2);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid2);
        VideoView.setVideoURI(uri);
        VideoView.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //
        Intent i = new Intent(this,LoginPage.class);
        startActivity(i);
    }
}
