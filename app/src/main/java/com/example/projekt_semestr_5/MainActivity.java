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
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;



public class MainActivity extends AppCompatActivity  implements SensorEventListener {


    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope;
    private float[] accelometerData, gyroscopeData, preparedData;

    private Button beginActivButton;
    private Button endActivButton;
    private ImageButton achivButton;
    private ImageButton statsButton;
    private TextView textActivity;
    private String nameActivity;
    public String zmienna ="Wszystkie twoje aktywności";
    private String modelFilename = "activity_ml_model.tflite";
    private String[] labels = {"siedzenie", "stanie", "chodzenie", "bieganie"};
    private int activityPrediction;
    private int previousActivity=-1;

    private long startTime;
    private long[] activityTime = new long[4];

    MachineLearning machineLearning;
    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSensors();
        mainmenu();
        fileManager = new FileManager(getApplicationContext());
        openMLModel();
        

        beginActivButton = findViewById(R.id.begin_activ);
        endActivButton = findViewById(R.id.end_activ);
        achivButton = findViewById(R.id.ButtonAchivments);
        statsButton = findViewById(R.id.ButtonStats);
        beginActivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  ImageView img = findViewById(R.id.imageActivity);
              //  img.setImageResource(R.drawable.chair);

              //  nameActivity ="Tu ma byc juz zapisana aktywnosc";
                textActivity = findViewById(R.id.textActivity);
              //  textActivity.setText(nameActivity);

                beginActivButton.setVisibility(View.INVISIBLE);
                endActivButton.setVisibility(View.VISIBLE);

                toastStart();
                startSensorMeasurement();
                startTime = System.currentTimeMillis();
               // if(button.getVisibility()==View.INVISIBLE)
            }
        });
        endActivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = findViewById(R.id.imageActivity);
                img.setImageResource(R.drawable.noactiv);

                nameActivity ="BRAK";
                textActivity = findViewById(R.id.textActivity);
                textActivity.setText(nameActivity);

                beginActivButton.setVisibility(View.VISIBLE);
                endActivButton.setVisibility(View.INVISIBLE);

                toastStop();
                stopSensorMeasurement();

                activityTime[previousActivity] += (System.currentTimeMillis() - startTime);
                saveTime();
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
    }


    public void toastStart(){
        Toast toast = Toast.makeText(getApplicationContext(), "ROZPOCZĘTO DZIAŁANIE PROGRAMU ", Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
        toastTV.setTextColor(Color.RED);
        toast.show();
    }

    public void toastStop(){
        Toast toast = Toast.makeText(getApplicationContext(), "ZAKOŃCZONO DZIAŁANIE PROGRAMU", Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        TextView toastTV = (TextView) toastLayout.getChildAt(0);
        toastTV.setTextSize(20);
        toastTV.setTextColor(Color.RED);
        toastTV.setTextAlignment(toastTV.TEXT_ALIGNMENT_CENTER);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        // your code.
        stopSensorMeasurement();
        finish();
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
       // machineLearning.closeInterpreter();
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

        preparedData = prepareData();
        getActivityPrediction();
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

    private void getActivityPrediction()
    {
        activityPrediction = machineLearning.getPrediction(preparedData);

        if(previousActivity == -1)
        {
            previousActivity = activityPrediction;
        }
        if(previousActivity != activityPrediction)
        {
            activityTime[previousActivity] += (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            previousActivity = activityPrediction;
        }

        if(activityPrediction>=0 && activityPrediction <4)
        {
            textActivity.setText(labels[activityPrediction]);

            ImageView img = findViewById(R.id.imageActivity);
            //img.setImageResource(R.drawable.man);

            Log.d("aktywnosc", labels[activityPrediction]);

            switch (activityPrediction) {
                case 0:
                    img.setImageResource(R.drawable.chair);
                    break;
                case 1:
                    img.setImageResource(R.drawable.man);
                    break;
                case 2:
                    img.setImageResource(R.drawable.walk);
                    break;
                case 3:
                    img.setImageResource(R.drawable.run);
                    break;
            }
        }
    }

    private void saveTime()
    {
        double sitting, standing, walking, running;

        if(fileManager.open("sitting") == "")
        {
            sitting = activityTime[0] * 2.7e-7;
            fileManager.save("sitting", Double.toString(sitting));
        }
        else
        {
            sitting = Double.parseDouble(fileManager.open("sitting"));
            sitting += (activityTime[0] * 2.7e-7);
            fileManager.save("sitting", Double.toString(sitting));
        }

        if(fileManager.open("standing") == "")
        {
            standing = activityTime[1] * 2.7e-7;
            fileManager.save("standing", Double.toString(standing));
        }
        else
        {
            standing = Double.parseDouble(fileManager.open("standing"));
            standing += (activityTime[1] * 2.7e-7);
            fileManager.save("standing", Double.toString(standing));
        }

        if(fileManager.open("walking") == "")
        {
            walking = activityTime[2] * 2.7e-7;
            fileManager.save("walking", Double.toString(walking));
        }
        else
        {
            walking = Double.parseDouble(fileManager.open("walking"));
            walking += (activityTime[2] * 2.7e-7);
            fileManager.save("walking", Double.toString(walking));
        }

        if(fileManager.open("running") == "")
        {
            running = activityTime[3] * 2.7e-7;
            fileManager.save("running", Double.toString(running));
        }
        else
        {
            running = Double.parseDouble(fileManager.open("running"));
            running += (activityTime[3] * 2.7e-7);
            fileManager.save("running", Double.toString(running));
        }

    }
}
