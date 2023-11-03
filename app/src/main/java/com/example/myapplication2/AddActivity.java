package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText dateInput;
    private EditText valueInput;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dateInput = findViewById(R.id.date_input);
        valueInput = findViewById(R.id.value_input);
        addButton = findViewById(R.id.add_button);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput.setText(currentDate);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                String date = dateInput.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput.getText().toString(); // Pobierz wartość z pola EditText
                myDB.addMeasurement(value, date); // Przekazujesz wartość i datę do metody
                Intent intent = new Intent( AddActivity.this, Biceps_lewy.class );
                intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity( intent );
                finish();
            }
        });
    }

}
