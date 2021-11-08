package com.example.workoutplaner.Workouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplaner.Days.DayActivity;
import com.example.workoutplaner.R;

import java.util.ArrayList;

public class RVWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity activity;
    ArrayList<Workout> list = new ArrayList<>();
    public RVWorkoutAdapter(Activity ctx)
    {
        this.activity = ctx;
    }
    public void setItems(ArrayList<Workout> workout)
    {
        list.addAll(workout);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.workout_item,parent, false);
        return new WorkoutVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        Workout w = null;
        this.onBindViewHolder(holder,position,w);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Workout w) {
        WorkoutVH vh = (WorkoutVH) holder;
        Workout workout = w==null? list.get(position):w;
        vh.txt_name.setText(workout.getName());
        //vh.txt_position.setText(workout.getPosition());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DayActivity.class);
                intent.putExtra("workoutID", workout.getKey());
                intent.putExtra("titleName", workout.getName());
                activity.startActivity(intent);
            }
        });
        vh.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(activity, vh.txt_option);
            popupMenu.inflate(R.menu.workout_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch(item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent = new Intent(activity, CreateOrEditWorkoutActivity.class);
                        intent.putExtra("EDIT", workout);
                        activity.startActivity(intent);
                        activity.finish();
                        break;
                    case R.id.menu_remove:
                        DAOWorkout dao = new DAOWorkout();
                        dao.remove(workout.getKey()).addOnSuccessListener(suc ->
                        {
                            Toast.makeText(activity, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(workout);
                        }).addOnFailureListener(er ->
                       {
                            Toast.makeText(activity, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
                       });
                       break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
