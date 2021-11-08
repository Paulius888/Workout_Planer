package com.example.workoutplaner.Exercises;

import android.util.Log;

import com.example.workoutplaner.Days.Day;
import com.example.workoutplaner.Workouts.Workout;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DAOExercise {

    private DatabaseReference databaseReference;
    public DAOExercise()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://workoutapp-dc337-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(Exercise.class.getSimpleName());
    }

    public Task<Void> add(Exercise exercise)
    {
        return databaseReference.push().setValue(exercise);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap)
    {
        return  databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        return databaseReference.child(key).removeValue();
    }
    public void removeByParent(String key) {
        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Exercise ex = data.getValue(Exercise.class);
                            String dayID = ex.getDayID();
                            if (key.equals(dayID)) {
                                remove(data.getKey());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    public Query get(String key)
    {
        if(key == null){
            return databaseReference.orderByKey();
        }
        return databaseReference.orderByKey().startAfter(key);
    }

    public Query get()
    {
        return databaseReference;
    }
}
