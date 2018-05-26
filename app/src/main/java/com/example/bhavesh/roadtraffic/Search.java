package com.example.bhavesh.roadtraffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Bhavesh on 11-02-2018.
 */

public class Search extends FragmentActivity implements View.OnClickListener {

    EditText locationSource;
    EditText locationDest;
    TextView distance;
    Button searchButton,timeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchButton = (Button)findViewById(R.id.search_button);
        timeSearch = (Button)findViewById(R.id.search_button_time);
        distance = (TextView)findViewById(R.id.textDistance);
        locationSource = (EditText) findViewById(R.id.editSource);
        locationDest = (EditText) findViewById(R.id.editDest);
        searchButton.setOnClickListener(this);
        timeSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                String source = locationSource.getText().toString();
                String destination = locationDest.getText().toString();
                if (source != null && !source.equals("") && destination != null && !destination.equals("")) {
                    Intent i = new Intent(this, Display.class);
                    i.putExtra("source", source);
                    i.putExtra("destination", destination);
                    startActivity(i);
                }
                else {
                    Toast.makeText(this,"Enter the location",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.search_button_time:
                String source_time = locationSource.getText().toString();
                String destination_time = locationDest.getText().toString();
                if (source_time != null && !source_time.equals("") && destination_time != null && !destination_time.equals("")) {
                    Intent i = new Intent(this, Display_time.class);
                    i.putExtra("source", source_time);
                    i.putExtra("destination", destination_time);
                    startActivity(i);
                }
                else {
                    Toast.makeText(this,"Enter the location",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
