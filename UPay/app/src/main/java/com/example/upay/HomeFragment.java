package com.example.upay;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class HomeFragment extends Fragment {
    ImageButton imageButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    //
    String name;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    //
    FragmentTransaction ft;
    Uri link;
    double balance = 102.93;
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        access_Dta();
        try {
            uploadImage();
            GetImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        imageButton = view.findViewById(R.id.imageView12);
        imageButton.setClipToOutline(true);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(getContext(),Profile.class);
             startActivity(i);
            }
        });
        return view;
    }

    public void access_Dta() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //String email = user.getEmail();
            // Account Username;
            name = user.getDisplayName();
            //boolean emailVerified = user.isEmailVerified();
        }
    }
    public void uploadImage() {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(link != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
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
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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