package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button button;
    private Button button_barki;
    private Button button_klata;
    private Button button_udo;
    private Button button_fa;
    private Button button_brzuch;
    private Button button_bic_lewy;
    public void openWaga()
    {
        Intent intent = new Intent( this,Waga.class );
        startActivity( intent );
    }

    public void openBicLewy()
    {
        Intent intent_bic_lewy = new Intent( this,Biceps_lewy.class );
        startActivity( intent_bic_lewy );
    }

    public void openBarki()
    {
        Intent intent_barki = new Intent( this,Barki.class );
        startActivity( intent_barki );
    }

    public void openKlata()
    {
        Intent intent_klata = new Intent( this,Klata.class );
        startActivity( intent_klata );
    }

    public void openUdo()
    {
        Intent intent_udo = new Intent( this,Udo.class );
        startActivity( intent_udo );
    }

    public void openFA()
    {
        Intent intent_fa = new Intent( this,FA.class );
        startActivity( intent_fa );
    }

    public void openBrzuch()
    {
        Intent intent_brzuch = new Intent( this,Brzuch.class );
        startActivity( intent_brzuch );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        button = ( Button ) findViewById( R.id.wagaButton );
        button.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openWaga();
            }
        });

        button_bic_lewy = ( Button ) findViewById( R.id.bicLewyButton );
        button_bic_lewy.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openBicLewy();
            }
        });

        button_barki = ( Button ) findViewById( R.id.barkiButton );
        button_barki.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openBarki();
            }
        });

        button_klata = ( Button ) findViewById( R.id.klataButton );
        button_klata.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openKlata();
            }
        });

        button_udo = ( Button ) findViewById( R.id.udoButton );
        button_udo.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openUdo();
            }
        });

        button_fa = ( Button ) findViewById( R.id.faButton );
        button_fa.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openFA();
            }
        });

        button_brzuch = ( Button ) findViewById( R.id.brzuchButton );
        button_brzuch.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick( View v )
            {
                openBrzuch();
            }
        });

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


    }

}



















