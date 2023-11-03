package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_Klata extends AppCompatActivity {

    private EditText dateInput_Klata;
    private EditText valueInput_Klata;
    private Button addButton_Klata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_klata);

        dateInput_Klata = findViewById(R.id.date_input_Klata);
        valueInput_Klata = findViewById(R.id.value_input_Klata);
        addButton_Klata = findViewById(R.id.add_button_Klata);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_Klata.setText(currentDate);

        addButton_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Klata myDB_Klata = new MyDatabaseHelper_Klata(AddActivity_Klata.this);
                String date = dateInput_Klata.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_Klata.getText().toString(); // Pobierz wartość z pola EditText
                myDB_Klata.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent(AddActivity_Klata.this, Klata.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
