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

public class Udo extends AppCompatActivity {

    RecyclerView recyclerView_Udo;
    FloatingActionButton add_button_Udo, delete_all_button_Udo;
    ImageView empty_imageView_Udo;
    TextView empty_textView_Udo;
    MyDatabaseHelper_Udo myDB_Udo;
    ArrayList<String> meas_id_Udo, value_Udo, date_Udo;
    CustomAdapter_Udo customAdapter_Udo;
    LineChart lineChart_Udo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udo);

        recyclerView_Udo = findViewById( R.id.recyclerView_Udo );
        add_button_Udo = findViewById( R.id.add_button_Udo );
        delete_all_button_Udo = findViewById( R.id.delete_all_button_Udo );
        empty_imageView_Udo = findViewById( R.id.empty_imageView_Udo );
        empty_textView_Udo = findViewById( R.id.empty_textView_Udo );
        recyclerView_Udo = findViewById( R.id.recyclerView_Udo );
        add_button_Udo = findViewById( R.id.add_button_Udo );
        delete_all_button_Udo = findViewById( R.id.delete_all_button_Udo );
        lineChart_Udo = findViewById( R.id.chart_Udo );

        delete_all_button_Udo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                confirmDialog();
            }
        });

        add_button_Udo.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( Udo.this, AddActivity_Udo.class );
                startActivity( intent );
            }
        });

        myDB_Udo = new MyDatabaseHelper_Udo( Udo.this );
        meas_id_Udo = new ArrayList<>();
        value_Udo = new ArrayList<>();
        date_Udo = new ArrayList<>();

        storeDataInArray();
        customAdapter_Udo = new CustomAdapter_Udo( Udo.this, this, meas_id_Udo, value_Udo, date_Udo );
        recyclerView_Udo.setAdapter( customAdapter_Udo );
        recyclerView_Udo.setLayoutManager( new LinearLayoutManager( Udo.this ) );

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {

        // Konfiguracja wykresu
        lineChart_Udo.getDescription().setEnabled(false);
        lineChart_Udo.setDrawGridBackground(false);

        XAxis xAxis = lineChart_Udo.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet
        xAxis.setAxisMinimum(0f); // Ustaw minimalną wartość osi X



        YAxis leftAxis = lineChart_Udo.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);


        YAxis rightAxis = lineChart_Udo.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart_Udo.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart_Udo.setDragEnabled(true);
        lineChart_Udo.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart_Udo.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart_Udo.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart_Udo.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart_Udo.moveViewToX(0f); // Przesunięcie wykresu na początek
    }

    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date_Udo.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value_Udo.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date_Udo.get(i);
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
        XAxis xAxis = lineChart_Udo.getXAxis();
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

        lineChart_Udo.setData(lineData);
        lineChart_Udo.invalidate(); // Odśwież wykres
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
        Cursor cursor = myDB_Udo.readAllData();
        if( cursor.getCount() == 0 )
        {
            empty_textView_Udo.setVisibility( View.VISIBLE );
            empty_imageView_Udo.setVisibility( View.VISIBLE );
        }
        else
        {
            while( cursor.moveToNext() )
            {
                meas_id_Udo.add( cursor.getString( 0 ) );
                value_Udo.add( cursor.getString( 1 ) );
                date_Udo.add( cursor.getString( 2 ) );
            }
            empty_textView_Udo.setVisibility( View.GONE );
            empty_imageView_Udo.setVisibility( View.GONE );
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
                MyDatabaseHelper_Udo myDB = new MyDatabaseHelper_Udo( Udo.this );
                myDB.deleteAllData();
                customAdapter_Udo.notifyDataSetChanged();
                Toast.makeText(Udo.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( Udo.this, Udo.class );
                startActivity( intent );
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
