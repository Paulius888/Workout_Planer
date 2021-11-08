package com.example.workoutplaner.Workouts;

import com.example.workoutplaner.Days.DAODay;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class DAOWorkout {

    private DatabaseReference databaseReference;
    public DAOWorkout()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://workoutapp-dc337-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(Workout.class.getSimpleName());
    }

    public Task<Void> add(Workout workout)
    {
        return databaseReference.push().setValue(workout);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap)
    {
        return  databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        DAODay dayDB = new DAODay();
        dayDB.removeByParent(key);
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
