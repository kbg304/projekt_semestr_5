package com.example.projekt_semestr_5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;


public class AchivActivity extends AppCompatActivity {
    private FrameLayout frameLayout;

    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);
        fileManager = new FileManager(getApplicationContext());

        checkForAchievements();

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }

    private void checkForAchievements()
    {
        //pierwsze kroki
        if(fileManager.open("pierwsze") == "1")
        {
            frameLayout = (FrameLayout) findViewById(R.id.pierwszeKroki);
            frameLayout.setAlpha(1);
        }
        else
        {
            double tmp = Double.parseDouble(fileManager.open("walking"));
            double tmp2 = Double.parseDouble(fileManager.open("running"));
            if(tmp >= 0.17 || tmp2 >= 0.17)
            {
                fileManager.save("pierwsze", "1");
                frameLayout = (FrameLayout) findViewById(R.id.pierwszeKroki);
                frameLayout.setAlpha(1);
            }
        }

        //nolife
        if(fileManager.open("nolife") == "1")
        {
            frameLayout = (FrameLayout) findViewById(R.id.nolife);
            frameLayout.setAlpha(1);
        }
        else
        {
            double tmp = Double.parseDouble(fileManager.open("sitting"));
            if(tmp >= 12)
            {
                fileManager.save("nolife", "1");
                frameLayout = (FrameLayout) findViewById(R.id.nolife);
                frameLayout.setAlpha(1);
            }
        }

        //piwniczak
        if(fileManager.open("piwniczak") == "1")
        {
            frameLayout = (FrameLayout) findViewById(R.id.piwniczak);
            frameLayout.setAlpha(1);
        }
        else
        {
            double tmp = Double.parseDouble(fileManager.open("sitting"));
            if(tmp >= 168)
            {
                fileManager.save("piwniczak", "1");
                frameLayout = (FrameLayout) findViewById(R.id.piwniczak);
                frameLayout.setAlpha(1);
            }
        }

        //biegacz
        if(fileManager.open("biegacz") == "1")
        {
            frameLayout = (FrameLayout) findViewById(R.id.biegacz);
            frameLayout.setAlpha(1);
        }
        else
        {
            double tmp = Double.parseDouble(fileManager.open("running"));
            if(tmp >= 3)
            {
                fileManager.save("biegacz", "1");
                frameLayout = (FrameLayout) findViewById(R.id.biegacz);
                frameLayout.setAlpha(1);
            }
        }

        //maratończyk
        if(fileManager.open("maratonczyk") == "1")
        {
            frameLayout = (FrameLayout) findViewById(R.id.Maratończyk);
            frameLayout.setAlpha(1);
        }
        else
        {
            double tmp = Double.parseDouble(fileManager.open("running"));
            if(tmp >= 10)
            {
                fileManager.save("maratonczyk", "1");
                frameLayout = (FrameLayout) findViewById(R.id.Maratończyk);
                frameLayout.setAlpha(1);
            }
        }
    }

}
