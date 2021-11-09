package com.example.workoutplaner.Exercises;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.workoutplaner.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class RegularExerciseFragment extends Fragment {

    private Exercise exercise;
    private ArrayList<ExerciseInput> exerciseInputs;
    AnimationDrawable avd;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RegExerciseAdapter regAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_regular_exercise, container, false);
        swipeRefreshLayout = rootView.findViewById(R.id.swip);
        recyclerView = rootView.findViewById(R.id.rv_regular);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        regAdapter = new RegExerciseAdapter(getActivity());
        recyclerView.setAdapter(regAdapter);
        exercise = (Exercise) getArguments().getSerializable("exercise");
        exerciseInputs = (ArrayList<ExerciseInput>)getArguments().getSerializable("regularExercisesList");
        ImageView done = rootView.findViewById(R.id.imageView3);
        try {
            avd = (AnimationDrawable) done.getDrawable();
            avd.start();
            Log.e("OKAY", "OKAY!!");
        }
        catch(Exception e) {
            Log.e("ERROR", e.getMessage());
        }

        for (int i = 0; i < exercise.getSets(); i++){
            exerciseInputs.add(null);
        }
        regAdapter.setItems(exerciseInputs);

        return rootView;
    }
}