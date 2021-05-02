package com.example.upay;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.Adapters.Adapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BottomSheetHome  extends BottomSheetDialogFragment implements OnMapReadyCallback {
    private FirebaseUser user;
    MapView mMapView;
    public TextView Name,location,amount,date;
    //
    public ArrayList<String> Names = new ArrayList<>();
    public ArrayList<String> Location = new ArrayList<>();
    public ArrayList<String> Amount = new ArrayList<>();
    public ArrayList<String> Date = new ArrayList<>();
    public ArrayList<String> lat = new ArrayList<>();
    public ArrayList<String> lon = new ArrayList<>();

    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyBAr8I668hwoXJLZby2XJc4gzAj0CjHxs8";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view1 = inflater.inflate(R.layout.bottom_sheet_home, container, false);
        //
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        //
        Name = view1.findViewById(R.id.txtName);
        location = view1.findViewById(R.id.txtLocation);
        amount = view1.findViewById(R.id.txtAmount);
        date = view1.findViewById(R.id.textView22);
        //
        mMapView = (MapView)view1.findViewById(R.id.mapView);
        mMapView.onCreate(mapViewBundle);

        mMapView.getMapAsync(this);
        return view1;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        mMapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
     Function_Data(map);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void Function_Data(GoogleMap map){
        user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    String position = dataSnapshot.child("Home Position").child("Position").getValue().toString();
                    String xl = dataSnapshot.child("User Data").child("Transaction count").getValue().toString();
                    String Currency = dataSnapshot.child("Currency").child("Currency").getValue().toString();
                    int SI = Integer.parseInt(xl);

                    for (int i = SI-1; i >=0; i--) {
                        Names.add(dataSnapshot.child("Transactions").child("" + i).child("Name").getValue().toString());
                        Location.add(dataSnapshot.child("Transactions").child("" + i).child("Location").getValue().toString());
                        Amount.add(dataSnapshot.child("Transactions").child("" + i).child("Amount").getValue().toString());
                        Date.add(dataSnapshot.child("Transactions").child("" + i).child("Date").getValue().toString());
                        lat.add(dataSnapshot.child("Transactions").child("" + i).child("Latitude").getValue().toString());
                        lon.add(dataSnapshot.child("Transactions").child("" + i).child("Longitude").getValue().toString());
                    }
                    for (int i = SI-1; i >=0; i--) {
                        if (position.equals(""+i)) {
                            Name.setText(Names.get(i));
                            location.setText(Location.get(i));
                            if (Currency.equals("$")) {
                                amount.setText("$"+new DecimalFormat("##.##").format(Float.parseFloat(Amount.get(i))));
                            }
                            else if (Currency.equals("€")){
                                double val =  (0.83*Double.parseDouble(Amount.get(i)));
                                amount.setText("€"+new DecimalFormat("##.##").format(val));

                            }
                            else if (Currency.equals("$CA")){
                                double val = (1.23*Double.parseDouble(Amount.get(i)));
                                amount.setText("$CA"+new DecimalFormat("##.##").format(val));
                            }
                            date.setText(Date.get(i));
                            map.addMarker(new MarkerOptions().position(new LatLng(Float.parseFloat(lat.get(i)), Float.parseFloat(lon.get(i)))).title("Marker"));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Float.parseFloat(lat.get(i)), Float.parseFloat(lon.get(i))), 12.0f));
                        }
                    }
                }catch(Exception e){}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
