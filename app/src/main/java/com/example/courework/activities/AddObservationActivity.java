package com.example.courework.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courework.MyDatabaseHelper;
import com.example.courework.R;
import com.example.courework.models.Observation;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.IOException;
import java.util.Calendar;


public class AddObservationActivity extends AppCompatActivity {

    MyDatabaseHelper myDB;
    TextView too_control;
    EditText name_ob_edt, comment_ob_edt;
    ImageButton camera_imgBtn;
    ImageView imgView;
    Uri imgFilePath;
    Bitmap imgToStore;
    Button save_ob_btn, back_add_ob_btn;
    String id, name, too, comment, hiker_id, id_p;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_observation);

        too_control = findViewById(R.id.too_control);
        name_ob_edt = findViewById(R.id.name_ob_edt);
        comment_ob_edt = findViewById(R.id.comment_ob_edt);
        save_ob_btn = findViewById(R.id.save_ob_btn);
        back_add_ob_btn = findViewById(R.id.back_add_ob_btn);
        imgView = findViewById(R.id.imgView);
        camera_imgBtn = findViewById(R.id.camera_imgBtn);
        myDB = new MyDatabaseHelper(this);
        getAndSetIntentData();
        too_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar kal = Calendar.getInstance();
                int year = kal.get(Calendar.YEAR);
                int month = kal.get(Calendar.MONTH);
                int day = kal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(AddObservationActivity.this, android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener, year, month, day);
                dialog.show();
            }
        });
        dateSetListener = (datePicker, year, month, day) -> {
            month = month +1;
            Log.d(TAG, "onDateSet: dd/mm/yyyy " + day + "/" + month + "/" + year);
            String date = day + "/" + month + "/" + year;
            too_control.setText(date);

        };

        camera_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddObservationActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(480, 480)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        save_ob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = name_ob_edt.getText().toString().trim();
                too = too_control.getText().toString().trim();
                comment = comment_ob_edt.getText().toString().trim();
                hiker_id = id_p;


                if (name.matches("")) {
                    displayFillAll();
                } else if (too.matches("Click here to select the time of observation")) {
                    displayFillAll();
                }else if (imgView.getDrawable() == null && imgToStore == null) {
                    displayFillAll();
                } else {

                    myDB.addObservation(new Observation(id, name, too, comment, imgToStore, hiker_id ));
                    Intent intent = new Intent(AddObservationActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        back_add_ob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            imgFilePath = data.getData();
            imgToStore= MediaStore.Images.Media.getBitmap(getContentResolver(), imgFilePath);
            imgView.setImageBitmap(imgToStore);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }


    public void displayFillAll(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(
                        "You need to fill all required fields!"
                )
                .setNeutralButton("Close", (dialogInterface, i) -> {

                })
                .show();
    }


    void getAndSetIntentData() {
        if (getIntent().hasExtra("id")) {
            // Geting Data from Intent
            id_p = getIntent().getStringExtra("id");
        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}