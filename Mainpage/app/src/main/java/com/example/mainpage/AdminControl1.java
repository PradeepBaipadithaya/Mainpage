package com.example.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminControl1 extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ContactModel> arrcont= new ArrayList<>();

    FloatingActionButton mAddAlarmFab, mAddPersonFab,mupdate;


    ExtendedFloatingActionButton mAddFab;


    TextView addAlarmActionText, addPersonActionText,updatetext;

    Boolean isAllFabsVisible;

    Button add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_control1);


        //Floating Button------------------------------------------------------------------------------------------------>

        mAddFab = findViewById(R.id.add_fab);
        mAddAlarmFab = findViewById(R.id.add_alarm_fab);
        mAddPersonFab = findViewById(R.id.add_person_fab1);
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
                        Toast.makeText(AdminControl1.this, "Person Added", Toast.LENGTH_SHORT).show();
                    }
                });


        mAddAlarmFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AdminControl1.this, "Long Press On What You Want To Delete", Toast.LENGTH_SHORT).show();
                    }
                });
        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminControl1.this, "Click on What You Want To Delete", Toast.LENGTH_SHORT).show();
            }
        });


        //Recylcer View ------------------------------------------------------------------------------------->

        recyclerView = findViewById(R.id.recyler2);
        recyclerView.setLayoutManager( new LinearLayoutManager( this));
        RecyclerContactAdapter_Trip_Collector adapter = new RecyclerContactAdapter_Trip_Collector(this,arrcont);
        recyclerView.setAdapter(adapter);
        mAddPersonFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AdminControl1.this);
                dialog.setContentView(R.layout.add_layout_1);

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
                            Toast.makeText(AdminControl1.this, "Please Fill The Box To Add", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            arrcont.add(new ContactModel(name1,email1,pass1));
                            adapter.notifyItemInserted(arrcont.size()-1);
                            recyclerView.scrollToPosition(arrcont.size()-1);
                            Toast.makeText(AdminControl1.this, "Addded Sucessfully", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();

            }
        });


    }
}