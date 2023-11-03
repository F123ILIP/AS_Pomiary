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

public class Klata extends AppCompatActivity {

    RecyclerView recyclerView_Klata;
    FloatingActionButton add_button_Klata, delete_all_button_Klata;
    ImageView empty_imageView_Klata;
    TextView empty_textView_Klata;
    MyDatabaseHelper_Klata myDB_Klata;
    ArrayList<String> meas_id_Klata, value_Klata, date_Klata;
    CustomAdapter_Klata customAdapter_Klata;
    LineChart lineChart_Klata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klata);

        recyclerView_Klata = findViewById(R.id.recyclerView_Klata);
        add_button_Klata = findViewById(R.id.add_button_Klata);
        delete_all_button_Klata = findViewById(R.id.delete_all_button_Klata);
        empty_imageView_Klata = findViewById(R.id.empty_imageView_Klata);
        empty_textView_Klata = findViewById(R.id.empty_textView_Klata);
        recyclerView_Klata = findViewById(R.id.recyclerView_Klata);
        add_button_Klata = findViewById(R.id.add_button_Klata);
        delete_all_button_Klata = findViewById(R.id.delete_all_button_Klata);
        lineChart_Klata = findViewById(R.id.chart_Klata);

        delete_all_button_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        add_button_Klata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Klata.this, AddActivity_Klata.class);
                startActivity(intent);
            }
        });

        myDB_Klata = new MyDatabaseHelper_Klata(Klata.this);
        meas_id_Klata = new ArrayList<>();
        value_Klata = new ArrayList<>();
        date_Klata = new ArrayList<>();

        storeDataInArray();
        customAdapter_Klata = new CustomAdapter_Klata(Klata.this, this, meas_id_Klata, value_Klata, date_Klata);
        recyclerView_Klata.setAdapter(customAdapter_Klata);
        recyclerView_Klata.setLayoutManager(new LinearLayoutManager(Klata.this));

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {
        // Konfiguracja wykresu
        lineChart_Klata.getDescription().setEnabled(false);
        lineChart_Klata.setDrawGridBackground(false);

        XAxis xAxis = lineChart_Klata.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet

        YAxis leftAxis = lineChart_Klata.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);

        YAxis rightAxis = lineChart_Klata.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart_Klata.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart_Klata.setDragEnabled(true);
        lineChart_Klata.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart_Klata.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart_Klata.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart_Klata.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart_Klata.moveViewToX(0f); // Przesunięcie wykresu na początek
    }

    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date_Klata.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value_Klata.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date_Klata.get(i);
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
        XAxis xAxis = lineChart_Klata.getXAxis();
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

        lineChart_Klata.setData(lineData);
        lineChart_Klata.invalidate(); // Odśwież wykres
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArray() {
        Cursor cursor = myDB_Klata.readAllData();
        if (cursor.getCount() == 0) {
            empty_textView_Klata.setVisibility(View.VISIBLE);
            empty_imageView_Klata.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                meas_id_Klata.add(cursor.getString(0));
                value_Klata.add(cursor.getString(1));
                date_Klata.add(cursor.getString(2));
            }
            empty_textView_Klata.setVisibility(View.GONE);
            empty_imageView_Klata.setVisibility(View.GONE);
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć wszystko?");
        builder.setMessage("Czy na pewno chcesz wszystko usunąć?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper_Klata myDB = new MyDatabaseHelper_Klata(Klata.this);
                myDB.deleteAllData();
                customAdapter_Klata.notifyDataSetChanged();
                Toast.makeText(Klata.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Klata.this, Klata.class);
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
