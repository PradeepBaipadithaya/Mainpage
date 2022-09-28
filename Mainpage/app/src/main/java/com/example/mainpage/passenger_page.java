package com.example.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class passenger_page extends AppCompatActivity {

    EditText e1,e2;
    LinearLayout l1;
    Button fetch_location;
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
                    String conductor_num = e1.getText().toString();
                    String conductor_bus_num = e2.getText().toString();
                    Intent intent = new Intent(passenger_page.this, passenger_start.class);
                    intent.putExtra("conductor_email", conductor_num);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(passenger_page.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}