package com.example.projekt_semestr_5;


import android.content.Intent;


import android.content.Context;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


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

import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;

    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope;
    private float[] accelometerData, gyroscopeData;

    private Button beginActivButton;
    private Button endActivButton;
    private ImageButton achivButton;
    private ImageButton statsButton;
    private TextView textActivity;
    private String nameActivity;
    public String zmienna ="Wszystkie twoje aktywności w przeciągu 7 dni";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSensors();
        mainmenu();


        beginActivButton = (Button) findViewById(R.id.begin_activ);
        endActivButton = (Button) findViewById(R.id.end_activ);
        achivButton = (ImageButton) findViewById(R.id.ButtonAchivments);
        statsButton = (ImageButton) findViewById(R.id.ButtonStats);
        beginActivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textActivity = findViewById(R.id.textActivity);
                textActivity.setText(nameActivity);
                ImageView img = (ImageView) findViewById(R.id.imageActivity);
                img.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.chair);
                beginActivButton.setVisibility(View.INVISIBLE);
                endActivButton.setVisibility(View.VISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), "ROZPOCZĘTO DZIAŁANIE PROGRAMU ", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
                toastTV.setTextColor(Color.RED);
                toast.show();
                startSensorMeasurement();
               // if(button.getVisibility()==View.INVISIBLE)
            }
        });
        endActivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) findViewById(R.id.imageActivity);
                img.setVisibility(View.INVISIBLE);
                beginActivButton.setVisibility(View.VISIBLE);
                endActivButton.setVisibility(View.INVISIBLE);
                Toast toast = Toast.makeText(getApplicationContext(), "ZAKOŃCZONO DZIAŁANIE PROGRAMU", Toast.LENGTH_SHORT);
                LinearLayout toastLayout = (LinearLayout) toast.getView();
                TextView toastTV = (TextView) toastLayout.getChildAt(0);
                toastTV.setTextSize(20);
                toastTV.setTextColor(Color.RED);
                toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
                toast.show();
                stopSensorMeasurement();
                nameActivity ="BRAK";
                textActivity = (TextView) findViewById(R.id.textActivity);
                textActivity.setText(nameActivity);
            }
        });
        achivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), AchivActivity.class);
                startActivity(intent);
            }
        });
        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), StatisticActivity.class);
                intent.putExtra("Zmienna", zmienna);
                startActivity(intent);
            }
        });


    }
    public void mainmenu() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //  ImageView img = (ImageView) findViewById(R.id.imageActivity);
        //  img.setImageResource(R.drawable.chair);

    }


    public void changeView(){



       //     setContentView(R.layout.fragment_home);
          //  ImageView img = (ImageView) findViewById(R.id.imageActivity);
         //   img.setImageResource(R.drawable.chair);

    }


  //  @Override
  //  public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
   //     return true;
   // }

  //  @Override
   // public boolean onSupportNavigateUp() {
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
      //  return NavigationUI.navigateUp(navController, mAppBarConfiguration)
       //         || super.onSupportNavigateUp();
   // }



    private void initializeSensors()
    {
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        accelometerData = new float[3];
        gyroscopeData = new float[3];

    }

    public void startSensorMeasurement()
    {
        sensorManager.registerListener(this, accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopSensorMeasurement()
    {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            accelometerData[0] = event.values[0];
            accelometerData[1] = event.values[1];
            accelometerData[2] = event.values[2];
        }
        if(sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            gyroscopeData[0] = event.values[0];
            gyroscopeData[1] = event.values[1];
            gyroscopeData[2] = event.values[2];
        }
    }

}
