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

public class UpdateActivity extends AppCompatActivity {

    EditText value_input, date_input;
    Button update_button, delete_button;
    String id,value,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        value_input = findViewById( R.id.value_input2 );
        date_input = findViewById( R.id.date_input2 );
        update_button = findViewById( R.id.update_button );
        delete_button = findViewById( R.id.delete_button );
        getAndSetIntentData();
        update_button.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                MyDatabaseHelper myDB = new MyDatabaseHelper( UpdateActivity.this );
                value = value_input.getText().toString();
                date = date_input.getText().toString();
                myDB.updateData( id, value, date );
                finish();
            }
        });

        delete_button.setOnClickListener( new View.OnClickListener()
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
        if( getIntent().hasExtra( "value" ) && getIntent().hasExtra( "date" ) && getIntent().hasExtra( "id" ) )
        {
            id = getIntent().getStringExtra( "id" );
            value = getIntent().getStringExtra( "value" );
            date = getIntent().getStringExtra( "date" );

            value_input.setText( value );
            date_input.setText( date );
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
                MyDatabaseHelper myDB = new MyDatabaseHelper( UpdateActivity.this );
                myDB.deleteOneRow( id );
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