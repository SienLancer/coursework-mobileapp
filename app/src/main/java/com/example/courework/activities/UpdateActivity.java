package com.example.courework.activities;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.courework.MyDatabaseHelper;
import com.example.courework.R;
import com.example.courework.models.Hike;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    EditText name_edt_up, location_edt_up, length_edt_up, des_edt_up;
    TextView doh_control_up;
    RadioGroup rg_up;
    RadioButton yes_rb_up, no_rb_up;
    Spinner level_spinner_up;
    Button update_btn_in, back_btn;
    String id, name, location, doh, des, level, parking;
    int length;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        name_edt_up = findViewById(R.id.name_edt_up);
        location_edt_up = findViewById(R.id.location_edt_up);
        doh_control_up = findViewById(R.id.doh_control_up);
        rg_up = findViewById(R.id.rg_up);
        yes_rb_up = findViewById(R.id.yes_rb_up);
        no_rb_up = findViewById(R.id.no_rb_up);
        level_spinner_up = findViewById(R.id.level_spinner_up);
        length_edt_up = findViewById(R.id.length_edt_up);
        des_edt_up = findViewById(R.id.des_edt_up);
        update_btn_in = findViewById(R.id.update_btn_in);
        back_btn = findViewById(R.id.back_btn);
        getAndSetIntentData();
//        ActionBar ab= getSupportActionBar();
//        if (ab != null) {
//            ab.setTitle(name);
//        }
        back_btn.setOnClickListener(view -> finish());
        update_btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                name = name_edt_up.getText().toString().trim();
                location = location_edt_up.getText().toString().trim();
                doh = doh_control_up.getText().toString().trim();
                length = Integer.parseInt(length_edt_up.getText().toString().trim());
                level = level_spinner_up.getSelectedItem().toString().trim();
                des = des_edt_up.getText().toString().trim();

                if (name.matches("")) {
                    displayFillAll();
                } else if (location.matches("")) {
                    displayFillAll();
                } else if (doh.matches("Click here to select the date of the hike")) {
                    displayFillAll();
                } else if (length==0) {
                    displayFillAll();
                } else if (level.matches("None")) {
                    displayFillAll();
                } else if (rg_up.getCheckedRadioButtonId()== -1) {
                    displayFillAll();
                } else {
                    int id_btn= rg_up.getCheckedRadioButtonId();
                    RadioButton rb = findViewById(id_btn);
                    parking = rb.getText().toString();

                    myDB.updateDataHike(new Hike(id, name, location, doh, parking, length, level, des));
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        doh_control_up.setOnClickListener(view -> {
            Calendar kal = Calendar.getInstance();
            int year = kal.get(Calendar.YEAR);
            int month = kal.get(Calendar.MONTH);
            int day = kal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =new DatePickerDialog(UpdateActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                    dateSetListener, year, month, day);
            dialog.show();
        });

        dateSetListener = (datePicker, year, month, day) -> {
            month = month +1;
            Log.d(TAG, "onDateSet: dd/mm/yyyy " + day + "/" + month + "/" + year);
            String date = day + "/" + month + "/" + year;
            doh_control_up.setText(date);

        };


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
            name_edt_up.setText(name);
            location_edt_up.setText(location);
            doh_control_up.setText(doh);
            length_edt_up.setText(length+"");
            des_edt_up.setText(des);
            if (parking.matches("Yes")){
                yes_rb_up.setChecked(true);
            }else if (parking.matches("No")){
                no_rb_up.setChecked(true);
            }
//
            if (level.matches("High")){
                level_spinner_up.setSelection(1);
            } else if (level.matches("Medium")) {
                level_spinner_up.setSelection(2);
            } else if (level.matches("Low")) {
                level_spinner_up.setSelection(3);
            }
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayFillAll(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(
                        "You need to fill all required fields or fill in the correct email!"
                )
                .setNeutralButton("Close", (dialogInterface, i) -> {

                })
                .show();
    }


}