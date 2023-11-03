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

public class Waga extends AppCompatActivity {

    private TextView mojaWagaTextView;
    RecyclerView recyclerView_Waga;
    FloatingActionButton add_button_Waga, delete_all_button_Waga;
    ImageView empty_imageView_Waga;
    TextView empty_textView_Waga;
    MyDatabaseHelper_Waga myDB_Waga;
    ArrayList<String> meas_id_Waga, value_Waga, date_Waga;
    CustomAdapter_Waga customAdapter_Waga;
    LineChart lineChart_Waga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waga);

        recyclerView_Waga = findViewById( R.id.recyclerView_Waga );
        add_button_Waga = findViewById( R.id.add_button_Waga );
        delete_all_button_Waga = findViewById( R.id.delete_all_button_Waga );
        empty_imageView_Waga = findViewById( R.id.empty_imageView_Waga );
        empty_textView_Waga = findViewById( R.id.empty_textView_Waga );
        recyclerView_Waga = findViewById( R.id.recyclerView_Waga );
        add_button_Waga = findViewById( R.id.add_button_Waga );
        delete_all_button_Waga = findViewById( R.id.delete_all_button_Waga );
        lineChart_Waga = findViewById( R.id.chart_Waga );

        delete_all_button_Waga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                confirmDialog();
            }
        });

        add_button_Waga.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View view )
            {
                Intent intent = new Intent( Waga.this,AddActivity_Waga.class );
                startActivity( intent );
            }
        });

        myDB_Waga = new MyDatabaseHelper_Waga( Waga.this );
        meas_id_Waga = new ArrayList<>();
        value_Waga = new ArrayList<>();
        date_Waga = new ArrayList<>();

        storeDataInArray();
        customAdapter_Waga = new CustomAdapter_Waga( Waga.this, this, meas_id_Waga, value_Waga, date_Waga );
        recyclerView_Waga.setAdapter( customAdapter_Waga );
        recyclerView_Waga.setLayoutManager( new LinearLayoutManager( Waga.this ) );

        setupLineChart();
        loadChartData();
    }

    private void setupLineChart() {
        // Konfiguracja wykresu
        lineChart_Waga.getDescription().setEnabled(false);
        lineChart_Waga.setDrawGridBackground(false);

        XAxis xAxis = lineChart_Waga.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.GRAY); // Kolor wartości na osi X (szary)
        xAxis.setTextSize(11f);
        xAxis.setGranularity(1f); // Ustaw granularność na 1, aby uniknąć przemieszczenia etykiet

        YAxis leftAxis = lineChart_Waga.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setTextColor(Color.GRAY); // Kolor wartości na osi Y (szary)
        leftAxis.setTextSize(12f);

        YAxis rightAxis = lineChart_Waga.getAxisRight();
        rightAxis.setEnabled(false);

        // Usunięcie legendy
        lineChart_Waga.getLegend().setEnabled(false);

        // Włącz przewijanie w poziomie
        lineChart_Waga.setDragEnabled(true);
        lineChart_Waga.setScaleEnabled(false); // Wyłącz skalowanie, aby uniknąć zmiany widoku

        // Ustaw zoomowanie na osi X
        lineChart_Waga.getViewPortHandler().setMaximumScaleX(4f); // Maksymalne powiększenie
        lineChart_Waga.getViewPortHandler().setMinimumScaleX(1f); // Minimalne powiększenie

        // Ustaw zachowanie przewijania
        lineChart_Waga.setVisibleXRangeMaximum(6f); // Maksymalna liczba widocznych etykiet na osi X
        lineChart_Waga.moveViewToX(0f); // Przesunięcie wykresu na początek
    }




    private void loadChartData() {
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(); // Lista do przechowywania dat w formacie "dd-MM"

        // Pobierz dane z bazy danych i dodaj je do wykresu
        int dataSize = date_Waga.size();
        int maxDisplayedPoints = 20;

        for (int i = Math.max(0, dataSize - maxDisplayedPoints); i < dataSize; i++) {
            float yValue = Float.parseFloat(value_Waga.get(i));
            entries.add(new Entry(i, yValue));

            // Konwertuj pełną datę na format "dd-MM" i dodaj do listy etykiet
            String fullDate = date_Waga.get(i);
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
        XAxis xAxis = lineChart_Waga.getXAxis();
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

        lineChart_Waga.setData(lineData);
        lineChart_Waga.invalidate(); // Odśwież wykres
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
        Cursor cursor = myDB_Waga.readAllData();
        if( cursor.getCount() == 0 )
        {
            empty_textView_Waga.setVisibility( View.VISIBLE );
            empty_imageView_Waga.setVisibility( View.VISIBLE );
        }
        else
        {
            while( cursor.moveToNext() )
            {
                meas_id_Waga.add( cursor.getString( 0 ) );
                value_Waga.add( cursor.getString( 1 ) );
                date_Waga.add( cursor.getString( 2 ) );
            }
            empty_textView_Waga.setVisibility( View.GONE );
            empty_imageView_Waga.setVisibility( View.GONE );
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
                MyDatabaseHelper_Waga myDB = new MyDatabaseHelper_Waga( Waga.this );
                myDB.deleteAllData();
                customAdapter_Waga.notifyDataSetChanged();
                Toast.makeText(Waga.this, "Wszystkie dane usunięte", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent( Waga.this, Waga.class );
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