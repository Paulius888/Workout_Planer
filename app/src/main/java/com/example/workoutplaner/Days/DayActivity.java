package com.example.workoutplaner.Days;

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

public class DayActivity extends AppCompatActivity
{
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVDayAdapter adapter;
    DAODay dao;
    private TextView emptyView;
    boolean isLoading=false;
    String key =null;
    private FloatingActionButton workoutActivityAddButton;
    private Context context = this;
    private String workoutID;
    private String titleName;

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
        setContentView(R.layout.day_rv);

        Bundle bundle = getIntent().getExtras();
        workoutID = bundle.getString("workoutID");
        titleName = bundle.getString("titleName");
        setTitle(titleName + " days");


        emptyView = findViewById(R.id.empty_view);
        swipeRefreshLayout = findViewById(R.id.swipe);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RVDayAdapter(this);
        recyclerView.setAdapter(adapter);
        dao = new DAODay();
        workoutActivityAddButton = (FloatingActionButton) findViewById(R.id.addingBtn);
        workoutActivityAddButton.setOnClickListener(startAddingActivity);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = user.getUid();
        loadData(userUID);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItem = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(totalItem < lastVisible)
                {
                    if(!isLoading)
                    {
                        isLoading=true;
                        loadData(userUID);
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
                ArrayList<Day> days = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    Day day = data.getValue(Day.class);
                    String usersid = day.getUserID();
                    String dayWorkoutId = day.getWorkoutID();
                    if(useruid.equals(usersid) && workoutID.equals(dayWorkoutId)) {
                        day.setKey(data.getKey());
                        days.add(day);
                        key = data.getKey();
                    }
                }
                adapter.setItems(days, workoutID, titleName);
                if (days.isEmpty()) {
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
        Intent intent = new Intent(this, CreateOrEditDayActivity.class);
        intent.putExtra("workoutID", workoutID);
        intent.putExtra("titleName", titleName);
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
