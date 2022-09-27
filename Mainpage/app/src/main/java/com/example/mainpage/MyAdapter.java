package com.example.mainpage;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Adition> mList;

    public MyAdapter(Context context, ArrayList<Adition> mList) {
        this.mList=mList;
        this.context=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_view,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adition adi=mList.get(position);
        holder.name.setText(adi.getName());
        holder.email.setText(adi.getEmail());
        holder.pass.setText(adi.getPass());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
     TextView name,email,pass;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name=itemView.findViewById(R.id.name);
            email=itemView.findViewById(R.id.email);
            pass=itemView.findViewById(R.id.pass);





        }
    }
}
