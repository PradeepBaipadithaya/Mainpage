package com.example.mainpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminControl extends AppCompatActivity {
    RecyclerView recyclerView;
  static ArrayList<ContactModel> arrcont= new ArrayList<>();


    FloatingActionButton mAddAlarmFab, mAddPersonFab,mupdate;


    ExtendedFloatingActionButton mAddFab;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mainpage-1398d-default-rtdb.firebaseio.com/");

    TextView addAlarmActionText, addPersonActionText,updatetext;

    Boolean isAllFabsVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control);


        //Floating Button------------------------------------------------------------------------------------------------>

        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab);
        mupdate=findViewById(R.id.update_person);
        updatetext=findViewById(R.id.add_person_action_textt);
        addAlarmActionText = findViewById(R.id.add_alarm_action_text);

        addPersonActionText = findViewById(R.id.add_person_action_text);
        mupdate.setVisibility(View.GONE);
        mAddAlarmFab.setVisibility(View.GONE);
        mAddPersonFab.setVisibility(View.GONE);
        addAlarmActionText.setVisibility(View.GONE);
        addPersonActionText.setVisibility(View.GONE);
        updatetext.setVisibility(View.GONE);
        isAllFabsVisible = false;
        mAddFab.shrink();



        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            mAddAlarmFab.show();
                            mupdate.show();
                            mAddPersonFab.show();
                            addAlarmActionText.setVisibility(View.VISIBLE);
                            updatetext.setVisibility(View.VISIBLE);
                            addPersonActionText.setVisibility(View.VISIBLE);
                            mAddFab.extend();


                            isAllFabsVisible = true;
                        } else {


                            mAddAlarmFab.hide();
                            mAddPersonFab.hide();
                            mupdate.hide();
                            addAlarmActionText.setVisibility(View.GONE);
                            addPersonActionText.setVisibility(View.GONE);
                            updatetext.setVisibility(View.GONE);

                            mAddFab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                });


        mAddPersonFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AdminControl.this, "Person Added", Toast.LENGTH_SHORT).show();
                    }
                });


        mAddAlarmFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AdminControl.this, "Long Press On What You Want To Delete", Toast.LENGTH_SHORT).show();
                    }
                });
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(AdminControl.this, "Click on What You Want To Delete", Toast.LENGTH_SHORT).show();
            }
        });


        //Recylcer View ------------------------------------------------------------------------------------->


        recyclerView = findViewById(R.id.recyler1);
        recyclerView.setLayoutManager( new LinearLayoutManager( this));
        RecyclerContactAdapter adapter = new RecyclerContactAdapter(this,arrcont);
        recyclerView.setAdapter(adapter);
        DatabaseReference myReff = FirebaseDatabase.getInstance().getReference().child("conductors");
        myReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    String abc=data.getKey();
                    Log.d("TAG",abc);
                    String detail[] = new String[3];
                    int i=0;
                    for (DataSnapshot data2:snapshot.child(abc).getChildren()) {
                        String abcd = data2.getValue().toString();
                        detail[i++]=abcd;
                        if(i==3){
                            arrcont.add(new ContactModel(detail[0],detail[1],detail[2]));
                            i=0;
                        }
//                        String name= data2.getChildren().getClass().getName().toString();
//                        String email=data2.getValue().toString();
                    }

                }
                adapter.notifyItemInserted(arrcont.size()-1);
                recyclerView.scrollToPosition(arrcont.size()-1);
                arrcont= new ArrayList<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        mAddPersonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AdminControl.this);
                dialog.setContentView(R.layout.add_layout);

                EditText name,email,pass;
                Button btn;
                email=dialog.findViewById(R.id.ipname);
                name=dialog.findViewById(R.id.ipemail);
                pass=dialog.findViewById(R.id.ippass);
                btn=dialog.findViewById(R.id.Done);


                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name1=name.getText().toString();
                        String email1=email.getText().toString();
                        String pass1=pass.getText().toString();
                        if(name1.isEmpty() || email1.isEmpty() || pass1.isEmpty()){
                            Toast.makeText(AdminControl.this, "Please Fill The Box To Add", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            myRef.child("conductors").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(email1)){
                                        Toast.makeText(AdminControl.this, "Email has already registered", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        //Updating database

                                        myRef.child("conductors").child(email1).child("email").setValue(name1).toString();
                                       myRef.child("conductors").child(email1).child("name").setValue(email1).toString();
                                        myRef.child("conductors").child(email1).child("password").setValue(pass1).toString();                ArrayList<ContactModel> arrcont= new ArrayList<>();

                                        //arrcont.add(new ContactModel(name1,email1,pass1));

                                        Toast.makeText(AdminControl.this, "Addded Sucessfully", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(AdminControl.this, "Email has already registered", Toast.LENGTH_SHORT).show();

                                }

                            });


                        }

                    }

                });
                dialog.show();

            }
        });
    }
}