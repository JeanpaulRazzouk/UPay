package com.example.Profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.External.LoginPage;
import com.example.upay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Profile extends AppCompatActivity {
  TextView textView;
  TextView textView2;
  TextView textview3;
  ImageButton imageButton;
    //
    Uri link;
    //
    public FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView7);
        textview3 = findViewById(R.id.textView101);
        imageButton = findViewById(R.id.imageButton);
        imageButton.setClipToOutline(true);
        //
        access_Dta();
        try {
            uploadImage();
            GetImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void access_Dta() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            textView.setText(" Hello,");
            textView2.setText(user.getDisplayName());
            textview3.setText("How UPay"+"\n"+"Works");
            Shader shader = new LinearGradient(180,220,0,textView.getLineHeight(),
                   Color.parseColor("#2196F3"), Color.parseColor("#D267E4"), Shader.TileMode.REPEAT);
            textView2.getPaint().setShader(shader);
        }
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

        if(link != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
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

    public void logout(View view) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signOut();
        //
        Intent i = new Intent(this, LoginPage.class);
        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void Pers_info(View view){
       PersonalInfoFragment selectedFragment =  new PersonalInfoFragment();;
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                selectedFragment).commit();
    }
    public void Pay_info(View view){

    }

    public void upay_work(View view){

    }

    public void get_help(View view){

    }

    public void feedback(View view){

    }

    public void terms_service(View view){

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
                        imageButton.setImageBitmap(bitmap);
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