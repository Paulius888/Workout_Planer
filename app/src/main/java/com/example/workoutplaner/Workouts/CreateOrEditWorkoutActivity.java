package com.example.workoutplaner.Workouts;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.workoutplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class CreateOrEditWorkoutActivity extends AppCompatActivity {
    AnimatedVectorDrawable avd;

    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        Intent intent = new Intent(this, WorkoutActivity.class);
        startActivity(intent);
        avd.stop();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutpage);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String useruid=user.getUid();
<<<<<<< HEAD:app/src/main/java/com/example/workoutplaner/Workouts/CreateOrEditWorkoutActivity.java
=======
        // used for displaying vector view
>>>>>>> 21e67cd (No changes made):app/src/main/java/com/example/workoutplaner/Workouts/WorkoutPageActivity.java
        ImageView done = findViewById(R.id.imageView_done);
        avd = (AnimatedVectorDrawable) done.getDrawable();

<<<<<<< HEAD:app/src/main/java/com/example/workoutplaner/Workouts/CreateOrEditWorkoutActivity.java
        final EditText edit_name = findViewById(R.id.edit_name);

=======
        final EditText edit_name = findViewById(R.id.exercise_name);
        //final EditText edit_position = findViewById(R.id.edit_position);
>>>>>>> 21e67cd (No changes made):app/src/main/java/com/example/workoutplaner/Workouts/WorkoutPageActivity.java
        Button btn = findViewById(R.id.btn_submit);
        DAOWorkout dao = new DAOWorkout();
        Workout workout_edit = (Workout)getIntent().getSerializableExtra("EDIT");
        if(workout_edit != null){
            btn.setText("UPDATE");
            edit_name.setText(workout_edit.getName());
        }
        btn.setOnClickListener(v->
        {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            View view = this.getCurrentFocus();
            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
              Workout workout = new Workout(edit_name.getText().toString(), useruid);
              if(workout_edit==null) {
                  if(edit_name.length() <= 0){
                      edit_name.setError("Name must be at least 1 character");
                      edit_name.requestFocus();
                      return;
                  }
                  else {
                      dao.add(workout).addOnSuccessListener(suc ->
                      {
                          avd.start();
                          Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                          Handler handler = new Handler();
                          handler.postDelayed(new Runnable() {
                              public void run() {
                                  endActivity();
                              }
                          }, 500);
                      }).addOnFailureListener(er ->
                      {
                          Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                      });
                  }
              }
              else{
                   HashMap<String,Object> hashMap = new HashMap<>();
                   hashMap.put("name", edit_name.getText().toString());

                  if(edit_name.length() <= 0){
                      edit_name.setError("Name must be at least 1 character");
                      edit_name.requestFocus();
                      return;
                  }
                  else {
                      dao.update(workout_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                      {
                          avd.start();
                          Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                          Handler handler = new Handler();
                          handler.postDelayed(new Runnable() {
                              public void run() {
                                  endActivity();
                              }
                          }, 500);
                      }).addOnFailureListener(er ->
                      {
                          Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                      });
                  }
              }


        });

    }
}
