package com.example.workoutplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Map;

public class ExercisesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<ExerciseState> ids;

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
        ids = new ArrayList<>();
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

            ids.add(new ExerciseState("exerciseName " + i));
        }

//        RegularExerciseFragment reg1 = new RegularExerciseFragment();
//        vpAdapter.addFragment(reg1, "Bench");
        vpAdapter.addFragment(new TimerExerciseFragment(), "Plank");
//        vpAdapter.addFragment(new RegularExerciseFragment(), "Squat");
//        vpAdapter.addFragment(new RegularExerciseFragment(), "OHP");
//        vpAdapter.addFragment(new TimerExerciseFragment(), "Running");
//        vpAdapter.addFragment(new TimerExerciseFragment(), "Timer");
        viewPager.setAdapter(vpAdapter);
    }

    public void setExerciseState(boolean newState, String id){
        String emp = "";
        for (int i = 0; i < ids.size(); i++){
            emp += ids.get(i).getId() + " | ";
            if (ids.get(i).getId().equals(id)){
                ids.get(i).setState(newState);
                setNewFragment();
                return;
            }
        }
        Toast.makeText(this, emp + id, Toast.LENGTH_LONG).show();
    }

    private void setNewFragment(){
        for (int i = 0; i < ids.size(); i++){
            if (!ids.get(i).getState()){
                Toast.makeText(this, ids.get(i).getId(), Toast.LENGTH_LONG).show();
                viewPager.setCurrentItem(i);
                return;
            }
        }
    }

    private class ExerciseState{
        private String Id;
        private boolean State;

        public ExerciseState(String id){
            Id = id;
            State = false;
        }

        public void setState(boolean state){
            State = state;
        }

        public String getId(){
            return Id;
        }

        public boolean getState(){
            return State;
        }
    }
}