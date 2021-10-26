package com.example.workoutplaner;


import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class RVWorkout extends AppCompatActivity
{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVWorkoutAdapter adapter;
    DAOWorkout dao;
    boolean isLoading=false;
    String key =null;
    private FloatingActionButton workoutActivityAddButton;
    private Context context = this;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_rv);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RVWorkoutAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAOWorkout();
        workoutActivityAddButton = (FloatingActionButton) findViewById(R.id.addingBtn);
        workoutActivityAddButton.setOnClickListener(startAddingActivity);
        loadData();


        //prints user ID to logcat during execution
        String uid = user.getUid();
        Log.i("Info", uid);
        //****************************************

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem< lastVisible)
                {
                    if(!isLoading)
                    {
                        isLoading=true;
                        loadData();
                    }

                }
            }
        });
    }

    private void loadData()
    {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //holds workout objects
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Workout> workouts = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    Workout workout = data.getValue(Workout.class);
                    workout.setKey(data.getKey());
                    workouts.add(workout);
                    key = data.getKey();
                }
                adapter.setItems(workouts);
                adapter.notifyDataSetChanged();
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void runAddingActivity(boolean flag){
        Intent intent = new Intent(context, WorkoutPageActivity.class);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }

    View.OnClickListener startAddingActivity = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            runAddingActivity(true);
        }
    };
}
