package com.example.workoutplaner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TimerExerciseFragment extends Fragment {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer_exercise, container, false);

        chronometer = rootView.findViewById(R.id.increasingChronometer);
        chronometer.setFormat("Time: %s");
        long timeElapsed = ((ExercisesActivity)getActivity()).TimeElapsed();
        chronometer.setBase(timeElapsed);
        chronometer.start();

        running = true;

        View pauseButton = rootView.findViewById(R.id.increasingButtonPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseChronometer(v);
            }
        });


        return rootView;
    }

    public void pauseChronometer(View v){
        if (running){
            chronometer.stop();
            running = false;
        }
    }

    public void resetChronometer(View v){
        chronometer.setBase(SystemClock.elapsedRealtime());
        if (running){
            chronometer.stop();
            running = false;
        }
        pauseOffset = 0;
    }
}