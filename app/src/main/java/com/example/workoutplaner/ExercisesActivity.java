package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class ExercisesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        for (int i = 0; i < 5; i++){
            Bundle bundle = new Bundle();
            Exercise exercise = new Exercise("exerciseName " + i);
            bundle.putSerializable("exercise", exercise);

            RegularExerciseFragment reg1 = new RegularExerciseFragment();
            reg1.setArguments(bundle);
            vpAdapter.addFragment(reg1, "Bench");
        }

//        RegularExerciseFragment reg1 = new RegularExerciseFragment();
//        vpAdapter.addFragment(reg1, "Bench");
//        vpAdapter.addFragment(new TimerExerciseFragment(), "Plank");
//        vpAdapter.addFragment(new RegularExerciseFragment(), "Squat");
//        vpAdapter.addFragment(new RegularExerciseFragment(), "OHP");
//        vpAdapter.addFragment(new TimerExerciseFragment(), "Running");
//        vpAdapter.addFragment(new TimerExerciseFragment(), "Timer");
        viewPager.setAdapter(vpAdapter);
    }
}