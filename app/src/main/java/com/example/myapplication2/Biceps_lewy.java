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

public class Biceps_lewy extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button, delete_all_button;
    ImageView empty_imageView;
    TextView empty_textView;
    MyDatabaseHelper myDB;
    ArrayList<String> meas_id, value, date;
    CustomAdapter customAdapter;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biceps_lewy);

        recyclerView = findViewById( R.id.recyclerView );
        add_button = findViewById( R.id.add_button );
        delete_all_button = findViewById( R.id.delete_all_button );
        empty_imageView = findViewById( R.id.empty_imageView);
        empty_textView = findViewById( R.id.empty_textView );
        recyclerView = findViewById( R.id.recyclerView );
        add_button = findViewById( R.id.add_button );
        delete_all_button = findViewById( R.id.delete_all_button );
        lineChart = findViewById( R.id.chart );

        delete_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                confirmDialog();
            }
        });

        add_button.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( Biceps_lewy.this,AddActivity.class );
                startActivity( intent );
            }
        });

        myDB = new MyDatabaseHelper( Biceps_lewy.this );
        meas_id = new ArrayList<>();
        value = new ArrayList<>();
        date = new ArrayList<>();

        storeDataInArray();
        customAdapter = new CustomAdapter( Biceps_lewy.this, this, meas_id,value, date );
        recyclerView.setAdapter( customAdapter );
        recyclerView.setLayoutManager( new LinearLayoutManager( Biceps_lewy.this ) );

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {
        // Konfiguracja wykresu
        lineChart.getDescription().setEnabled(false);
        lineChart.setDrawGridBackground(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart.moveViewToX(0f); // Przesunięcie wykresu na początek
    }




    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date.get(i);
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
        XAxis xAxis = lineChart.getXAxis();
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

        lineChart.setData(lineData);
        lineChart.invalidate(); // Odśwież wykres
    }



    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data )
    {
        super.onActivityResult( requestCode,resultCode,data );
        if( requestCode == 1 )
        {
            recreate();
        }
    }

    void storeDataInArray()
    {
        Cursor cursor = myDB.readAllData();
        if( cursor.getCount() == 0 )
        {
            empty_textView.setVisibility( View.VISIBLE );
            empty_imageView.setVisibility( View.VISIBLE );
        }
        else
        {
            while( cursor.moveToNext() )
            {
                meas_id.add( cursor.getString( 0 ) );
                value.add( cursor.getString( 1 ) );
                date.add( cursor.getString( 2 ) );
            }
            empty_textView.setVisibility( View.GONE );
            empty_imageView.setVisibility( View.GONE );
        }
    }

    void confirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Usunąć wszystko?" );
        builder.setMessage( "Czy na pewno chcesz wszystko usunąć?" );
        builder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                MyDatabaseHelper myDB = new MyDatabaseHelper( Biceps_lewy.this );
                myDB.deleteAllData();
                customAdapter.notifyDataSetChanged();
                Toast.makeText(Biceps_lewy.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( Biceps_lewy.this, Biceps_lewy.class );
                startActivity( intent );
                finish();
                finish();
            }
        });

        builder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });

        builder.create().show();
    }
}