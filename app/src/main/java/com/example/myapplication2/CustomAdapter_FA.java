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

public class CustomAdapter_FA extends RecyclerView.Adapter<CustomAdapter_FA.MyViewHolder> {
    private Context context_FA;
    Activity activity_FA;
    private ArrayList meas_id_FA, value_FA, date_FA;

    CustomAdapter_FA(Activity activity_FA, Context context_FA, ArrayList meas_id_FA, ArrayList value_FA, ArrayList date_FA) {
        this.activity_FA = activity_FA;
        this.context_FA = context_FA;
        this.meas_id_FA = meas_id_FA;
        this.value_FA = value_FA;
        this.date_FA = date_FA;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context_FA);
        View view = inflater.inflate(R.layout.my_r_fa, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_FA.setText(String.valueOf(meas_id_FA.get(position)));
        holder.value_txt_FA.setText(String.valueOf(value_FA.get(position)) + " cm");
        holder.date_txt_FA.setText(String.valueOf(date_FA.get(position)));
        holder.mainLayout_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context_FA, UpdateActivity_FA.class);
                intent.putExtra("id_FA", String.valueOf(meas_id_FA.get(position)));
                intent.putExtra("value_FA", String.valueOf(value_FA.get(position)));
                intent.putExtra("date_FA", String.valueOf(date_FA.get(position)));
                activity_FA.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meas_id_FA.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView meas_id_txt_FA, value_txt_FA, date_txt_FA;
        ConstraintLayout mainLayout_FA;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_FA = itemView.findViewById(R.id.meas_id_txt_FA);
            value_txt_FA = itemView.findViewById(R.id.value_txt_FA);
            date_txt_FA = itemView.findViewById(R.id.date_txt_FA);
            mainLayout_FA = itemView.findViewById(R.id.mainLayout_FA);
        }
    }
}
