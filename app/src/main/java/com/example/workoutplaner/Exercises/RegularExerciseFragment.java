package com.example.workoutplaner.Exercises;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.workoutplaner.R;

import java.util.ArrayList;

public class RegularExerciseFragment extends Fragment {

    private Exercise exercise;
    private ArrayList<ExerciseInput> exerciseInputs;

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

        for (int i = 0; i < exercise.getSets(); i++){
            exerciseInputs.add(null);
        }
        regAdapter.setItems(exerciseInputs);

        return rootView;
    }
}