package com.example.workoutplaner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TimerExerciseFragment extends Fragment {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffset;

    private DatabaseReference mDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timer_exercise, container, false);

        chronometer = rootView.findViewById(R.id.increasingChronometer);
        mDatabase = FirebaseDatabase.getInstance().getReference("workout_time_elapsed");
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
        //WorkoutTimeElapsed w = new WorkoutTimeElapsed(FirebaseAuth.getInstance().getCurrentUser().getUid(), chronometer.getBase());
        //mDatabase.setValue(w);


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
    /*public Task<Void> add(workout_time_elapsed w)
    {


        //return databaseReference.push().setValue(w);
    }*/


}