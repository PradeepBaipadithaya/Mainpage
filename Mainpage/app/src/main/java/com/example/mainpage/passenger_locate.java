package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mainpage.databinding.ActivityPassengerLocateBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class passenger_locate extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPassengerLocateBinding binding;
    public DatabaseReference reference_bus;
    public DatabaseReference reference_user;
    String userID;
    Marker myMarker;
    Marker myMarker_user;
    public Double locationLat=12.132;
    public Double locationLong=75.21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        String locationLat = getIntent().getStringExtra("lat");
//        String locationLong = getIntent().getStringExtra("long");

        binding = ActivityPassengerLocateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        Toast.makeText(this, ""+locationLat, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, ""+locationLong, Toast.LENGTH_SHORT).show();

        reference_bus = FirebaseDatabase.getInstance("https://mainpage-1398d-default-rtdb.firebaseio.com/").getReference("Location details").child("Conductor").child("test");
        reference_user = FirebaseDatabase.getInstance("https://mainpage-1398d-default-rtdb.firebaseio.com/").getReference("Location details").child("Passenger").child("testing");
        try {
            read_user_location();
            read_bus_location();
        }catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    private void read_user_location() {
        try {
            reference_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String values[] = new String[2];
                    int i=0;
                    for (DataSnapshot data:snapshot.getChildren()) {
                        String abc=data.getValue().toString();
                        values[i++]=abc;
                        if(i==2){
                            locationLat = Double.parseDouble(values[0]);
                            locationLong = Double.parseDouble(values[1]);
                            myMarker_user.setPosition(new LatLng(locationLat, locationLong));
                            Toast.makeText(passenger_locate.this, ""+locationLat, Toast.LENGTH_SHORT).show();
                            i=0;
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(passenger_locate.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(passenger_locate.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void read_bus_location() {
        try {
            reference_bus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String values[] = new String[2];
                    int i=0;
                    for (DataSnapshot data:snapshot.getChildren()) {
                        String abc=data.getValue().toString();
                        values[i++]=abc;
                        if(i==2){
                            locationLat = Double.parseDouble(values[0]);
                            locationLong = Double.parseDouble(values[1]);
                            myMarker.setPosition(new LatLng(locationLat, locationLong));
                            Toast.makeText(passenger_locate.this, ""+locationLat, Toast.LENGTH_SHORT).show();
                            i=0;
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(passenger_locate.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(passenger_locate.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        userID = intent.getStringExtra("conductor_email");

        // Add a marker in Sydney and move the camera

        LatLng currentLocation = new LatLng(locationLat, locationLong);
        myMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Bus Location"));
        myMarker_user = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(locationLat, locationLong));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(7);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

//        read_location();
    }


}