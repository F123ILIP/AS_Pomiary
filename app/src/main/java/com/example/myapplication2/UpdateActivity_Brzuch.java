package com.example.myapplication2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity_Brzuch extends AppCompatActivity {

    EditText value_input_Brzuch, date_input_Brzuch;
    Button update_button_Brzuch, delete_button_Brzuch;
    String id_Brzuch, value_Brzuch, date_Brzuch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_brzuch);

        value_input_Brzuch = findViewById(R.id.value_input_Brzuch);
        date_input_Brzuch = findViewById(R.id.date_input_Brzuch);
        update_button_Brzuch = findViewById(R.id.update_button_Brzuch);
        delete_button_Brzuch = findViewById(R.id.delete_button_Brzuch);
        getAndSetIntentData();
        update_button_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Brzuch myDB_Brzuch = new MyDatabaseHelper_Brzuch(UpdateActivity_Brzuch.this);
                value_Brzuch = value_input_Brzuch.getText().toString();
                date_Brzuch = date_input_Brzuch.getText().toString();
                myDB_Brzuch.updateData(id_Brzuch, value_Brzuch, date_Brzuch);
                finish();
            }
        });

        delete_button_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("value_Brzuch") && getIntent().hasExtra("date_Brzuch") && getIntent().hasExtra("id_Brzuch")) {
            id_Brzuch = getIntent().getStringExtra("id_Brzuch");
            value_Brzuch = getIntent().getStringExtra("value_Brzuch");
            date_Brzuch = getIntent().getStringExtra("date_Brzuch");

            value_input_Brzuch.setText(value_Brzuch);
            date_input_Brzuch.setText(date_Brzuch);
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
                MyDatabaseHelper_Brzuch myDB_Brzuch = new MyDatabaseHelper_Brzuch(UpdateActivity_Brzuch.this);
                myDB_Brzuch.deleteOneRow(id_Brzuch);
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
