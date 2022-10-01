package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;

import java.util.ArrayList;
import java.util.List;

public class passenger_locate extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityPassengerLocateBinding binding;
    public DatabaseReference reference_bus;
    public DatabaseReference reference_user;
    String userID;
    Marker myMarker;
    Marker myMarker_user;
    public double locationLat=12.132;
    public double locationLong=75.21;
    public double locationLat_user=19.132;
    public double locationLong_user=60.21;

    Double lat_val = locationLat;
    Double long_val = locationLong;
    Polyline polyline;


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
                            locationLat_user = Double.parseDouble(values[0]);
                            locationLong_user = Double.parseDouble(values[1]);
                            myMarker_user.setPosition(new LatLng(locationLat_user, locationLong_user));
//                            Toast.makeText(passenger_locate.this, ""+locationLat, Toast.LENGTH_SHORT).show();
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
            final boolean[] flag = {true};

            reference_bus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String values[] = new String[2];
                    int i=0;
                    if(flag[0]){
                        rout();
                        flag[0] =false;
                    }
                    polyline.remove();
                    for (DataSnapshot data:snapshot.getChildren()) {
                        String abc=data.getValue().toString();
                        values[i++]=abc;
                        if(i==2){
                            locationLat = Double.parseDouble(values[0]);
                            locationLong = Double.parseDouble(values[1]);
                            myMarker.setPosition(new LatLng(locationLat, locationLong));

                            rout();

//                            Toast.makeText(passenger_locate.this, ""+locationLat, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(passenger_locate.this, "" + e, Toast.LENGTH_LONG).show();
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
//        LatLng madrid = new LatLng(40.416775,-3.70379);
        LatLng currentLocation = new LatLng(locationLat, locationLong);
        myMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Bus Location"));
        myMarker_user = mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(locationLat, locationLong));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(7);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

//        read_location();
    }

    private void rout() {

//Double lat = locationLat;
//Double lat = locationLat;
        List<LatLng> path = new ArrayList();

        //Execute Directions API request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAGNT9WwAdNDz1o8P3Xrb0arxptCDVp1gM")
                .build();
//        String lat_user =Double.toString(locationLat_user);
//        String long_user =Double.toString(locationLong_user);
        String bus_loc = ""+locationLat+","+""+locationLong;
        String user_loc = ""+locationLat_user+","+""+locationLong_user;
//        Toast.makeText(this, ""+locationLat_user, Toast.LENGTH_LONG).show();
        DirectionsApiRequest req = DirectionsApi.getDirections(context, ""+user_loc, ""+bus_loc);
//        DirectionsApiRequest req = DirectionsApi.getDirections(context, "12.32421,75.23423", "13.231231,76.23421");
        try {
            DirectionsResult res = req.await();

            //Loop through legs and steps to get encoded polylines of each step
            if (res.routes != null && res.routes.length > 0) {
                DirectionsRoute route = res.routes[0];

                if (route.legs !=null) {
                    for(int i=0; i<route.legs.length; i++) {
                        DirectionsLeg leg = route.legs[i];
                        if (leg.steps != null) {
                            for (int j=0; j<leg.steps.length;j++){
                                DirectionsStep step = leg.steps[j];
                                if (step.steps != null && step.steps.length >0) {
                                    for (int k=0; k<step.steps.length;k++){
                                        DirectionsStep step1 = step.steps[k];
                                        EncodedPolyline points1 = step1.polyline;
                                        if (points1 != null) {
                                            //Decode polyline and add points to list of route coordinates
                                            List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                            for (com.google.maps.model.LatLng coord1 : coords1) {
                                                path.add(new LatLng(coord1.lat, coord1.lng));
                                            }
                                        }
                                    }
                                } else {
                                    EncodedPolyline points = step.polyline;
                                    if (points != null) {
                                        //Decode polyline and add points to list of route coordinates
                                        List<com.google.maps.model.LatLng> coords = points.decodePath();
                                        for (com.google.maps.model.LatLng coord : coords) {
                                            path.add(new LatLng(coord.lat, coord.lng));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch(Exception ex) {
            Log.e("tag", ex.getLocalizedMessage());
        }

        //Draw the polyline
        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(Color.BLUE).width(5);
            polyline =mMap.addPolyline(opts);

        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
    }


}