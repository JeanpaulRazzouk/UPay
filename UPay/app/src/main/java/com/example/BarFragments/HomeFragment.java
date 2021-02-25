package com.example.BarFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Adapters.Adapter;
import com.example.upay.BottomSheetNFC;
import com.example.Profile.Profile;
import com.example.upay.PurchaseItems;
import com.example.upay.R;
import com.example.upay.User;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    //
    ImageButton imageButton;
    ImageButton imageButton2;
    ImageView imageView;
    TextView textView;
    TextView textView2;
    TextView textView3;
    ScrollView scrollView;
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
    Animation animation2;
    CardView cardView;
    CardView cardView2;
    CircularProgressBar circularProgressBar;
    //
    private RecyclerView recyclerView;
    private com.example.Adapters.Adapter adapter;
    private ArrayList<PurchaseItems> itemsArrayList;
    // Adding Data;
    PurchaseItems [] p;
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
        textView.setText("Home");
        Shader shader = new LinearGradient(350,0,0,textView.getLineHeight(),
                Color.parseColor("#ffffff"), Color.parseColor("#D267E4"), Shader.TileMode.MIRROR);
        textView.getPaint().setShader(shader);
        //
        relativeLayout = view.findViewById(R.id.frag_home);
        // first row;
        animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.open_animation);
        animation2.setDuration(1000);
        imageView.setAnimation(animation2);
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
                // THIS IS JUST A TEST;
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
        Activity();
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

//    public Date StringToDate(String s){
//
//        Date result = null;
//        try{
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//            result  = dateFormat.parse(s);
//        }
//
//        catch(ParseException e){
//            e.printStackTrace();
//
//        }
//        return result ;
//    }

    public void Activity (){
        // TEST;
        ArrayList<Integer> myImageList = new ArrayList<>();
        myImageList.add(R.drawable.ic_french_fries);
        myImageList.add(R.drawable.ic_atom);
        myImageList.add(R.drawable.ic_french_fries);
        // TEST
//        Names.add(0,"McDonald's");
//        Location.add(0,"Beirut,LB");
//        Amount.add(0,"10.54");
//        Date.add(0,"03/12/2020");
        //
        Names.add(0,"Starbucks");
        Location.add(0,"Byblos,LB");
        Amount.add(0,"20");
        Date.add(0,"07/01/2021");
        //
        Names.add(0,"Zaatar w Zeit");
        Location.add(0,"Zalka,LB");
        Amount.add(0,"50");
        Date.add(0,"12/01/2021");
        //
        Names.add(0,"Wrangler");
        Location.add(0,"Dbayeh,LB");
        Amount.add(0,"15.50");
        Date.add(0,"17/02/2021");
        //
        Names.add(0,"Duty Free");
        Location.add(0,"Beirut,LB");
        Amount.add(0,"20.50");
        Date.add(0,"18/02/2021");
        //
        Names.add(0,"Burger King");
        Location.add(0,"Beirut,LB");
        Amount.add(0,"41.5");
        Date.add(0,"25/02/2021");

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


        user = FirebaseAuth.getInstance().getCurrentUser();
        Float finalVal = val;
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("User Data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String x;
                try {
                    x = dataSnapshot.child("income").getValue().toString();
                    int income = Integer.parseInt(x);
                    int perc_res = (int) ((finalVal /income)*100);
                    textView3.setText(perc_res+"%");
                    circularProgressBar.setProgressWithAnimation((float) perc_res, Long.valueOf(3000)); // 3 sec;
                    if (income < perc_res){
                        circularProgressBar.setProgressBarColor(Color.parseColor("#FF1D47"));
                    }
                    else if (perc_res > 0.60*income && perc_res < income){
                        circularProgressBar.setProgressBarColor(Color.parseColor("#FF9847"));
                    }
                    else {}

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        for (int i =0 ; i<p.length;i++) {
             p[i] = new PurchaseItems(Names.get(i), Location.get(i), "\t\t$" + Amount.get(i),Date.get(i));
            itemsArrayList.add(p[i]);
        }
    }
}