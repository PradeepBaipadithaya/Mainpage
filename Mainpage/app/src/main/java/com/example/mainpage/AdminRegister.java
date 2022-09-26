package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRegister extends AppCompatActivity {
    EditText email,pass;
    Button btn,btn2;
    ProgressDialog progressDialog;
    String emailpattern= "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}";


    FirebaseAuth mAuth;

    FirebaseUser muser;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        email=findViewById(R.id.admine);
        pass=findViewById(R.id.adminpass);
        btn=findViewById(R.id.pass6);
        btn2=findViewById(R.id.pass10);

        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(AdminRegister.this, Admin_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }

            private void performAuth() {
                String emailid=email.getText().toString();
                String passwd=pass.getText().toString();
                progressDialog=new ProgressDialog(AdminRegister.this);


                if(validateEmail(emailid)){

                    progressDialog.setMessage("Please Register....");
                    progressDialog.setTitle("Registartion");
                    progressDialog.setCanceledOnTouchOutside(false);

                    progressDialog.show();


                    mAuth.createUserWithEmailAndPassword(emailid,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.dismiss();
                                senduserTonextActivity();
                                Toast.makeText(AdminRegister.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(AdminRegister.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else if(passwd.isEmpty() || passwd.length()<6){
                    pass.setError("ENTER CORRECT PASSWORD");
                }
                else{                              email.setError("ENTER CORRECT EMAIL");
                }
            }

            private void senduserTonextActivity() {
                Intent i =new Intent(AdminRegister.this, Admin_login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

    }
    private boolean validateEmail(String pass) {
        Pattern pt;
        Matcher mac;
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        mac=regex.matcher(pass);
        return mac.matches();
    }
}