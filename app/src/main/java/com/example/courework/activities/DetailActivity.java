package com.example.courework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courework.R;

public class DetailActivity extends AppCompatActivity {
    String id, name, location, doh, des, level, parking;
    int length;
    Button back_btn;
    TextView name_content, location_content, doh_content, park_content, length_content, level_content, des_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        name_content = findViewById(R.id.name_d_content);
        location_content = findViewById(R.id.location_d_content);
        doh_content = findViewById(R.id.doh_d_content);
        park_content = findViewById(R.id.park_d_content);
        length_content = findViewById(R.id.length_d_content);
        level_content = findViewById(R.id.level_d_content);
        des_content = findViewById(R.id.des_d_content);
        back_btn = findViewById(R.id.back_ob_btn);

        getAndSetIntentData();

        back_btn.setOnClickListener(view -> finish());



    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("location")
                && getIntent().hasExtra("doh") && getIntent().hasExtra("parking") && getIntent().hasExtra("length") ) {
            // Geting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            location = getIntent().getStringExtra("location");
            doh = getIntent().getStringExtra("doh");
            parking = getIntent().getStringExtra("parking");
            length = getIntent().getIntExtra("length", 0);
            level = getIntent().getStringExtra("level");
            des = getIntent().getStringExtra("des");

            // Seting intent data
            name_content.setText(name);
            location_content.setText(location);
            des_content.setText(doh);
            length_content.setText(length+"Km");
            des_content.setText(des);
            if (parking.matches("Yes")){
                park_content.setText("Yes");
            }else if (parking.matches("No")){
                park_content.setText("No");
            }
//
            if (level.matches("High")){
                level_content.setText("High");
            } else if (level.matches("Medium")) {
                level_content.setText("Medium");
            } else if (level.matches("Low")) {
                level_content.setText("Low");
            }
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}