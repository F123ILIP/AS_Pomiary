package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.node.IntermediateLayoutModifierNode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;

import java.util.ArrayList;

public class Brzuch extends AppCompatActivity {

    RecyclerView recyclerView_Brzuch;
    FloatingActionButton add_button_Brzuch, delete_all_button_Brzuch;
    ImageView empty_imageView_Brzuch;
    TextView empty_textView_Brzuch;
    MyDatabaseHelper_Brzuch myDB_Brzuch;
    ArrayList<String> meas_id_Brzuch, value_Brzuch, date_Brzuch;
    CustomAdapter_Brzuch customAdapter_Brzuch;
    LineChart lineChart_Brzuch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brzuch);

        recyclerView_Brzuch = findViewById(R.id.recyclerView_Brzuch);
        add_button_Brzuch = findViewById(R.id.add_button_Brzuch);
        delete_all_button_Brzuch = findViewById(R.id.delete_all_button_Brzuch);
        empty_imageView_Brzuch = findViewById(R.id.empty_imageView_Brzuch);
        empty_textView_Brzuch = findViewById(R.id.empty_textView_Brzuch);
        recyclerView_Brzuch = findViewById(R.id.recyclerView_Brzuch);
        add_button_Brzuch = findViewById(R.id.add_button_Brzuch);
        delete_all_button_Brzuch = findViewById(R.id.delete_all_button_Brzuch);
        lineChart_Brzuch = findViewById(R.id.chart_Brzuch);

        delete_all_button_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        add_button_Brzuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Brzuch.this, AddActivity_Brzuch.class);
                startActivity(intent);
            }
        });

        myDB_Brzuch = new MyDatabaseHelper_Brzuch(Brzuch.this);
        meas_id_Brzuch = new ArrayList<>();
        value_Brzuch = new ArrayList<>();
        date_Brzuch = new ArrayList<>();

        storeDataInArray();
        customAdapter_Brzuch = new CustomAdapter_Brzuch(Brzuch.this, this, meas_id_Brzuch, value_Brzuch, date_Brzuch);
        recyclerView_Brzuch.setAdapter(customAdapter_Brzuch);
        recyclerView_Brzuch.setLayoutManager(new LinearLayoutManager(Brzuch.this));

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {
        // Konfiguracja wykresu
        lineChart_Brzuch.getDescription().setEnabled(false);
        lineChart_Brzuch.setDrawGridBackground(false);

        XAxis xAxis = lineChart_Brzuch.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet

        YAxis leftAxis = lineChart_Brzuch.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);

        YAxis rightAxis = lineChart_Brzuch.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart_Brzuch.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart_Brzuch.setDragEnabled(true);
        lineChart_Brzuch.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart_Brzuch.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart_Brzuch.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart_Brzuch.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart_Brzuch.moveViewToX(0f); // Przesunięcie wykresu na początek
    }

    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date_Brzuch.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value_Brzuch.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date_Brzuch.get(i);
            String[] parts = fullDate.split(" "); // Podziel datę na część daty i czasu
            String[] dateParts = parts[0].split("-"); // Podziel część daty na rok, miesiąc i dzień
            String shortDate = dateParts[0] + "-" + dateParts[1]; // Zbuduj skróconą datę w formacie "dd-MM"
            labels.add(shortDate);
        }

        LineDataSet dataSet = new LineDataSet(entries, null); // Drugi argument ustaw na null, aby ukryć legendę

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        LineData lineData = new LineData(dataSets);

        // Ustaw etykiety dla osi X
        XAxis xAxis = lineChart_Brzuch.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Sprawdź, czy wartość mieści się w zakresie indeksów
                int index = (int) value;
                if (index >= 0 && index < labels.size()) {
                    return labels.get(index);
                }
                return ""; // Jeśli wartość jest poza zakresem, nie wyświetlaj etykiety
            }
        });

        lineChart_Brzuch.setData(lineData);
        lineChart_Brzuch.invalidate(); // Odśwież wykres
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArray() {
        Cursor cursor = myDB_Brzuch.readAllData();
        if (cursor.getCount() == 0) {
            empty_textView_Brzuch.setVisibility(View.VISIBLE);
            empty_imageView_Brzuch.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                meas_id_Brzuch.add(cursor.getString(0));
                value_Brzuch.add(cursor.getString(1));
                date_Brzuch.add(cursor.getString(2));
            }
            empty_textView_Brzuch.setVisibility(View.GONE);
            empty_imageView_Brzuch.setVisibility(View.GONE);
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć wszystko?");
        builder.setMessage("Czy na pewno chcesz wszystko usunąć?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper_Brzuch myDB = new MyDatabaseHelper_Brzuch(Brzuch.this);
                myDB.deleteAllData();
                customAdapter_Brzuch.notifyDataSetChanged();
                Toast.makeText(Brzuch.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Brzuch.this, Brzuch.class);
                startActivity(intent);
                finish();
                finish();
            }
        });

        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }
}
