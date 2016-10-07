package com.inerrsia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MeaningActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "word";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);
        Intent intent = getIntent();


    }
}
