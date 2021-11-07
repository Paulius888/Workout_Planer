package com.example.workoutplaner.Workouts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workoutplaner.R;
import com.example.workoutplaner.RVWorkoutDays;
import com.example.workoutplaner.Workouts.DAOWorkout;
import com.example.workoutplaner.Workouts.Workout;
import com.example.workoutplaner.Workouts.WorkoutPageActivity;
import com.example.workoutplaner.Workouts.WorkoutVH;

import java.util.ArrayList;

public class RVWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    ArrayList<Workout> list = new ArrayList<>();
    public RVWorkoutAdapter(Context ctx)
    {
        this.context = ctx;
    }
    public void setItems(ArrayList<Workout> workout)
    {
        list.addAll(workout);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_item,parent, false);
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
                Intent intent = new Intent(context, RVWorkoutDays.class);
                intent.putExtra("name", workout.getName());
                context.startActivity(intent);
            }
        });
        vh.txt_option.setOnClickListener(v->
        {
            PopupMenu popupMenu = new PopupMenu(context, vh.txt_option);
            popupMenu.inflate(R.menu.workout_menu);
            popupMenu.setOnMenuItemClickListener(item->
            {
                switch(item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, WorkoutPageActivity.class);
                        intent.putExtra("EDIT", workout);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOWorkout dao = new DAOWorkout();
                        dao.remove(workout.getKey()).addOnSuccessListener(suc ->
                        {
                            Toast.makeText(context, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(workout);
                        }).addOnFailureListener(er ->
                       {
                            Toast.makeText(context, ""+er.getMessage(), Toast.LENGTH_SHORT).show();
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
