package com.example.courework.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.courework.CustomAdapter;
import com.example.courework.MyDatabaseHelper;
import com.example.courework.ObservationAdapter;
import com.example.courework.R;
import com.example.courework.models.Observation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ObservationActivity extends AppCompatActivity {
    FloatingActionButton add_fab;
    MyDatabaseHelper myDB;
    RecyclerView ob_rv;
    ObservationAdapter observationAdapter;
    ArrayList<Observation> observations;
    String o_id, o_name, too, comment, hiker_id;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);

        add_fab = findViewById(R.id.add_fab);
        ob_rv = findViewById(R.id.ob_rv);



        myDB = new MyDatabaseHelper(this);
        observations = new ArrayList<>();


        storeDataInArrays();
        getAndSetIntentData();
        ArrayList<Observation> ob = new ArrayList<>();
        for (Observation observation : observations){
            if (observation.getHiker_id().matches(id)){
                ob.add(observation);
            }else {
                ArrayList<Observation> emptyList = new ArrayList<>();
                observations = emptyList;
            }
        }
        observations = ob;

        observationAdapter = new ObservationAdapter(this, observations);
        //getAndSetIntentData();
        ob_rv.setAdapter(observationAdapter);
        ob_rv.setLayoutManager(new LinearLayoutManager(this));

        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObservationActivity.this, AddObservationActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });




    }


    void getAndSetIntentData() {
        if (getIntent().hasExtra("id")) {

            // Geting Data from Intent
            id = getIntent().getStringExtra("id");


        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }



    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllDataOb();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String too = cursor.getString(2);
                String comment = cursor.getString(3);
                String hiker_id = cursor.getString(4);
                Observation observation = new Observation(id, name, too, comment, hiker_id);
                observations.add(observation);
            }
        }
    }
}