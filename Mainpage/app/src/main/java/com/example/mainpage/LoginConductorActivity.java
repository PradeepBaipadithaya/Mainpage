package com.example.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginConductorActivity extends AppCompatActivity {

    EditText e1,e2;
    LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passanger);
        e1=findViewById(R.id.num1);

    }
}