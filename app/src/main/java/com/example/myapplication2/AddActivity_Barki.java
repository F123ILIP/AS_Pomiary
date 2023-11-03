package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_Barki extends AppCompatActivity {

    private EditText dateInput_Barki;
    private EditText valueInput_Barki;
    private Button addButton_Barki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barki);

        dateInput_Barki = findViewById(R.id.date_input_Barki);
        valueInput_Barki = findViewById(R.id.value_input_Barki);
        addButton_Barki = findViewById(R.id.add_button_Barki);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_Barki.setText(currentDate);

        addButton_Barki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Barki myDB_Barki = new MyDatabaseHelper_Barki(AddActivity_Barki.this);
                String date = dateInput_Barki.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_Barki.getText().toString(); // Pobierz wartość z pola EditText
                myDB_Barki.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent( AddActivity_Barki.this, Barki.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                finish();
            }
        });
    }

}
