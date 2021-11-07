package com.example.workoutplaner.Workouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.workoutplaner.MenuHandler;
import com.example.workoutplaner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity
{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVWorkoutAdapter adapter;
    DAOWorkout dao;
    private TextView emptyView;
    boolean isLoading=false;
    String key =null;
    private FloatingActionButton workoutActivityAddButton;
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
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_rv);
        emptyView = findViewById(R.id.empty_view);
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
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String useruid=user.getUid();
        loadData(useruid);
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
                        loadData(useruid);
                    }

                }
            }
        });
    }

    private void loadData(String useruid)
    {
        swipeRefreshLayout.setRefreshing(true);
        dao.get(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Workout> workouts = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    Workout workout = data.getValue(Workout.class);
                    String usersid = workout.getUser();
                    if(useruid.equals(usersid)) {
                        workout.setKey(data.getKey());
                        workouts.add(workout);
                        key = data.getKey();
                    }
                }
                adapter.setItems(workouts);
                if (workouts.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
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

    public void runAddingActivity(){
        Intent intent = new Intent(this, CreateOrEditWorkoutActivity.class);
        startActivity(intent);
        finish();
    }

    View.OnClickListener startAddingActivity = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            runAddingActivity();
        }
    };
}
