package com.example.workoutplaner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NavigationFragment extends Fragment {


    public NavigationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_navigation, container, false);



        return v;
    }
    public void onWorkoutClick(View view) {
        Intent intent = new Intent(getContext(), RVWorkout.class);
        this.startActivity(intent);
    }
    public void onLogoutClick(View view) {
        /*
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);*/
        Intent intent = new Intent(getContext(), LoginFragment.class);
        startActivity(intent);
    }
}