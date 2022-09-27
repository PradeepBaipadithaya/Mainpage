package com.example.mainpage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ticket_collector_login extends AppCompatActivity {
    Button login;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_collector_login);
        login = findViewById(R.id.ticket_collector_login);
        email = findViewById(R.id.ticket_collector_email);
        password = findViewById(R.id.ticket_collector_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_entered = email.getText().toString();
                String pass_entered = password.getText().toString();
                if(email_entered.equals("") || pass_entered.equals("") ){
                    Toast.makeText(ticket_collector_login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent i =new Intent(ticket_collector_login.this, ticket_collector_bus.class);
                    startActivity(i);
                }

            }
        });
    }
}