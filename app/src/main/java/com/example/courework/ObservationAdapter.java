package com.example.courework;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courework.models.Hiker;
import com.example.courework.models.Observation;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.MyViewHolder> {

    private Context context;
    String id;
    ArrayList<Observation> observations;

    public ObservationAdapter(Context context, ArrayList<Observation> observations) {
        this.context = context;
        this.observations = observations;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewo = inflater.inflate(R.layout.ob_row, parent, false);
        return new MyViewHolder(viewo);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Observation observation =observations.get(position);
        holder.id_row_ob_txt.setText("ID: "+observation.getHiker_id());
        holder.name_ob_row_txt.setText("Name: "+observation.getName());
        holder.too_ob_txt.setText("Time of observation: "+observation.getTimeOfOb());
        holder.imageView_ob.setImageBitmap(observation.getImage());


    }



    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_ob_row_txt, too_ob_txt, id_row_ob_txt;
        ImageView imageView_ob;
        Button delete_btn_ob_row, update_btn_ob;
        LinearLayout mainLayout_ob;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_ob_row_txt = itemView.findViewById(R.id.name_ob_row_txt);
            too_ob_txt = itemView.findViewById(R.id.too_row_txt);
            update_btn_ob = itemView.findViewById(R.id.update_btn_ob);
            delete_btn_ob_row = itemView.findViewById(R.id.delete_btn_ob_row);
            id_row_ob_txt = itemView.findViewById(R.id.id_row_ob_txt);
            imageView_ob = itemView.findViewById(R.id.imageView_ob);
            mainLayout_ob = itemView.findViewById(R.id.mainLayout_ob);
        }
    }



}
