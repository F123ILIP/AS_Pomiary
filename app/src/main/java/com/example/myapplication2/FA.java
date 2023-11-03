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

public class FA extends AppCompatActivity {

    RecyclerView recyclerView_FA;
    FloatingActionButton add_button_FA, delete_all_button_FA;
    ImageView empty_imageView_FA;
    TextView empty_textView_FA;
    MyDatabaseHelper_FA myDB_FA;
    ArrayList<String> meas_id_FA, value_FA, date_FA;
    CustomAdapter_FA customAdapter_FA;
    LineChart lineChart_FA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fa);

        recyclerView_FA = findViewById(R.id.recyclerView_FA);
        add_button_FA = findViewById(R.id.add_button_FA);
        delete_all_button_FA = findViewById(R.id.delete_all_button_FA);
        empty_imageView_FA = findViewById(R.id.empty_imageView_FA);
        empty_textView_FA = findViewById(R.id.empty_textView_FA);
        recyclerView_FA = findViewById(R.id.recyclerView_FA);
        add_button_FA = findViewById(R.id.add_button_FA);
        delete_all_button_FA = findViewById(R.id.delete_all_button_FA);
        lineChart_FA = findViewById(R.id.chart_FA);

        delete_all_button_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        add_button_FA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FA.this, AddActivity_FA.class);
                startActivity(intent);
            }
        });

        myDB_FA = new MyDatabaseHelper_FA(FA.this);
        meas_id_FA = new ArrayList<>();
        value_FA = new ArrayList<>();
        date_FA = new ArrayList<>();

        storeDataInArray();
        customAdapter_FA = new CustomAdapter_FA(FA.this, this, meas_id_FA, value_FA, date_FA);
        recyclerView_FA.setAdapter(customAdapter_FA);
        recyclerView_FA.setLayoutManager(new LinearLayoutManager(FA.this));

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {
        // Konfiguracja wykresu
        lineChart_FA.getDescription().setEnabled(false);
        lineChart_FA.setDrawGridBackground(false);

        XAxis xAxis = lineChart_FA.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet

        YAxis leftAxis = lineChart_FA.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);

        YAxis rightAxis = lineChart_FA.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart_FA.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart_FA.setDragEnabled(true);
        lineChart_FA.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart_FA.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart_FA.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart_FA.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart_FA.moveViewToX(0f); // Przesunięcie wykresu na początek
    }

    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date_FA.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value_FA.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date_FA.get(i);
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
        XAxis xAxis = lineChart_FA.getXAxis();
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

        lineChart_FA.setData(lineData);
        lineChart_FA.invalidate(); // Odśwież wykres
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }

    void storeDataInArray() {
        Cursor cursor = myDB_FA.readAllData();
        if (cursor.getCount() == 0) {
            empty_textView_FA.setVisibility(View.VISIBLE);
            empty_imageView_FA.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                meas_id_FA.add(cursor.getString(0));
                value_FA.add(cursor.getString(1));
                date_FA.add(cursor.getString(2));
            }
            empty_textView_FA.setVisibility(View.GONE);
            empty_imageView_FA.setVisibility(View.GONE);
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Usunąć wszystko?");
        builder.setMessage("Czy na pewno chcesz wszystko usunąć?");
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper_FA myDB = new MyDatabaseHelper_FA(FA.this);
                myDB.deleteAllData();
                customAdapter_FA.notifyDataSetChanged();
                Toast.makeText(FA.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FA.this, FA.class);
                startActivity(intent);
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
