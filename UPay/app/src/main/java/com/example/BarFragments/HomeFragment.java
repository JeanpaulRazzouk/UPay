package com.example.BarFragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
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
import com.example.Profile.Profile;
import com.example.payment.BottomSheetNFC;
import com.example.payment.User;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.io.IOException;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {
    //
    ImageButton imageButton;
    ImageButton imageButton2;
    ImageButton refresh;
    ImageView imageView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    ScrollView scrollView;
    ProgressBar progressBar;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    //
    RelativeLayout relativeLayout;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult == null){
                return;
            }

            for(Location location:locationResult.getLocations()){
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    mDatabase = FirebaseDatabase.getInstance().getReference();
                    HashMap<String, Object> values = new HashMap<>();
                    values.put("Current Country Location", getAddress(location.getLatitude(),location.getLongitude()));
                    values.put("Current Place Location", getAddress2(location.getLatitude(),location.getLongitude()));
                    mDatabase.child("Users").child(user.getUid()).child("Location").setValue(values);
            }
        }
    };
    //
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

        Add(user.getUid(), user.getDisplayName(), user.getEmail());
        //
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //
        //getLocation();
        checkSettingsandStartLocation();
        //
        scrollView = view.findViewById(R.id.scroll);
        scrollView.smoothScrollTo(0, 0);
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
        cardView.setAnimation(animation);
        ;
        cardView2.setAnimation(animation);
        //
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        //
        circularProgressBar = view.findViewById(R.id.pb_one);

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
                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            String icm = dataSnapshot.child("Track Data").child("Track Data").getValue().toString();
                            if (icm  != null){
                                BottomSheetNFC bottomSheet = new BottomSheetNFC();
                                bottomSheet.show(getFragmentManager(), "TAG");
                            }
                        }catch(Exception e){
                            Toast.makeText(getContext(),"Please add Track Data",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
        handler.post(new Runnable() {
            @Override
            public void run() {
                Activity();
            }
        });
        //
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
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

        if (link != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/" + user.getUid());
            ref.putFile(link)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            try {
                                GetImage();
                            } catch (Exception e) {
                            }
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
        StorageReference storageRef = storageReference.child("images/" + user.getUid());

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

    public void Activity() {
//       TODO Data Injection;



//        for (int i = 0; i < Names.size(); i++) {
//            user = FirebaseAuth.getInstance().getCurrentUser();
//            HashMap<String, Object> values = new HashMap<>();
//            values.put("Name", Names.get(i));
//            values.put("Location", Location.get(i));
//            values.put("Amount", Amount.get(i));
//            values.put("Date", Date.get(i));
//            mDatabase.child("Users").child(user.getUid()).child("Transactions").child("" + i).updateChildren(values);
//        }

//            HashMap<String, Object> values2 = new HashMap<>();
//            values2.put("Transaction count",Names.size());
//            mDatabase.child("Users").child(user.getUid()).child("User Data").updateChildren(values2);
            //
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String xl = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                int SI = Integer.parseInt(xl);
                //
                p = new ArrayList<>();
                // getting recycler data
                for (int i = 0; i < SI; i++) {
                        try {
                            Names.add(dataSnapshot.child("Transactions").child("" + i).child("Name").getValue().toString());
                            Location.add(dataSnapshot.child("Transactions").child("" + i).child("Location").getValue().toString());
                            Amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                            Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                        }catch(Exception e){}
                    }
                    if (Integer.parseInt(xl) == 0) {
                        textView4.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView4.setVisibility(View.INVISIBLE);
                    }

                for (int i = SI-1; i >=0; i--) {
                    p.add(new PurchaseItems(Names.get(i), Location.get(i), "\t\t$" + Amount.get(i), Date.get(i)));
                    recyclerView.setAdapter(new Adapter(getContext(), p));
                }
                //
                float val = 0;
                float val1 = 0;

                for (int i = 0; i < SI; i++) {
                    val1 = Float.parseFloat(Amount.get(i));
                    val = val + val1;
                }
                textView2.setText("$" + val);
                Float finalVal = val;
                // val and % data;
                String x;
                try {
                    x = dataSnapshot.child("Income").child("income").getValue().toString();
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
                } catch (Exception e) {

                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void checkSettingsandStartLocation() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();

        SettingsClient client = LocationServices.getSettingsClient(getContext());

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Settings of device are satisfied we can start updates;
                startLocationUpdate();
            }
        });

        //

        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(getActivity(), 1001);
                    } catch (IntentSender.SendIntentException sendIntentException) {
                        sendIntentException.printStackTrace();
                    }
                }
            }
        });
    }

    private void startLocationUpdate() {
        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }catch(Exception e){}
    }




    private void stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }


    @Override
    public void onStop(){
        super.onStop();
     stopLocationUpdate();
    }



    public String getAddress(double lat, double lng) {
        String add = null;
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            //add = obj.getAddressLine(0);
            add = obj.getCountryName();
        }catch (Exception e){}
//            add = add + "\n" + obj.getCountryCode();
//            add = add + "\n" + obj.getAdminArea();
//            add = add + "\n" + obj.getPostalCode();
//            add = add + "\n" + obj.getSubAdminArea();
//            add = add + "\n" + obj.getLocality();
//            add = add + "\n" + obj.getSubThoroughfare();
            // TennisAppActivity.showDialog(add);
        return add;
    }

    public String getAddress2(double lat, double lng) {
        String add = null;
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            add = obj.getFeatureName();
        }catch (Exception e){}
//            add = add + "\n" + obj.getCountryCode();
//            add = add + "\n" + obj.getAdminArea();
//            add = add + "\n" + obj.getPostalCode();
//            add = add + "\n" + obj.getSubAdminArea();
//            add = add + "\n" + obj.getLocality();
//            add = add + "\n" + obj.getSubThoroughfare();
        // TennisAppActivity.showDialog(add);
        return add;
    }

}