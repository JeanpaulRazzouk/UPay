package com.example.upay;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.InputStream;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {
  TextView textView;
  TextView textView2;
  TextView textView3;
  ImageButton imageButton;
  //
    Uri link;
    //
    private FirebaseUser user;
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
        textView3 = findViewById(R.id.textView8);
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
            textView3.setText("\n"+user.getEmail());
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
                InputStream is;
                try {
                    is = getContentResolver().openInputStream(link); // Streaming Image from gallery;
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