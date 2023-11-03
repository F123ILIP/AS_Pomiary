package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_Udo extends AppCompatActivity {

    private EditText dateInput_Udo;
    private EditText valueInput_Udo;
    private Button addButton_Udo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_udo);

        dateInput_Udo = findViewById(R.id.date_input_Udo);
        valueInput_Udo = findViewById(R.id.value_input_Udo);
        addButton_Udo = findViewById(R.id.add_button_Udo);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_Udo.setText(currentDate);

        addButton_Udo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Udo myDB_Udo = new MyDatabaseHelper_Udo(AddActivity_Udo.this);
                String date = dateInput_Udo.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_Udo.getText().toString(); // Pobierz wartość z pola EditText
                myDB_Udo.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent(AddActivity_Udo.this, Udo.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}
