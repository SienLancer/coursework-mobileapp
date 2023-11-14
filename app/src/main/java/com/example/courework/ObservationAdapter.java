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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courework.activities.MainActivity;
import com.example.courework.activities.UpdateObservationActivity;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Observation observation =observations.get(position);
        holder.cmt_row_ob_txt.setText(observation.getComment());
        holder.name_ob_row_txt.setText(observation.getName());
        holder.too_ob_txt.setText(observation.getTimeOfOb());
        holder.imageView_ob.setImageBitmap(observation.getImage());

        holder.update_btn_ob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateObservationActivity.class);
                Observation observation =observations.get(position);
                intent.putExtra("id", observation.getId());
                intent.putExtra("name", observation.getName());
                intent.putExtra("too", observation.getTimeOfOb());
                intent.putExtra("comment", observation.getComment());
                intent.putExtra("image", observation.getImage());
                intent.putExtra("hiker_id", observation.getHiker_id());


                context.startActivity(intent);


            }
        });

        holder.delete_btn_ob_row.setOnClickListener(view -> {
            confirmDialogForOne(observation.getId(), observation.getName());

        });

    }

    void confirmDialogForOne(String id, String name){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Delete "+name+" ?");
        builder.setMessage("Are you sure you want delete "+name+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(context);
                myDB.deleteOneRowOb(id);
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
    public int getItemCount() {
        return observations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name_ob_row_txt, too_ob_txt, cmt_row_ob_txt;
        ImageView imageView_ob;
        Button delete_btn_ob_row, update_btn_ob;
        LinearLayout mainLayout_ob;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_ob_row_txt = itemView.findViewById(R.id.name_ob_row_txt);
            too_ob_txt = itemView.findViewById(R.id.too_row_txt);
            update_btn_ob = itemView.findViewById(R.id.update_btn_ob);
            delete_btn_ob_row = itemView.findViewById(R.id.delete_btn_ob_row);
            cmt_row_ob_txt = itemView.findViewById(R.id.comment_row_ob_txt);
            imageView_ob = itemView.findViewById(R.id.imageView_ob);
            mainLayout_ob = itemView.findViewById(R.id.mainLayout_ob);
        }
    }



}
