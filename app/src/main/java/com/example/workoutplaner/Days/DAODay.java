package com.example.workoutplaner.Days;

import android.util.Log;

import com.example.workoutplaner.Exercises.DAOExercise;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DAODay {

    private DatabaseReference databaseReference;
    FirebaseDatabase db;
    public DAODay()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://workoutapp-dc337-default-rtdb.europe-west1.firebasedatabase.app");
        databaseReference = db.getReference(Day.class.getSimpleName());
    }

    public Task<Void> add(Day day)
    {
        return databaseReference.push().setValue(day);
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap)
    {
        return  databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key)
    {
        DAOExercise exDAO = new DAOExercise();
        exDAO.removeByParent(key);
        return databaseReference.child(key).removeValue();
    }
    public void removeByParent(String key) {
        databaseReference.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Day day = data.getValue(Day.class);
                            String dayWorkoutId = day.getWorkoutID();
                            if (key.equals(dayWorkoutId)) {
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
