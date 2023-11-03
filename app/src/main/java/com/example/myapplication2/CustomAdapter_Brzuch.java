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

public class CustomAdapter_Brzuch extends RecyclerView.Adapter<CustomAdapter_Brzuch.MyViewHolder> {
    private Context context_Brzuch;
    Activity activity_Brzuch;
    private ArrayList meas_id_Brzuch, value_Brzuch, date_Brzuch;

    CustomAdapter_Brzuch(Activity activity_Brzuch, Context context_Brzuch, ArrayList meas_id_Brzuch, ArrayList value_Brzuch, ArrayList date_Brzuch) {
        this.activity_Brzuch = activity_Brzuch;
        this.context_Brzuch = context_Brzuch;
        this.meas_id_Brzuch = meas_id_Brzuch;
        this.value_Brzuch = value_Brzuch;
        this.date_Brzuch = date_Brzuch;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context_Brzuch);
        View view = inflater.inflate(R.layout.my_r_brzuch, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_Brzuch.setText(String.valueOf(meas_id_Brzuch.get(position)));
        holder.value_txt_Brzuch.setText(String.valueOf(value_Brzuch.get(position)) + " cm");
        holder.date_txt_Brzuch.setText(String.valueOf(date_Brzuch.get(position)));
        holder.mainLayout_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context_Brzuch, UpdateActivity_Brzuch.class);
                intent.putExtra("id_Brzuch", String.valueOf(meas_id_Brzuch.get(position)));
                intent.putExtra("value_Brzuch", String.valueOf(value_Brzuch.get(position)));
                intent.putExtra("date_Brzuch", String.valueOf(date_Brzuch.get(position)));
                activity_Brzuch.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meas_id_Brzuch.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt_Brzuch, value_txt_Brzuch, date_txt_Brzuch;
        ConstraintLayout mainLayout_Brzuch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_Brzuch = itemView.findViewById(R.id.meas_id_txt_Brzuch);
            value_txt_Brzuch = itemView.findViewById(R.id.value_txt_Brzuch);
            date_txt_Brzuch = itemView.findViewById(R.id.date_txt_Brzuch);
            mainLayout_Brzuch = itemView.findViewById(R.id.mainLayout_Brzuch);
        }
    }
}
