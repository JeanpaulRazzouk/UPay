package com.example.BarFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.upay.Adapter;
import com.example.upay.BottomSheetPayPal;
import com.example.Profile.Profile;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.example.upay.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {


    public float income = 20000;
    //
    ImageButton imageButton;
    ImageButton imageButton2;
    TextView textView;
    TextView textView2;
    TextView textView3;
    //
    RelativeLayout relativeLayout;
    //
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    //
    String name;
    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    //
    FragmentTransaction ft;
    Uri link;
    //
    Animation animation;
    CardView cardView;
    CardView cardView2;
    CircularProgressBar circularProgressBar;
    //
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<PurchaseItems> itemsArrayListt;
    //
    // Adding Data;
    PurchaseItems [] p;
    public ArrayList<String> Names = new ArrayList<>();
    public ArrayList<String> Location = new ArrayList<>();
    public ArrayList<String> Amount = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        //
        access_Dta();
        try {
            uploadImage();
            GetImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Add(user.getUid(),user.getDisplayName(),user.getEmail());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //
        imageButton = view.findViewById(R.id.imageView12);
        imageButton.setClipToOutline(true);

        imageButton2 = view.findViewById(R.id.pay_btn);

        cardView = view.findViewById(R.id.income);
        cardView2 = view.findViewById(R.id.Pay);
        //
        textView2 = view.findViewById(R.id.exp_val);
        textView3 = view.findViewById(R.id.percentage);
        //
        textView = view.findViewById(R.id.textView3);
        textView.setText("Home");
        Shader shader = new LinearGradient(180,220,0,textView.getLineHeight(),
                Color.parseColor("#2196F3"), Color.parseColor("#D267E4"), Shader.TileMode.REPEAT);
        textView.getPaint().setShader(shader);
        //
        relativeLayout = view.findViewById(R.id.frag_home);
        //

        // first row;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
        animation.setDuration(1000);
        cardView.setAnimation(animation);;
        cardView2.setAnimation(animation);
        //
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        //
         circularProgressBar =view.findViewById(R.id.pb_one);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(getContext(), Profile.class);
             startActivity(i);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // THIS IS JUST A TEST;
                BottomSheetPayPal bottomSheet = new BottomSheetPayPal();
                bottomSheet.show(getFragmentManager(),"TAG");
            }
        });
        //
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsArrayListt = new ArrayList<>();
        adapter = new Adapter(getContext(), itemsArrayListt);
        recyclerView.setAdapter(adapter);
        // this is the list;
        Activity();
        return view;
    }
    private void Add(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("Users").child(userId).setValue(user);
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

    public void Activity (){
        // TEST
        Names.add(0,"McDonald's");
        Names.add(1,"Apple Store");
        Names.add(2,"Starbucks");
        Names.add(3,"iShop");
        //
        Location.add(0,"Beirut,LB");
        Location.add(1,"San Francisco,CA");
        Location.add(2,"San Francisco,CA");
        Location.add(3,"NY,NY");
        //
        Amount.add(0,"10.34");
        Amount.add(1,"9899.99");
        Amount.add(2,"7.89");
        Amount.add(3,"100.00");
        //
        p = new PurchaseItems[Names.size()];
        //
        float val = 0;
        float val1 = 0;

        for (int i =0 ; i<p.length;i++) {
        val1 = Float.parseFloat(Amount.get(i));
        val = val + val1;
        }

        textView2.setText("$"+val);
        //
        float perc_res = (val/income)*100;
        textView3.setText((int) Math.floor(perc_res)+"%");
        circularProgressBar.setProgressWithAnimation((float) perc_res, Long.valueOf(3000)); // =3s
        //
        for (int i =0 ; i<p.length;i++) {
             p[i] = new PurchaseItems(Names.get(i), Location.get(i), "\t\t$" + Amount.get(i));
            itemsArrayListt.add(p[i]);
        }
    }





}