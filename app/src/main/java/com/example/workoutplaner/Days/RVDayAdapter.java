package com.example.workoutplaner.Days;

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

import com.example.workoutplaner.ExercisesActivity;
import com.example.workoutplaner.R;

import java.util.ArrayList;

public class RVDayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity activity;
    ArrayList<Day> list = new ArrayList<>();
    public RVDayAdapter(Activity ctx)
    {
        this.activity = ctx;
    }
    public void setItems(ArrayList<Day> day)
    {
        list.addAll(day);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.day_item, parent, false);
        return new DayVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        Day w = null;
        this.onBindViewHolder(holder,position,w);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, Day w) {
        DayVH vh = (DayVH) holder;
        Day day = w==null? list.get(position):w;
        vh.txt_name.setText(day.getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ExercisesActivity.class);
                intent.putExtra("dayID", day.getName());
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
                        Intent intent = new Intent(activity, CreateOrEditDayActivity.class);
                        intent.putExtra("EDIT", day);
                        activity.startActivity(intent);
                        activity.finish();
                        break;
                    case R.id.menu_remove:
                        DAODay dao = new DAODay();
                        dao.remove(day.getKey()).addOnSuccessListener(suc ->
                        {
                            Toast.makeText(activity, "Record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            list.remove(day);
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
