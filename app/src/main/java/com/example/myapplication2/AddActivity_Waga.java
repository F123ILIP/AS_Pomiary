package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication2.ui.notifications.NotificationsFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity_Waga extends AppCompatActivity {
    private EditText dateInput_Waga;
    private EditText valueInput_Waga;
    private Button addButton_Waga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waga);

        dateInput_Waga = findViewById(R.id.date_input_Waga);
        valueInput_Waga = findViewById(R.id.value_input_Waga);
        addButton_Waga = findViewById(R.id.add_button_Waga);

        // Pobierz bieżącą datę i ustaw ją w polu EditText "date_input"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
        String currentDate = dateFormat.format(new Date());
        dateInput_Waga.setText(currentDate);

        addButton_Waga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper_Waga myDB_Waga = new MyDatabaseHelper_Waga(AddActivity_Waga.this);
                String date = dateInput_Waga.getText().toString(); // Pobierz datę z pola EditText
                String value = valueInput_Waga.getText().toString(); // Pobierz wartość z pola EditText
                myDB_Waga.addMeasurement(value, date); // Przekazujesz wartość i datę do metody

                Intent intent = new Intent(AddActivity_Waga.this, Waga.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}

