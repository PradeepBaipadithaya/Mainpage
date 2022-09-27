package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Admin_login extends AppCompatActivity {
    EditText email,pass;
    Button btn;
    ProgressDialog progressDialog;
    String emailpattern= "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}";

    FirebaseAuth mAuth;
    FirebaseUser muser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        email=findViewById(R.id.lemail);
        pass=findViewById(R.id.lpass);
        btn=findViewById(R.id.ticket_collector_login);
        mAuth=FirebaseAuth.getInstance();
        muser=mAuth.getCurrentUser();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(Admin_login.this,Update_Seclector_Admin.class);
                //startActivity(i);

                preformLogin();
            }
        });
    }

    private void preformLogin() {
        String emailid=email.getText().toString();
        String passwd=pass.getText().toString();
        progressDialog=new ProgressDialog(Admin_login.this);


        if(validateEmail(emailid)){

            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);

            progressDialog.show();

        mAuth.signInWithEmailAndPassword(emailid,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    senduserTonextActivity();
                    Toast.makeText(Admin_login.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(Admin_login.this,""+task.getException(),Toast.LENGTH_SHORT).show();
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
        Intent i =new Intent(Admin_login.this, Update_Seclector_Admin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private boolean validateEmail(String pass) {
        Pattern pt;
        Matcher mac;
        Pattern regex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        mac=regex.matcher(pass);
        return mac.matches();
    }
}