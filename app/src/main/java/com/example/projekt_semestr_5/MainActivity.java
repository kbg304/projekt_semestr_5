package com.example.projekt_semestr_5;


import android.content.Intent;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


import android.graphics.Color;
import android.graphics.PorterDuff;

import android.os.Bundle;

import androidx.core.content.res.TypedArrayUtils;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;


public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;

    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope;
    private float[] accelometerData, gyroscopeData, preparedData;

    private Button button;
    private Button button2;
    private ImageButton button3;
    private ImageButton button4;
    public String zmienna ="Wszystkie twoje aktywności w przeciągu 7 dni";
    private String modelFilename = "activity_ml_model.tflite";

    MachineLearning machineLearning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int zmiennapierwszegouruchomienia=1;

        initializeSensors();

        if(zmiennapierwszegouruchomienia == 0)
        {
            firstrun();
        }
        else
        {
            mainmenu();
        }

        openMLModel();



        button = (Button) findViewById(R.id.begin_activ);
        button2 = (Button) findViewById(R.id.end_activ);
        button3 = (ImageButton) findViewById(R.id.ButtonAchivments);
        button4 = (ImageButton) findViewById(R.id.ButtonStats);
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
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), AchivActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
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

    private void openMLModel()
    {
        try
        {

            //float[] test = new float[]{-0.051475335f, 9.563639f, 0.2669535f, -0.007078074f, 0.01732998f, 0.11274448f};


            MappedByteBuffer model = loadModelFile(getAssets(), modelFilename);

            machineLearning = new MachineLearning(model);

            //int prediction = machineLearning.getPrediction(test);

           // Log.d("wynik", Integer.toString(prediction));

        }
        catch (Exception ex)
        {
            Log.e("blad","Nie zaladowano modelu");
            Log.e("blad",ex.getMessage());
        }
    }

    private static MappedByteBuffer loadModelFile(AssetManager assets, String modelFilename)
            throws IOException {
        AssetFileDescriptor fileDescriptor = assets.openFd(modelFilename);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private float[] prepareData()
    {
        float[] data = new float[6];

        for(int i=0; i<3; i++)
    {
        data[i] = accelometerData[i];
    }

        for(int j=3; j<6; j++)
        {
            data[j] = gyroscopeData[j-3];
        }

        return data;
    }
}
