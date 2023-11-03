package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity_Udo extends AppCompatActivity {

    EditText value_input_Udo, date_input_Udo;
    Button update_button_Udo, delete_button_Udo;
    String id_Udo, value_Udo, date_Udo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_udo);

        value_input_Udo = findViewById(R.id.value_input_Udo);
        date_input_Udo = findViewById(R.id.date_input_Udo);
        update_button_Udo = findViewById(R.id.update_button_Udo);
        delete_button_Udo = findViewById(R.id.delete_button_Udo);
        getAndSetIntentData();
        update_button_Udo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Udo myDB_Udo = new MyDatabaseHelper_Udo(UpdateActivity_Udo.this);
                value_Udo = value_input_Udo.getText().toString();
                date_Udo = date_input_Udo.getText().toString();
                myDB_Udo.updateData(id_Udo, value_Udo, date_Udo);
                finish();
            }
        });

        delete_button_Udo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("value_Udo") && getIntent().hasExtra("date_Udo") && getIntent().hasExtra("id_Udo")) {
            id_Udo = getIntent().getStringExtra("id_Udo");
            value_Udo = getIntent().getStringExtra("value_Udo");
            date_Udo = getIntent().getStringExtra("date_Udo");

            value_input_Udo.setText(value_Udo);
            date_input_Udo.setText(date_Udo);
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
                MyDatabaseHelper_Udo myDB_Udo = new MyDatabaseHelper_Udo(UpdateActivity_Udo.this);
                myDB_Udo.deleteOneRow(id_Udo);
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
