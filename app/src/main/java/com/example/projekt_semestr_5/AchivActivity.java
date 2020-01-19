package com.example.projekt_semestr_5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AchivActivity extends AppCompatActivity {
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);
        frameLayout = (FrameLayout) findViewById(R.id.pierwszeKroki);
        frameLayout.setAlpha(1);
        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // if(button.getVisibility()==View.INVISIBLE)

            }
        });

    }

}
