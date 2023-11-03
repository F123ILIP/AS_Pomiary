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

public class CustomAdapter_Udo extends RecyclerView.Adapter<CustomAdapter_Udo.MyViewHolder> {
    private Context context_Udo;
    Activity activity_Udo;
    private ArrayList meas_id_Udo, value_Udo, date_Udo;

    CustomAdapter_Udo(Activity activity_Udo, Context context_Udo, ArrayList meas_id_Udo, ArrayList value_Udo, ArrayList date_Udo) {
        this.activity_Udo = activity_Udo;
        this.context_Udo = context_Udo;
        this.meas_id_Udo = meas_id_Udo;
        this.value_Udo = value_Udo;
        this.date_Udo = date_Udo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context_Udo);
        View view = inflater.inflate(R.layout.my_r_udo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_Udo.setText(String.valueOf(meas_id_Udo.get(position)));
        holder.value_txt_Udo.setText(String.valueOf(value_Udo.get(position)) + " cm");
        holder.date_txt_Udo.setText(String.valueOf(date_Udo.get(position)));
        holder.mainLayout_Udo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context_Udo, UpdateActivity_Udo.class);
                intent.putExtra("id_Udo", String.valueOf(meas_id_Udo.get(position)));
                intent.putExtra("value_Udo", String.valueOf(value_Udo.get(position)));
                intent.putExtra("date_Udo", String.valueOf(date_Udo.get(position)));
                activity_Udo.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meas_id_Udo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt_Udo, value_txt_Udo, date_txt_Udo;
        ConstraintLayout mainLayout_Udo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_Udo = itemView.findViewById(R.id.meas_id_txt_Udo);
            value_txt_Udo = itemView.findViewById(R.id.value_txt_Udo);
            date_txt_Udo = itemView.findViewById(R.id.date_txt_Udo);
            mainLayout_Udo = itemView.findViewById(R.id.mainLayout_Udo);
        }
    }
}
