package com.example.External;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upay.HomePage;
import com.example.upay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;

public class FirstTimeActivity extends AppCompatActivity {
TextView textView;
TextView textView2;
TextView textView3;
ImageButton imageButton;
ImageButton imageButton2;
Animation animation;
Uri link;
    public FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //
        MediaPlayer mp = new MediaPlayer();
        mp = MediaPlayer.create(getApplicationContext(), R.raw.welcometoupay);
        mp.start();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        HashMap<String, Object> value2 = new HashMap<>();
        value2.put("USD",false);
        value2.put("EUR",false);
        value2.put("CAN",false);
        mDatabase.child("Users").child(user.getUid()).child("Currency Settings").updateChildren(value2);
        //
        HashMap<String, Object> v = new HashMap<>();
        v.put("Switch1", "false");
        v.put("Switch2", "false");
        v.put("Switch3", "false");
        v.put("Switch4", "false");
        mDatabase.child("Users").child(user.getUid()).child("Switches").setValue(v);
        //
        HashMap<String, Object> values2 = new HashMap<>();
        values2.put("Transaction count",0);
        mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values2);
        //
       textView = findViewById(R.id.welcome);
       textView2 = findViewById(R.id.welcome2);
       textView3 = findViewById(R.id.textView16);
       textView3.setText("Continue");
       //
       imageButton = findViewById(R.id.imageButton6);
       imageButton2 = findViewById(R.id.imageButton4);
       //
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.open_animation);
        animation.setDuration(1000);
        textView.setAnimation(animation);
        textView2.setAnimation(animation);
        imageButton.setAnimation(animation);
        imageButton2.setAnimation(animation);
       //
        Shader shader = new LinearGradient(180,220,0,textView.getLineHeight(),
                Color.parseColor("#2196F3"), Color.parseColor("#D267E4"), Shader.TileMode.REPEAT);
        textView.getPaint().setShader(shader);

        try {
            uploadImage();
            GetImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomePage.class);
                startActivity(i);
            }
        });
        //
        //
//        HashMap<String, Object> values = new HashMap<>();
//        values.put("Current Country Location", "");
//        values.put("Current Place Location", "");
//
//        mDatabase.child("Users").child(user.getUid()).child("Location").setValue(values);
    }
    public void Gallery(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                link = data.getData();
                try {
                    uploadImage();
                    GetImage();
                }
                catch (Exception e) {
                    Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(link != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this,R.style.MyAlertDialogStyle);
            progressDialog.setTitle("Uploading...");
            progressDialog.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.dialog_bg));
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+user.getUid());
            ref.putFile(link)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            try {
                                GetImage();
                            }catch (Exception e){}
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
        }
    }

    public void GetImage() throws Exception {
        StorageReference storageRef = storageReference.child("images/" +user.getUid());
        final File localFile = File.createTempFile("images", "jpg");
        localFile.mkdir();
        storageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                        imageButton2.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
    }
}