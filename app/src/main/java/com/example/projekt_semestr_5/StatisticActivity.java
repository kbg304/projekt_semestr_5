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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.statistics);
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

}
