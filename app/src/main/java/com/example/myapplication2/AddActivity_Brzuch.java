package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_Brzuch extends AppCompatActivity {

    private EditText dateInput_Brzuch;
    private EditText valueInput_Brzuch;
    private Button addButton_Brzuch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brzuch);

        dateInput_Brzuch = findViewById(R.id.date_input_Brzuch);
        valueInput_Brzuch = findViewById(R.id.value_input_Brzuch);
        addButton_Brzuch = findViewById(R.id.add_button_Brzuch);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_Brzuch.setText(currentDate);

        addButton_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Brzuch myDB_Brzuch = new MyDatabaseHelper_Brzuch(AddActivity_Brzuch.this);
                String date = dateInput_Brzuch.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_Brzuch.getText().toString(); // Pobierz wartość z pola EditText
                myDB_Brzuch.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent(AddActivity_Brzuch.this, Brzuch.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
