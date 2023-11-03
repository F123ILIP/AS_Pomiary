package com.example.myapplication2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter_Klata extends RecyclerView.Adapter<CustomAdapter_Klata.MyViewHolder> {
    private Context context_Klata;
    Activity activity_Klata;
    private ArrayList meas_id_Klata, value_Klata, date_Klata;

    CustomAdapter_Klata(Activity activity_Klata, Context context_Klata, ArrayList meas_id_Klata, ArrayList value_Klata, ArrayList date_Klata) {
        this.activity_Klata = activity_Klata;
        this.context_Klata = context_Klata;
        this.meas_id_Klata = meas_id_Klata;
        this.value_Klata = value_Klata;
        this.date_Klata = date_Klata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context_Klata);
        View view = inflater.inflate(R.layout.my_r_klata, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_Klata.setText(String.valueOf(meas_id_Klata.get(position)));
        holder.value_txt_Klata.setText(String.valueOf(value_Klata.get(position)) + " cm");
        holder.date_txt_Klata.setText(String.valueOf(date_Klata.get(position)));
        holder.mainLayout_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context_Klata, UpdateActivity_Klata.class);
                intent.putExtra("id_Klata", String.valueOf(meas_id_Klata.get(position)));
                intent.putExtra("value_Klata", String.valueOf(value_Klata.get(position)));
                intent.putExtra("date_Klata", String.valueOf(date_Klata.get(position)));
                activity_Klata.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meas_id_Klata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt_Klata, value_txt_Klata, date_txt_Klata;
        ConstraintLayout mainLayout_Klata;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_Klata = itemView.findViewById(R.id.meas_id_txt_Klata);
            value_txt_Klata = itemView.findViewById(R.id.value_txt_Klata);
            date_txt_Klata = itemView.findViewById(R.id.date_txt_Klata);
            mainLayout_Klata = itemView.findViewById(R.id.mainLayout_Klata);
        }
    }
}
