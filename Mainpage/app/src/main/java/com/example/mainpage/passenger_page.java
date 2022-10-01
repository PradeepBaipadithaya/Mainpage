package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class passenger_page extends AppCompatActivity {

    EditText e1,e2;
    LinearLayout l1;
    Button fetch_location;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef_email = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passanger);
        e1=findViewById(R.id.conductor_num);
        e2=findViewById(R.id.conductor_bus_num);
        fetch_location = findViewById(R.id.tripbtn);

        fetch_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String conductor_bus_num = e1.getText().toString();
                    String conductor_num = e2.getText().toString();
                    String key = conductor_bus_num+"_"+conductor_num;
                    if(conductor_num.equals("") && conductor_bus_num.equals("")){
                        Toast.makeText(passenger_page.this, "Any one of the field is mandatory", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        myRef.child("tripcollectorsinfo").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(key)) {
                                    DataSnapshot value = snapshot.child(key).child("conductoremail");
                                    String abc = value.getValue().toString();
                                    Intent intent = new Intent(passenger_page.this, passenger_start.class);
                                    intent.putExtra("conductor_bus_num", abc);
                                    startActivity(intent);
//                                        Toast.makeText(passenger_page.this, getpass, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(passenger_page.this, "Bus and mobile number not found"+key, Toast.LENGTH_SHORT).show();

                                    }
                                }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(passenger_page.this, "Some error occured", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }catch (Exception e){
                    Toast.makeText(passenger_page.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}