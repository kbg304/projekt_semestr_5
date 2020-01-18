package com.example.projekt_semestr_5.ui.home;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.projekt_semestr_5.R;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    public Button button;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = v.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
      //  ImageView img = (ImageView) v.findViewById(R.id.imageActivity);
      //  img.setImageResource(R.drawable.walk);
      //  button = (Button) v.findViewById(R.id.begin_activ);

      //  button = (Button) inflater.inflate(R.layout.fragment_home, container, false).findViewById(R.id.begin_activ);
       // button.setOnClickListener(this);

       // String menu = getArguments().getString("Menu");

       // button.setText(menu);




       //    button.setOnClickListener(new View.OnClickListener() {
        //       @Override
        //        public void onClick(View v) {

                //   Toast.makeText(getActivity(),
                //          "Kamil jest słaby",
                //          Toast.LENGTH_SHORT).show();
          //      }
         //  });



        return v;
    }

}