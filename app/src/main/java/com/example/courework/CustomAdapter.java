package com.example.courework;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import com.example.courework.activities.DetailActivity;
import com.example.courework.activities.MainActivity;
import com.example.courework.activities.ObservationActivity;
import com.example.courework.activities.UpdateActivity;
import com.example.courework.models.Hike;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {

    private Context context;
    ArrayList<Hike> hikes;
    ArrayList<Hike> hikersSearches;

    public CustomAdapter(Context context, ArrayList<Hike> hikes) {
        this.context = context;
        this.hikes = hikes;
        this.hikersSearches = hikes;
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
        Hike hike = hikes.get(position);
        holder.nameR_txt.setText(hike.getName());
        holder.locationR_txt.setText(hike.getLocation());
        holder.dohR_txt.setText(hike.getDoh());

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                Hike hike1 = hikes.get(position);
                intent.putExtra("id", hike1.getId());
                intent.putExtra("name", hike1.getName());
                intent.putExtra("location", hike1.getLocation());
                intent.putExtra("doh", hike1.getDoh());
                intent.putExtra("parking", hike1.getParking());
                intent.putExtra("length", hike1.getLength());
                intent.putExtra("level", hike1.getLevel());
                intent.putExtra("des", hike1.getDescription());

                context.startActivity(intent);
            }
        });

        holder.delete_btn_one.setOnClickListener(view -> {
            confirmDialogForOne(hike.getId(), hike.getName());

        });
        holder.update_btn_out.setOnClickListener(view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            Hike hike1 = hikes.get(position);
            intent.putExtra("id", hike1.getId());
            intent.putExtra("name", hike1.getName());
            intent.putExtra("location", hike1.getLocation());
            intent.putExtra("doh", hike1.getDoh());
            intent.putExtra("parking", hike1.getParking());
            intent.putExtra("length", hike1.getLength());
            intent.putExtra("level", hike1.getLevel());
            intent.putExtra("des", hike1.getDescription());

            context.startActivity(intent);


        });

        holder.more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ObservationActivity.class);
                Hike hike = hikes.get(position);
                intent.putExtra("id", hike.getId());
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return hikes.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameR_txt, locationR_txt, dohR_txt;
        Button delete_btn_one, update_btn_out, more_btn;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameR_txt = itemView.findViewById(R.id.name_row_txt);
            locationR_txt = itemView.findViewById(R.id.location_row_txt);
            dohR_txt = itemView.findViewById(R.id.doh_row_txt);
            delete_btn_one = itemView.findViewById(R.id.delete_btn_row);
            update_btn_out = itemView.findViewById(R.id.update_btn_out);
            more_btn = itemView.findViewById(R.id.more_btn);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }


    void confirmDialogForOne(String id, String name){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Delete "+name+" ?");
        builder.setMessage("Are you sure you want delete "+name+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                myDB.deleteOneRowHike(id);
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
                    hikes = hikersSearches;
                }else {
                    ArrayList<Hike> hl = new ArrayList<>();
                    for (Hike hike : hikersSearches){
                        if (hike.getName().toLowerCase().contains(searchText.toLowerCase())){
                            hl.add(hike);
                        }else {
                            ArrayList<Hike> emptyList = new ArrayList<>();
                            hikes = emptyList;
                        }
                    }
                    hikes = hl;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = hikes;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hikes = (ArrayList<Hike>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
