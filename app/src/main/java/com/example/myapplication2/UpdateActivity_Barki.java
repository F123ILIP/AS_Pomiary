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

public class UpdateActivity_Barki extends AppCompatActivity {

    EditText value_input_Barki, date_input_Barki;
    Button update_button_Barki, delete_button_Barki;
    String id_Barki, value_Barki, date_Barki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barki);

        value_input_Barki = findViewById(R.id.value_input_Barki);
        date_input_Barki = findViewById(R.id.date_input_Barki);
        update_button_Barki = findViewById(R.id.update_button_Barki);
        delete_button_Barki = findViewById(R.id.delete_button_Barki);
        getAndSetIntentData();
        update_button_Barki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Barki myDB_Barki = new MyDatabaseHelper_Barki(UpdateActivity_Barki.this);
                value_Barki = value_input_Barki.getText().toString();
                date_Barki = date_input_Barki.getText().toString();
                myDB_Barki.updateData(id_Barki, value_Barki, date_Barki);
                finish();
            }
        });

        delete_button_Barki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("value_Barki") && getIntent().hasExtra("date_Barki") && getIntent().hasExtra("id_Barki")) {
            id_Barki = getIntent().getStringExtra("id_Barki");
            value_Barki = getIntent().getStringExtra("value_Barki");
            date_Barki = getIntent().getStringExtra("date_Barki");

            value_input_Barki.setText(value_Barki);
            date_input_Barki.setText(date_Barki);
        } else {
            Toast.makeText(this, "Brak danych. ", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć?");
        builder.setMessage("Czy na pewno chcesz usunąć?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper_Barki myDB_Barki = new MyDatabaseHelper_Barki(UpdateActivity_Barki.this);
                myDB_Barki.deleteOneRow(id_Barki);
                finish();
            }
        });

        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        builder.create().show();
    }
}
