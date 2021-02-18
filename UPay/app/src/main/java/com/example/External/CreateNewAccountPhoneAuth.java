package com.example.External;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.example.upay.R;
import com.google.android.datatransport.Transport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;

import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class CreateNewAccountPhoneAuth extends AppCompatActivity {
    VideoView VideoView;
    //
    TextView textView;
    ImageButton imageButton;
    private FirebaseAuth mAuth;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account_phone_auth);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        VideoView = findViewById(R.id.VideoView2);
        VideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //
        textView = findViewById(R.id.textView19); // done;

        textView.setText("Send");

        imageButton = findViewById(R.id.imageButtonxx);
        mAuth = FirebaseAuth.getInstance();
        //

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_account();
            }
        });
        intro_vid();
    }

    public void create_account() {
        Intent i = getIntent();
        String Email = i.getStringExtra("Email");
        String Password = i.getStringExtra("Password");
        String Username = i.getStringExtra("Username");

        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(CreateNewAccountPhoneAuth.this, " Account Successfully created ",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().
                                    setDisplayName(" " + Username).build();
                            user.updateProfile(profileUpdates);
                            //
                            Intent i = new Intent(getApplicationContext(), LoginPage.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateNewAccountPhoneAuth.this, " Account Already exists ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void intro_vid() {
        VideoView = findViewById(R.id.VideoView2);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.vid2);
        VideoView.setVideoURI(uri);
        VideoView.start();
    }

    @Override
    public void onBackPressed() {

    }

}


