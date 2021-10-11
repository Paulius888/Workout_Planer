package com.example.workoutplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class ExercisesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_hamburger_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuHandler.HandleMenuClick(item, this);
        return super.onOptionsItemSelected(item);
    }

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