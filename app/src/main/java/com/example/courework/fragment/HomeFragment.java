package com.example.courework.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.courework.CustomAdapter;
import com.example.courework.models.Hike;
import com.example.courework.activities.MainActivity;
import com.example.courework.MyDatabaseHelper;
import com.example.courework.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private View mView;
    RecyclerView rv;
    FloatingActionButton delAll_fab;
    MyDatabaseHelper myDB;
    SearchView name_sv;

    ArrayList<Hike> hikes;
    CustomAdapter customAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        rv = mView.findViewById(R.id.rv);
        delAll_fab = mView.findViewById(R.id.deleteAll_fab);
        name_sv = mView.findViewById(R.id.name_sv);
        myDB = new MyDatabaseHelper(getActivity());
        hikes = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity(), hikes);
        rv.setAdapter(customAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        rv.addItemDecoration(itemDecoration);

        name_sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return false;
            }
        });

        delAll_fab.setOnClickListener(view -> confirmDialogForAll());

        return mView;
    }

    private void storeDataInArrays() {
        Cursor cursor = myDB.readAllDataHike();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(), "NO DATA", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String doh = cursor.getString(3);
                String parking = cursor.getString(4);
                int length = cursor.getInt(5);
                String level = cursor.getString(6);
                String des = cursor.getString(7);
                Hike hike = new Hike(id, name, location, doh, parking, length, level, des);
                hikes.add(hike);
            }
        }
    }

    void confirmDialogForAll(){
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete all");
        builder.setMessage("Do you want to delete all the information in the list?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(getActivity());
                myDB.deleteAllDataHike();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }



}