package com.example.workoutplaner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVWorkoutDayAdapter extends RecyclerView.Adapter<RVWorkoutDayAdapter.ViewHolder> {

    private ArrayList<WorkoutDay> mWorkoutList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
                super(itemView);
                mTextView = itemView.findViewById(R.id.txt_day);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


    }

    public RVWorkoutDayAdapter(ArrayList<WorkoutDay> workoutdaylist) {
        mWorkoutList = workoutdaylist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.workoutday_item, parent, false);
        ViewHolder vh = new ViewHolder(v,mListener);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WorkoutDay currentItem = mWorkoutList.get(position);
        holder.mTextView.setText(currentItem.getText());

    }
    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }
}
