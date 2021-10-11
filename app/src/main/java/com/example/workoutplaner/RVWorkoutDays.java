package com.example.workoutplaner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVWorkoutDays extends AppCompatActivity {

    private ArrayList<WorkoutDay> workoutDayList;
    private RecyclerView mRecyclerView;
    private RVWorkoutDayAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = this;

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
        setContentView(R.layout.workoutday_rv);

        getIntentBack();

        ArrayList<WorkoutDay> workoutDayList = new ArrayList<>();
        workoutDayList.add(new WorkoutDay( "Monday"));
        workoutDayList.add(new WorkoutDay( "Tuesday"));
        workoutDayList.add(new WorkoutDay("Friday"));
        workoutDayList.add(new WorkoutDay("Saturday"));

        mRecyclerView = findViewById(R.id.rv_workoutDays);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RVWorkoutDayAdapter(workoutDayList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RVWorkoutDayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, ExercisesActivity.class);
                context.startActivity(intent);
            }
        });
    }


    private void getIntentBack(){
        if(getIntent().hasExtra("name"));{
            String name = getIntent().getStringExtra("name");

            setName(name);
        }
    }

    private void setName(String workoutName){
        TextView name = findViewById(R.id.txt_name_days);
        name.setText(workoutName);
    }
}