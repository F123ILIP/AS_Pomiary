package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity_FA extends AppCompatActivity {

    EditText value_input_FA, date_input_FA;
    Button update_button_FA, delete_button_FA;
    String id_FA, value_FA, date_FA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_fa);

        value_input_FA = findViewById(R.id.value_input_FA);
        date_input_FA = findViewById(R.id.date_input_FA);
        update_button_FA = findViewById(R.id.update_button_FA);
        delete_button_FA = findViewById(R.id.delete_button_FA);
        getAndSetIntentData();

        update_button_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_FA myDB_FA = new MyDatabaseHelper_FA(UpdateActivity_FA.this);
                value_FA = value_input_FA.getText().toString();
                date_FA = date_input_FA.getText().toString();
                myDB_FA.updateData(id_FA, value_FA, date_FA);
                finish();
            }
        });

        delete_button_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("value_FA") && getIntent().hasExtra("date_FA") && getIntent().hasExtra("id_FA")) {
            id_FA = getIntent().getStringExtra("id_FA");
            value_FA = getIntent().getStringExtra("value_FA");
            date_FA = getIntent().getStringExtra("date_FA");

            value_input_FA.setText(value_FA);
            date_input_FA.setText(date_FA);
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
                MyDatabaseHelper_FA myDB_FA = new MyDatabaseHelper_FA(UpdateActivity_FA.this);
                myDB_FA.deleteOneRow(id_FA);
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
