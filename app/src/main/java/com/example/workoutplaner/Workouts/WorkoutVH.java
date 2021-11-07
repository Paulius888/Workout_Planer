package com.example.workoutplaner.Workouts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplaner.R;

public class WorkoutVH extends RecyclerView.ViewHolder
{
    public TextView txt_name, txt_position, txt_option;
    public WorkoutVH(@NonNull View itemView)
    {
        super(itemView);
        txt_name = itemView.findViewById(R.id.txt_name);
        //txt_position = itemView.findViewById(R.id.txt_position);
        txt_option = itemView.findViewById(R.id.txt_option);
    }
}
