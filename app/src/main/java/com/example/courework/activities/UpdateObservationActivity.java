package com.example.courework.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.util.ArrayList;

public class UpdateObservationActivity extends AppCompatActivity {
    MyDatabaseHelper myDB;
    TextView too_up_control;
    EditText name_ob_up_edt, comment_ob_up_edt;
    ImageButton camera_up_imgBtn;
    ImageView imgView_up;
    Uri imgFilePath;
    Bitmap imgToStore;
    Button save_ob_up_btn, back_up_ob_btn;
    String id, name, too, comment, hiker_id;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);
        too_up_control = findViewById(R.id.too_up_control);
        name_ob_up_edt = findViewById(R.id.name_ob_up_edt);
        comment_ob_up_edt = findViewById(R.id.comment_ob_up_edt);
        save_ob_up_btn = findViewById(R.id.save_ob_up_btn);
        back_up_ob_btn = findViewById(R.id.back_update_ob_btn);
        imgView_up = findViewById(R.id.imgView_up);
        camera_up_imgBtn = findViewById(R.id.camera_up_imgBtn);
        myDB = new MyDatabaseHelper(this);
        getAndSetIntentData();

        back_up_ob_btn.setOnClickListener(view -> finish());

        save_ob_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = name_ob_up_edt.getText().toString().trim();
                too = too_up_control.getText().toString().trim();
                comment = comment_ob_up_edt.getText().toString().trim();

                if (name.matches("")) {
                    displayFillAll();
                } else if (too.matches("Click here to select the time of observation")) {
                    displayFillAll();
                }else if (imgView_up.getDrawable() == null && imgToStore == null) {
                    displayFillAll();
                } else {

                    myDB.updateDataOb(new Observation(id, name, too, comment, imgToStore, hiker_id ));
                    Intent intent = new Intent(UpdateObservationActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

        camera_up_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UpdateObservationActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(300, 300)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("too")
                && getIntent().hasExtra("comment") && getIntent().hasExtra("image") && getIntent().hasExtra("hiker_id") ) {
            // Geting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            too = getIntent().getStringExtra("too");
            comment = getIntent().getStringExtra("comment");
            imgToStore = getIntent().getParcelableExtra("image");
            hiker_id = getIntent().getStringExtra("hiker_id");

            // Seting intent data
            name_ob_up_edt.setText(name);
            too_up_control.setText(too);
            comment_ob_up_edt.setText(comment);
            imgView_up.setImageBitmap(imgToStore);

        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            imgFilePath = data.getData();
            imgToStore= MediaStore.Images.Media.getBitmap(getContentResolver(), imgFilePath);
            imgView_up.setImageBitmap(imgToStore);
        } catch (IOException e) {
            throw new RuntimeException(e);
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