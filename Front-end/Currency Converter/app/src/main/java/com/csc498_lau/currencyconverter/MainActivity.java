package com.csc498_lau.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToCalculator (View view) {

        Intent obj = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(obj);
    }
}