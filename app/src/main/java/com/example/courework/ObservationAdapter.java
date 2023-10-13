package com.example.courework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courework.models.Observation;

import java.util.ArrayList;

public class ObservationAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    ArrayList<Observation> observations;

    public ObservationAdapter(Context context, ArrayList<Observation> observations) {
        this.context = context;
        this.observations = observations;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ob_row, parent, false);
        return new CustomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_ob_row_txt, comment_ob_txt;
        Button delete_btn_ob_row, update_btn_ob;
        LinearLayout mainLayout_ob;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_ob_row_txt = itemView.findViewById(R.id.name_ob_row_txt);
            comment_ob_txt = itemView.findViewById(R.id.comment_row_txt);
            update_btn_ob = itemView.findViewById(R.id.update_btn_ob);
            delete_btn_ob_row = itemView.findViewById(R.id.delete_btn_ob_row);
            mainLayout_ob = itemView.findViewById(R.id.mainLayout_ob);
        }
    }
}
