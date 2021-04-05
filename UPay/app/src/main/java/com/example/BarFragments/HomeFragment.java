package com.example.BarFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapters.Adapter;
import com.example.Adapters.AdapterCal;
import com.example.Profile.Profile;
import com.example.payment.BottomSheetNFC;
import com.example.payment.User;
import com.example.upay.CalData;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HomeFragment extends Fragment {
    //
    ImageButton imageButton;
    ImageButton imageButton2;
    ImageView imageView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    ScrollView scrollView;
    ProgressBar progressBar;
    //
    RelativeLayout relativeLayout;
    //
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    //
    String name;
    BottomNavigationView bottomNavigationView;
    Uri link;
    //
    Animation animation;
    CardView cardView;
    CardView cardView2;
    CircularProgressBar circularProgressBar;
    //
    private RecyclerView recyclerView;
    private com.example.Adapters.Adapter adapter;
    private ArrayList<PurchaseItems> itemsArrayList;
    // Adding Data;
    ArrayList<PurchaseItems> p;
    //
    public ArrayList<String> Names = new ArrayList<>();
    public ArrayList<String> Location = new ArrayList<>();
    public ArrayList<String> Amount = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
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
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        scrollView = view.findViewById(R.id.scroll);
        scrollView.smoothScrollTo(0,0);
        //
        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        //
        imageButton = view.findViewById(R.id.imageView12);
        imageButton.setClipToOutline(true);

        imageButton2 = view.findViewById(R.id.pay_btn);
        imageView = view.findViewById(R.id.imageView2);
        //
        cardView = view.findViewById(R.id.income);
        cardView2 = view.findViewById(R.id.Pay);
        //
        textView2 = view.findViewById(R.id.exp_val);
        textView3 = view.findViewById(R.id.percentage);
        //
        textView = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView14);
        textView4.setVisibility(View.INVISIBLE);
        textView.setText("Home");
        //
        relativeLayout = view.findViewById(R.id.frag_home);
        // second  row;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
        animation.setDuration(1100);
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
                BottomSheetNFC bottomSheet = new BottomSheetNFC();
                bottomSheet.show(getFragmentManager(),"TAG");
            }
        });
        //
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemsArrayList = new ArrayList<>();
        adapter = new Adapter(getContext(), itemsArrayList);
        recyclerView.setAdapter(adapter);
        // this is the list;
        HandlerThread handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable()
        {
            @Override
            public void run() {
                Activity();
            }
        });
        return view;
    }


    private void Add(String userId, String name, String email) {
        User user = new User(name, email);

        mDatabase.child("Users").child(userId).child("Personal Info").setValue(user);
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
//        // TODO Data Injection;
        Names.add(0,"Crepaway");
        Location.add(0,"Byblos,LB");
        Amount.add(0,"60.21");
        Date.add(0,"24/01/2021");
        //
        Names.add(0,"Carrefour");
        Location.add(0,"Hazmieh,LB");
        Amount.add(0,"98.00");
        Date.add(0,"25/01/2021");
        //
        Names.add(0,"Starbucks");
        Location.add(0,"Byblos,LB");
        Amount.add(0,"100.00");
        Date.add(0,"26/02/2021");
        //

        Names.add(0,"Apple Store");
        Location.add(0,"NY,USA");
        Amount.add(0,"158.00");
        Date.add(0,"27/02/2021");
        //
        Names.add(0,"Mc'Donalds");
        Location.add(0,"Tyre,LB");
        Amount.add(0,"100.00");
        Date.add(0,"01/03/2021");

        Names.add(0,"Crepaway");
        Location.add(0,"Byblos,LB");
        Amount.add(0,"130.00");
        Date.add(0,"09/03/2021");

        Names.add(0,"Starbucks");
        Location.add(0,"Beirut,LB");
        Amount.add(0,"120.00");
        Date.add(0,"12/03/2021");

        Names.add(0,"Mc'Donalds");
        Location.add(0,"Dora,LB");
        Amount.add(0,"12.00");
        Date.add(0,"15/03/2021");

        Names.add(0,"Starbucks");
        Location.add(0,"Beirut,LB");
        Amount.add(0,"12.00");
        Date.add(0,"18/03/2021");
        //
            for (int i = 0 ; i< Names.size();i++) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> values = new HashMap<>();
                values.put("Name", Names.get(i));
                values.put("Location", Location.get(i));
                values.put("Amount", Amount.get(i));
                values.put("Date", Date.get(i));
                mDatabase.child("Users").child(user.getUid()).child("Transactions").child(""+i).updateChildren(values);
            }
            HashMap<String, Object> values2 = new HashMap<>();
            values2.put("Transaction count",Names.size());
            mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values2);
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String xl = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                int SI = Integer.parseInt(xl);
                //
                p = new ArrayList<>();
                // getting recycler data
                for (int i =0 ; i<SI;i++) {
                    Names.add(dataSnapshot.child("Transactions").child("" + i).child("Name").getValue().toString());
                    Location.add(dataSnapshot.child("Transactions").child("" + i).child("Location").getValue().toString());
                    Amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                    Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                }
                    try {
                        if (Names.get(0) == null) {
                        }
                    }catch(Exception e){
                        progressBar.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.VISIBLE);
                    }
                for (int i =0 ; i<SI;i++) {
                    p.add(new PurchaseItems(Names.get(i), Location.get(i), "\t\t$" + Amount.get(i),Date.get(i)));
                    recyclerView.setAdapter(new Adapter(getContext(), p));
                }
                //
                float val = 0;
                float val1 = 0;

                for (int i =0 ; i<SI;i++) {
                    val1 = Float.parseFloat(Amount.get(i));
                    val = val + val1;
                }
                textView2.setText("$"+val);
                Float finalVal = val;
                // val and % data;
                String x;
            try {
                x = dataSnapshot.child("User Data").child("income").getValue().toString();
                int income = Integer.parseInt(x);
                int perc_res = (int) ((finalVal / income) * 100);
                textView3.setText(perc_res + "%");
                circularProgressBar.setProgressWithAnimation((float) perc_res, Long.valueOf(3000)); // 3 sec;
                if (income < perc_res) {
                    circularProgressBar.setProgressBarColor(Color.parseColor("#FF1D47"));
                } else if (perc_res > 0.60 * income && perc_res < income) {
                    circularProgressBar.setProgressBarColor(Color.parseColor("#FF9847"));
                } else {
                }
            }catch(Exception e){

            }
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}