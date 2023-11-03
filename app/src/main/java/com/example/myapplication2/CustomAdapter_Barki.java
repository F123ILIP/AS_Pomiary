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

public class CustomAdapter_Barki extends RecyclerView.Adapter<CustomAdapter_Barki.MyViewHolder> {
    private Context context_Barki;
    Activity activity_Barki;
    private ArrayList meas_id_Barki,value_Barki,date_Barki;
    CustomAdapter_Barki( Activity activity_Barki, Context context_Barki, ArrayList meas_id_Barki, ArrayList value_Barki, ArrayList date_Barki )
    {
        this.activity_Barki = activity_Barki;
        this.context_Barki = context_Barki;
        this.meas_id_Barki = meas_id_Barki;
        this.value_Barki = value_Barki;
        this.date_Barki = date_Barki;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context_Barki );
        View view = inflater.inflate( R.layout.my_r_barki, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_Barki.setText( String.valueOf( meas_id_Barki.get( position ) ) );
        holder.value_txt_Barki.setText( String.valueOf( value_Barki.get( position ) ) + " cm" );
        holder.date_txt_Barki.setText( String.valueOf( date_Barki.get( position ) ) );
        holder.mainLayout_Barki.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context_Barki, UpdateActivity_Barki.class );
                intent.putExtra( "id_Barki", String.valueOf( meas_id_Barki.get( position ) ) );
                intent.putExtra( "value_Barki", String.valueOf( value_Barki.get( position ) ) );
                intent.putExtra( "date_Barki", String.valueOf( date_Barki.get( position ) ) );
                activity_Barki.startActivityForResult( intent,1 );
            }
        });
    }

    @Override
    public int getItemCount() {

        return meas_id_Barki.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt_Barki, value_txt_Barki, date_txt_Barki;
        ConstraintLayout mainLayout_Barki;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_Barki = itemView.findViewById( R.id.meas_id_txt_Barki );
            value_txt_Barki = itemView.findViewById( R.id.value_txt_Barki );
            date_txt_Barki = itemView.findViewById( R.id.date_txt_Barki );
            mainLayout_Barki = itemView.findViewById( R.id.mainLayout_Barki );
        }
    }
}
