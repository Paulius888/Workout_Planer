package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //private Button open_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // paprastas button, kuris yra main activity
        /*open_nav = (Button) findViewById(R.id.button);    *mygtukas, kol kas istrintas, tai neveiks be button id
        open_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNavigation();       // atidaro navigation

            }});*/
    }

    // navigation atidarymo metodas
    /*public void openNavigation() {
        Intent intent = new Intent(this, Navigation.class);
        startActivity(intent);
    }*/

}