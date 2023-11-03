package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

public class UpdateActivity_Waga extends AppCompatActivity {

    EditText value_input_Waga, date_input_Waga;
    Button update_button_Waga, delete_button_Waga;
    String id_Waga,value_Waga,date_Waga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_waga);

        value_input_Waga = findViewById( R.id.value_input_Waga );
        date_input_Waga = findViewById( R.id.date_input_Waga );
        update_button_Waga = findViewById( R.id.update_button_Waga );
        delete_button_Waga = findViewById( R.id.delete_button_Waga );
        getAndSetIntentData();
        update_button_Waga.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                MyDatabaseHelper_Waga myDB_Waga = new MyDatabaseHelper_Waga( UpdateActivity_Waga.this );
                value_Waga = value_input_Waga.getText().toString();
                date_Waga = date_input_Waga.getText().toString();
                myDB_Waga.updateData( id_Waga, value_Waga, date_Waga );
                finish();
            }
        });

        delete_button_Waga.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData()
    {
        if( getIntent().hasExtra( "value_Waga" ) && getIntent().hasExtra( "date_Waga" ) && getIntent().hasExtra( "id_Waga" ) )
        {
            id_Waga = getIntent().getStringExtra( "id_Waga" );
            value_Waga = getIntent().getStringExtra( "value_Waga" );
            date_Waga = getIntent().getStringExtra( "date_Waga" );

            value_input_Waga.setText( value_Waga );
            date_input_Waga.setText( date_Waga );
        }
        else
        {
            Toast.makeText( this, "Brak danych. ", Toast.LENGTH_SHORT ).show();
        }
    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Usunąć?" );
        builder.setMessage( "Czy na pewno chcesz usunąć?" );
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                MyDatabaseHelper_Waga myDB_Waga = new MyDatabaseHelper_Waga( UpdateActivity_Waga.this );
                myDB_Waga.deleteOneRow( id_Waga );
                finish();
            }
        });

        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });

        builder.create().show();
    }
}