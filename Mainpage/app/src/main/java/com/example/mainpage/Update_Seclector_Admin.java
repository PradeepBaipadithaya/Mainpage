package com.example.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Update_Seclector_Admin extends AppCompatActivity {
    Button cond,trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_seclector_admin);
        cond=findViewById(R.id.updatecond);
        trip=findViewById(R.id.updatetrip);
        cond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cond.animate().setDuration(500).rotationXBy(360f).start();
                Intent i =new Intent(Update_Seclector_Admin.this, AdminControl.class);
                startActivity(i);
            }
        });
        trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cond.animate().setDuration(500).rotationXBy(360f).start();
                Intent i =new Intent(Update_Seclector_Admin.this, AdminControl1.class);
                startActivity(i);
            }
        });
    }
}