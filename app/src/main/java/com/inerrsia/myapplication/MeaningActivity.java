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

        TextView meaning = (TextView) findViewById(R.id.meainng_meaning);
        TextView syn = (TextView) findViewById(R.id.meaning_syn);
        TextView word = (TextView) findViewById(R.id.meaning_word);
        TextView defindex = (TextView) findViewById(R.id.defindex);
        word.setText(intent.getStringExtra(EXTRA_MESSAGE));

    }
}
