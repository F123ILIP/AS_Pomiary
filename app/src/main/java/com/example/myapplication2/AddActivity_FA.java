package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_FA extends AppCompatActivity {

    private EditText dateInput_FA;
    private EditText valueInput_FA;
    private Button addButton_FA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fa);

        dateInput_FA = findViewById(R.id.date_input_FA);
        valueInput_FA = findViewById(R.id.value_input_FA);
        addButton_FA = findViewById(R.id.add_button_FA);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_FA.setText(currentDate);

        addButton_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_FA myDB_FA = new MyDatabaseHelper_FA(AddActivity_FA.this);
                String date = dateInput_FA.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_FA.getText().toString(); // Pobierz wartość z pola EditText
                myDB_FA.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent(AddActivity_FA.this, FA.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
