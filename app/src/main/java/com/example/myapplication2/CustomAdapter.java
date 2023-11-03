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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    private ArrayList meas_id,value,date;
    CustomAdapter( Activity activity, Context context, ArrayList meas_id, ArrayList value, ArrayList date )
    {
        this.activity = activity;
        this.context = context;
        this.meas_id = meas_id;
        this.value = value;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View view = inflater.inflate( R.layout.my_r, parent, false );
        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.meas_id_txt.setText( String.valueOf( meas_id.get( position ) ) );
        holder.value_txt.setText( String.valueOf( value.get( position ) ) + " cm" );
        holder.date_txt.setText( String.valueOf( date.get( position ) ) );
        holder.mainLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( context, UpdateActivity.class );
                intent.putExtra( "id", String.valueOf( meas_id.get( position ) ) );
                intent.putExtra( "value", String.valueOf( value.get( position ) ) );
                intent.putExtra( "date", String.valueOf( date.get( position ) ) );
                activity.startActivityForResult( intent,1 );
            }
        });
    }

    @Override
    public int getItemCount() {

        return meas_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView meas_id_txt, value_txt, date_txt;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            meas_id_txt = itemView.findViewById( R.id.meas_id_txt );
            value_txt = itemView.findViewById( R.id.value_txt );
            date_txt = itemView.findViewById( R.id.date_txt );
            mainLayout = itemView.findViewById( R.id.mainLayout );
        }
    }

}
