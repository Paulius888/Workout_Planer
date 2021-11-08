package com.example.workoutplaner.Exercises;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplaner.R;

public class RegularExerciseVH extends RecyclerView.ViewHolder {

    public EditText weight, reps;

    public RegularExerciseVH(@NonNull View itemView) {
        super(itemView);
        weight = itemView.findViewById(R.id.weight);
        reps = itemView.findViewById(R.id.reps);
    }
}
