package com.example.workoutplaner.Exercises;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class DAODoneExercise {
    private DatabaseReference databaseReference;
    public DAODoneExercise()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://workoutapp-dc337-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(DoneExercise.class.getSimpleName());
    }

    public Task<Void> add(DoneExercise exercise)
    {
        return databaseReference.push().setValue(exercise);
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
