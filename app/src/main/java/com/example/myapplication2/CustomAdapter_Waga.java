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

public class CustomAdapter_Waga extends RecyclerView.Adapter<CustomAdapter_Waga.MyViewHolder> {
    private Context context_Waga;
    Activity activity_Waga;
    private ArrayList meas_id_Waga,value_Waga,date_Waga;
    CustomAdapter_Waga( Activity activity_Waga, Context context_Waga, ArrayList meas_id_Waga, ArrayList value_Waga, ArrayList date_Waga )
    {
        this.activity_Waga = activity_Waga;
        this.context_Waga = context_Waga;
        this.meas_id_Waga = meas_id_Waga;
        this.value_Waga = value_Waga;
        this.date_Waga = date_Waga;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context_Waga );
        View view = inflater.inflate( R.layout.my_r_waga, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt_Waga.setText( String.valueOf( meas_id_Waga.get( position ) ) );
        holder.value_txt_Waga.setText( String.valueOf( value_Waga.get( position ) ) + " kg" );
        holder.date_txt_Waga.setText( String.valueOf( date_Waga.get( position ) ) );
        holder.mainLayout_Waga.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context_Waga, UpdateActivity_Waga.class );
                intent.putExtra( "id_Waga", String.valueOf( meas_id_Waga.get( position ) ) );
                intent.putExtra( "value_Waga", String.valueOf( value_Waga.get( position ) ) );
                intent.putExtra( "date_Waga", String.valueOf( date_Waga.get( position ) ) );
                activity_Waga.startActivityForResult( intent,1 );
            }
        });
    }

    @Override
    public int getItemCount() {

        return meas_id_Waga.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt_Waga, value_txt_Waga, date_txt_Waga, moja_waga;
        ConstraintLayout mainLayout_Waga;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt_Waga = itemView.findViewById( R.id.meas_id_txt_Waga );
            value_txt_Waga = itemView.findViewById( R.id.value_txt_Waga );
            date_txt_Waga = itemView.findViewById( R.id.date_txt_Waga );
            mainLayout_Waga = itemView.findViewById( R.id.mainLayout_Waga );
        }
    }
}
