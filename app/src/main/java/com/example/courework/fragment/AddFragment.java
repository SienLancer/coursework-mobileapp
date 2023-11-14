package com.example.courework.fragment;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.courework.models.Hike;
import com.example.courework.activities.MainActivity;
import com.example.courework.MyDatabaseHelper;
import com.example.courework.R;

import java.util.ArrayList;
import java.util.Calendar;

public class AddFragment extends Fragment {

    private View mView;
    EditText name_edt, location_edt, length_edt, des_edt;
    TextView doh_control;
    Spinner level_spinner;
    RadioGroup rg;
    RadioButton yes_rb;
    RadioButton no_rb;
    Button save_btn;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String id, name, location, doh, des, level, parking;
    int length;
    MyDatabaseHelper myDB;
    ArrayList<Hike> hikes;
    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();

        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add, container, false);
        name_edt = mView.findViewById(R.id.name_edt);
        location_edt = mView.findViewById(R.id.location_edt);
        doh_control = mView.findViewById(R.id.doh_control);
        rg = mView.findViewById(R.id.rg);
        length_edt = mView.findViewById(R.id.length_edt);
        des_edt = mView.findViewById(R.id.des_edt);
        yes_rb = mView.findViewById(R.id.yes_rb);
        no_rb = mView.findViewById(R.id.no_rb);
        save_btn = mView.findViewById(R.id.save_btn);
        level_spinner = mView.findViewById(R.id.level_spinner);

        myDB = new MyDatabaseHelper(getActivity());
        hikes =new ArrayList<>();
        length_edt.setText("0");
        save_btn.setOnClickListener(view -> {
            name = name_edt.getText().toString().trim();
            location = location_edt.getText().toString().trim();
            doh = doh_control.getText().toString().trim();
            length = Integer.parseInt(length_edt.getText().toString().trim());
            level = level_spinner.getSelectedItem().toString().trim();
            des = des_edt.getText().toString().trim();



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
            } else if (rg.getCheckedRadioButtonId()== -1) {
                displayFillAll();
            } else {
                int id_btn = rg.getCheckedRadioButtonId();
                RadioButton rb = mView.findViewById(id_btn);
                parking = rb.getText().toString();

                myDB.addHike(new Hike(id, name, location, doh, parking, length, level, des));
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }



        });

        doh_control.setOnClickListener(view -> {
            Calendar kal = Calendar.getInstance();
            int year = kal.get(Calendar.YEAR);
            int month = kal.get(Calendar.MONTH);
            int day = kal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog =new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,
                    dateSetListener, year, month, day);
            dialog.show();
        });

        dateSetListener = (datePicker, year, month, day) -> {
            month = month +1;
            Log.d(TAG, "onDateSet: dd/mm/yyyy " + day + "/" + month + "/" + year);
            String date = day + "/" + month + "/" + year;
            doh_control.setText(date);

        };

        return mView;
    }

    public void displayFillAll(){
        new AlertDialog.Builder(getActivity())
                .setTitle("Error")
                .setMessage(
                        "You need to fill all required fields!"
                )
                .setNeutralButton("Close", (dialogInterface, i) -> {

                })
                .show();
    }




}