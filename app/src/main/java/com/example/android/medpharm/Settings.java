package com.example.android.medpharm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {

    EditText distance;
    Button mapSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        distance = (EditText) findViewById(R.id.distance);
        mapSearch = (Button) findViewById(R.id.mapSearch);

        mapSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String distt = distance.getText().toString();
                int dist = Integer.parseInt(distt);
                Intent mapIntent = new Intent(getApplicationContext(),MapsActivity.class);
                mapIntent.putExtra("dist",dist);
                mapIntent.putExtra("flag",1);
                startActivity(mapIntent);
            }
        });
    }
}
