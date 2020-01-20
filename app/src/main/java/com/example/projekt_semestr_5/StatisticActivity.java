package com.example.projekt_semestr_5;

import android.content.Intent;;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StatisticActivity extends AppCompatActivity {
    FileManager fileManager;
    private double sittingdata, standingdata, walkingdata, runningdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        fileManager = new FileManager(getApplicationContext());
        setDataTime();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String zmienna = getIntent().getStringExtra("Zmienna");
            final TextView mTextView = (TextView) findViewById(R.id.textView);
            mTextView.setText(zmienna);

        }
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // if(button.getVisibility()==View.INVISIBLE)

            }
        });


    }
    public void setDataTime(){
       if(fileManager.open("sitting") == "") {TextView sittingText = findViewById(R.id.textSitting); sittingText.setText("0 godzin"); }
        else{
            sittingdata = Double.parseDouble(fileManager.open("sitting"));
            TextView sittingText = findViewById(R.id.textSitting);
            sittingText.setText(String.format("%.2f", sittingdata) + " godzin");}
    //    }
        if(fileManager.open("standing") == "") { TextView standingText = findViewById(R.id.textStanding); standingText.setText("0 godzin"); }
        else{
            standingdata = Double.parseDouble(fileManager.open("standing"));
            TextView standingText = findViewById(R.id.textStanding);
            standingText.setText(String.format("%.2f", standingdata)+" godzin");}

        if(fileManager.open("walking") == "") {TextView walkingText = findViewById(R.id.textWalking); walkingText.setText("0 godzin"); }
        else{
            walkingdata = Double.parseDouble(fileManager.open("walking"));
            TextView walkingText = findViewById(R.id.textWalking);
            walkingText.setText(String.format("%.2f", walkingdata) + " godzin");}

        if(fileManager.open("running") == "") {TextView runningData = findViewById(R.id.textRunning); runningData.setText("0 godzin"); }
        else{
            runningdata = Double.parseDouble(fileManager.open("running"));
            TextView runningText = findViewById(R.id.textRunning);
            runningText.setText(String.format("%.2f", runningdata) + " godzin");}
    }

}
