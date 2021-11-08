package com.example.workoutplaner.Exercises;

import com.example.workoutplaner.Workouts.Workout;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

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
