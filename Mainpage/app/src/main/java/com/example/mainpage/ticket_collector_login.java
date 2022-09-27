package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.mainpage.R.id.tripemail;
import static com.example.mainpage.R.id.trippass;
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

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mainpage-1398d-default-rtdb.firebaseio.com/");
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_collector_login);
        e1=findViewById(tripemail);
        e2=findViewById(trippass);
        b=findViewById(R.id.tripbtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid=e1.getText().toString();
                String passwd=e2.getText().toString();
                myRef.child("tripcollector").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(emailid)){
                            final String getpass=snapshot.child(emailid).child("password").getValue(String.class);
                            if(getpass.equals(passwd)){
                                Toast.makeText(ticket_collector_login.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(ticket_collector_login.this, "Please Check Above ", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ticket_collector_login.this, "Pleass", Toast.LENGTH_SHORT).show();

                    }

                });
            }
        });
    }
}