package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity_Klata extends AppCompatActivity {

    EditText value_input_Klata, date_input_Klata;
    Button update_button_Klata, delete_button_Klata;
    String id_Klata, value_Klata, date_Klata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_klata);

        value_input_Klata = findViewById(R.id.value_input_Klata);
        date_input_Klata = findViewById(R.id.date_input_Klata);
        update_button_Klata = findViewById(R.id.update_button_Klata);
        delete_button_Klata = findViewById(R.id.delete_button_Klata);
        getAndSetIntentData();
        update_button_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Klata myDB_Klata = new MyDatabaseHelper_Klata(UpdateActivity_Klata.this);
                value_Klata = value_input_Klata.getText().toString();
                date_Klata = date_input_Klata.getText().toString();
                myDB_Klata.updateData(id_Klata, value_Klata, date_Klata);
                finish();
            }
        });

        delete_button_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("value_Klata") && getIntent().hasExtra("date_Klata") && getIntent().hasExtra("id_Klata")) {
            id_Klata = getIntent().getStringExtra("id_Klata");
            value_Klata = getIntent().getStringExtra("value_Klata");
            date_Klata = getIntent().getStringExtra("date_Klata");

            value_input_Klata.setText(value_Klata);
            date_input_Klata.setText(date_Klata);
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
                MyDatabaseHelper_Klata myDB_Klata = new MyDatabaseHelper_Klata(UpdateActivity_Klata.this);
                myDB_Klata.deleteOneRow(id_Klata);
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
