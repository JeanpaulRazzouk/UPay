package com.example.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BarFragments.HomeFragment;
import com.example.upay.HomePage;
import com.example.upay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.view.View.INVISIBLE;

public class ChangeInfo extends Fragment {
    ImageButton imageButton,imageButton2,imageButton3,imageButton4;
    Uri link;
    public FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView textview,textview2;
    public ProgressBar circularProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_info, container, false);
        //
        circularProgressBar = view.findViewById(R.id.progressBar4);
        //
        textview = view.findViewById(R.id.textView36);
        textview2 = view.findViewById(R.id.textView37);
        imageButton = view.findViewById(R.id.imageButton16);
        imageButton.setClipToOutline(true);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Gallery();
            }
        });
        imageButton2 = view.findViewById(R.id.imageButton17);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        imageButton3 = view.findViewById(R.id.Username_btn);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ChangeName();
            }
        });

        imageButton4 = view.findViewById(R.id.imageButton21);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), HomePage.class);
                startActivity(i);
            }
        });


        try {
            uploadImage();
            GetImage();
        } catch (
                Exception e) {
            e.printStackTrace();

        }
        // methods();
        getFirebaseData();
        return view;
    }

    public void getFirebaseData(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            //Username
            textview.setText(user.getDisplayName());
            //Email;
            textview2.setText(user.getEmail());
        }
    }

     public void ChangeName(){
         final EditText taskEditText = new EditText(getContext());
         AlertDialog dialog = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle)
                 .setTitle("Change Your Name")
                 .setMessage("Enter your new username")
                 .setView(taskEditText)
                 .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         try {
                             String task = String.valueOf(taskEditText.getText());
                             user = FirebaseAuth.getInstance().getCurrentUser();
                             //
                             textview.setText(task);
                             FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                             UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                     .setDisplayName(task)
                                     .build();

                             user.updateProfile(profileUpdates)
                                     .addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if (task.isSuccessful()) {

                                             }
                                         }
                                     });
                         }catch(Exception e){}
                     }
                 })
                 .setNegativeButton("Cancel", null)
                 .create();
         dialog.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dialog_bg));
         dialog.show();
     }



    public void newPassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        auth.sendPasswordResetEmail(user.getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Please Check your Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void showDialog() {
        final EditText taskEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext(),R.style.MyAlertDialogStyle)
                .setTitle("Password Verification")
                .setMessage("Please enter your password")
                .setView(taskEditText)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String task = String.valueOf(taskEditText.getText());
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            newPassword();
                        }catch(Exception e){}
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dialog_bg));
        dialog.show();
    }


    public void Gallery() {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                link = data.getData();
                try {
                    uploadImage();
                    GetImage();
                }
                catch (Exception e) {
                    Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void uploadImage(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        if(link != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext(),R.style.MyAlertDialogStyle);
            progressDialog.setTitle("One sec...");
            progressDialog.getWindow().setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dialog_bg));
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
        user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference storageRef = storageReference.child("images/" +user.getUid());
        final File localFile = File.createTempFile("images", "jpg");
        localFile.mkdir();
        storageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getPath());
                        circularProgressBar.setVisibility(INVISIBLE);
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
