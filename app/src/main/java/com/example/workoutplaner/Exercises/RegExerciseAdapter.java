package com.example.workoutplaner.Exercises;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplaner.R;

import java.util.ArrayList;

public class RegExerciseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    ArrayList<ExerciseInput> exerciseInputs;

    public RegExerciseAdapter(Context cont){
        this.context = cont;
    }

    public void setItems(ArrayList<ExerciseInput> ex){
        exerciseInputs = ex;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.regular_item, parent, false);
        return new RegularExerciseVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RegularExerciseVH vh = (RegularExerciseVH) holder;
        if (exerciseInputs.get(position) == null){
            exerciseInputs.set(position, new ExerciseInput(0, 0));
        }

        ExerciseInput regExercise = exerciseInputs.get(position);

        vh.weight.setText(Double.toString(regExercise.getWeight()));
        vh.reps.setText(Integer.toString(regExercise.getReps()));

        vh.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vh.weight.getText().toString().equals("")){
                    return;
                }
                regExercise.setWeight(Double.parseDouble(vh.weight.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        vh.reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (vh.reps.getText().toString().equals("")){
                    return;
                }
                regExercise.setReps(Integer.parseInt(vh.reps.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return exerciseInputs.size();
    }
}
