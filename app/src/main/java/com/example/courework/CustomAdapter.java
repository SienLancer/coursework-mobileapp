package com.example.courework;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courework.fragment.AddFragment;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private Context context;
    Activity activity;
    ArrayList<Hiker> hikers;
    ArrayList<Hiker> hikersSearch;

    public CustomAdapter(Context context, ArrayList<Hiker> hikers) {
        this.context = context;
        this.hikers = hikers;
        this.hikersSearch = hikers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Hiker hiker =hikers.get(position);
        holder.nameR_txt.setText("Name: "+hiker.getName());
        holder.locationR_txt.setText("Location: "+hiker.getLocation());
        holder.dohR_txt.setText("Date of the hike: "+hiker.getDoh());
        holder.mainLayout.setOnClickListener(view -> displayNextAlert(hiker.getName(), hiker.getLocation(), hiker.getDoh(),
                hiker.getParking(), hiker.getLength(), hiker.getLevel(), hiker.getDescription()));

        holder.delete_btn_one.setOnClickListener(view -> {
            confirmDialogForOne(hiker.getId(), hiker.getName());

        });
        holder.update_btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                Hiker hiker =hikers.get(position);
                intent.putExtra("id", hiker.getId());
                intent.putExtra("name", hiker.getName());
                intent.putExtra("location", hiker.getLocation());
                intent.putExtra("doh", hiker.getDoh());
                intent.putExtra("parking", hiker.getParking());
                intent.putExtra("length", hiker.getLength());
                intent.putExtra("level", hiker.getLevel());
                intent.putExtra("des", hiker.getDescription());

                context.startActivity(intent);


            }
        });
    }




    @Override
    public int getItemCount() {
        return hikers.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameR_txt, locationR_txt, dohR_txt, parking_txt, length_txt, level_txt, des_txt;
        Button delete_btn_one, update_btn_out;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameR_txt = itemView.findViewById(R.id.name_row_txt);
            locationR_txt = itemView.findViewById(R.id.location_row_txt);
            dohR_txt = itemView.findViewById(R.id.doh_row_txt);
            delete_btn_one = itemView.findViewById(R.id.delete_btn_one);
            update_btn_out = itemView.findViewById(R.id.update_btn_out);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public void displayNextAlert(String name, String location, String doh, String parking, int length, String level, String des){
        new AlertDialog.Builder(context)
                .setTitle("Details Entered")
                .setMessage(
                        "\n" +
                                "Name: "+name + "\n" +
                                "Location: "+location + "\n" +
                                "Date of the hike: "+doh + "\n" +
                                "Parking available: "+parking + "\n" +
                                "Length: "+length +"Km"+ "\n" +
                                "Difficulty level: "+level + "\n" +
                                "Description: "+des
                )
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    void confirmDialogForOne(String id, String name){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Delete "+name+" ?");
        builder.setMessage("Are you sure you want delete "+name+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                myDB.deleteOneRow(id);
                Intent intent = new Intent(context, MainActivity.class);
                ((Activity)context).startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String searchText = charSequence.toString();
                if (searchText.isEmpty()){
                    hikers = hikersSearch;
                }else {
                    ArrayList<Hiker> hl = new ArrayList<>();
                    for (Hiker hiker : hikersSearch){
                        if (hiker.getName().toLowerCase().contains(searchText.toLowerCase())){
                            hl.add(hiker);
                        }else {
                            ArrayList<Hiker> emptyList = new ArrayList<>();
                            hikers = emptyList;
                        }
                    }
                    hikers = hl;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = hikers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hikers = (ArrayList<Hiker>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
