package com.example.workoutplaner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RegularExerciseFragment extends Fragment {

    private VPAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_regular_exercise, container, false);
        Exercise exercise = (Exercise) getArguments().getSerializable("exercise");
        TextView exercise1 = rootView.findViewById(R.id.repsSets1);
        addExerciseName(exercise1, exercise.getName());

        return rootView;
    }

    public void addExerciseName(TextView tv, String name){
        tv.setText(name);
    }
}