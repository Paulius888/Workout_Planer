package com.example.workoutplaner.Exercises;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.workoutplaner.MenuHandler;
import com.example.workoutplaner.R;
import com.example.workoutplaner.VPAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ExercisesActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private long start;
    private DAOExercise dao;
    private FloatingActionButton addExerciseButton;
    private ArrayList<Exercise> regExercises;
    private ArrayList<ArrayList<ExerciseInput>> savedExercises;
    private String dayID;
    private String userID;
    private String titleName;
    private String key;

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

        Bundle bundle = getIntent().getExtras();
        titleName= bundle.getString("titleName");
        dayID = bundle.getString("dayID");

        setTitle(titleName + " exercises");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        addExerciseButton = findViewById(R.id.addingBtn3);
        tabLayout.setupWithViewPager(viewPager);
        start = SystemClock.elapsedRealtime();
        dao = new DAOExercise();
        savedExercises = new ArrayList<>();
        addExerciseButton.setOnClickListener(view ->{
            onAddExerciseClick(view);
        });
        key = null;
        loadData(userID);
    }

    private void fillViewPager(){
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        for (int i = 0; i < regExercises.size(); i++){
            savedExercises.add(new ArrayList<ExerciseInput>());
            Bundle bundle = new Bundle();

            bundle.putSerializable("regularExercisesList", savedExercises.get(i));
            bundle.putSerializable("exercise", regExercises.get(i));
            RegularExerciseFragment reg1 = new RegularExerciseFragment();
            reg1.setArguments(bundle);
            vpAdapter.addFragment(reg1, regExercises.get(i).getName());
            Toast.makeText(this, dayID, Toast.LENGTH_LONG).show();
        }

        vpAdapter.addFragment(new TimerExerciseFragment(), "Time");
        viewPager.setAdapter(vpAdapter);
    }

    public long TimeElapsed() {
        long end = System.currentTimeMillis();
        return start;
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

    private void loadData(String userID) {
        dao.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                regExercises = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Exercise ex = data.getValue(Exercise.class);
                    String usersid = ex.getUserID();
                    String exerciseDayId = ex.getDayID();
                    if(userID.equals(usersid) && dayID.equals(exerciseDayId)) {
                        ex.setKey(data.getKey());
                        regExercises.add(ex);
                        key = data.getKey();
                    }
                    else {
                        Log.e("COULDNT ADD, USER ID:", userID);
                        Log.e("exercise USER ID:", usersid);
                        Log.e("COULDNT ADD, DAY ID:", dayID);
                        Log.e("exercise day ID:", exerciseDayId);
                    }
                }
                fillViewPager();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onAddExerciseClick(View view){
        Intent intent = new Intent(this, CreateOrEditExerciseActivity.class);
        intent.putExtra("dayID", dayID);
        intent.putExtra("titleName", titleName);
        intent.putExtra("userID", userID);
        startActivity(intent);
        finish();
    }

    public void sendDoneExercises(){
        DAODoneExercise ex = new DAODoneExercise();
        for (int i = 0; i < savedExercises.size(); i++){
            DoneExercise done = new DoneExercise(savedExercises.get(i));
            ex.add(done);
        }
    }
}