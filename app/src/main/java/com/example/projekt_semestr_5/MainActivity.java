package com.example.projekt_semestr_5;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        int zmiennapierwszegouruchomienia=1;

        if(zmiennapierwszegouruchomienia == 0)
        {
            firstrun();
        }
        else
        {
            mainmenu();
        }

        button = (Button) findViewById(R.id.begin_activ);
        button2 = (Button) findViewById(R.id.end_activ);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) findViewById(R.id.imageActivity);
                img.setImageResource(R.drawable.chair);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), "ROZPOCZĘTO DZIAŁANIE PROGRAMU ", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
                toastTV.setTextColor(Color.RED);
                toast.show();
               // if(button.getVisibility()==View.INVISIBLE)

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) findViewById(R.id.imageActivity);
                img.setImageResource(R.drawable.walk);
                button.setVisibility(View.VISIBLE);
                button2.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), "ZAKOŃCZONO DZIAŁANIE PROGRAMU", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.RED);
                toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
                toast.show();
            }
        });


    }
    public void mainmenu() {
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //  ImageView img = (ImageView) findViewById(R.id.imageActivity);
        //  img.setImageResource(R.drawable.chair);

    }

    public void firstrun(){
        setContentView(R.layout.startup);
        Button button = (Button) findViewById(R.id.button_firstrun);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainmenu();
            }
        });

    }

    public void changeView(){



       //     setContentView(R.layout.fragment_home);
          //  ImageView img = (ImageView) findViewById(R.id.imageActivity);
         //   img.setImageResource(R.drawable.chair);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
