package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ticket_collector_login extends AppCompatActivity {
    EditText e1,e2;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_collector_login);
        e1 = findViewById(R.id.tripcollectoremail);
        e2 = findViewById(R.id.tripcollectorpass);
        b = findViewById(R.id.tripbtn);
        try {
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailid = e1.getText().toString();
                    String passwd = e2.getText().toString();

                    myRef.child("ticketcollectors").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(emailid)) {
                                final String getpass = snapshot.child(emailid).child("password").getValue().toString();
                                if (getpass.equals(passwd)) {
                                    Intent i = new Intent(ticket_collector_login.this, Trip_collector_work.class);
                                    startActivity(i);
                                    Toast.makeText(ticket_collector_login.this, getpass, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ticket_collector_login.this, getpass, Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Toast.makeText(ticket_collector_login.this, snapshot.child(emailid).toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ticket_collector_login.this, "Pleass", Toast.LENGTH_SHORT).show();

                        }

                    });
//
                }
            });

        } catch (Exception e) {
            Toast.makeText(ticket_collector_login.this, "" + e, Toast.LENGTH_SHORT).show();
        }
    }
}