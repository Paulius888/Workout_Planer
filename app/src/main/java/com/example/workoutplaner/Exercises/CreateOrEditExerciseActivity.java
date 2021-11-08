package com.example.workoutplaner.Exercises;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.example.workoutplaner.R;
import com.example.workoutplaner.Workouts.DAOWorkout;
import com.example.workoutplaner.Workouts.RVWorkout;
import com.example.workoutplaner.Workouts.Workout;
import com.example.workoutplaner.Workouts.WorkoutPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class CreateOrEditExerciseActivity extends AppCompatActivity {

    private EditText exerciseName, setsAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid();

        exerciseName = findViewById(R.id.exercise_name);
        setsAmount = findViewById(R.id.exercise_sets_amout);

        final EditText edit_name = findViewById(R.id.exercise_name);

        Button btn = findViewById(R.id.btn_submit);

        DAOExercise dao = new DAOExercise();
        Exercise exercise_edit = (Exercise) getIntent().getSerializableExtra("EDIT");

        if (exercise_edit != null) {
            btn.setText("UPDATE");
            edit_name.setText(exercise_edit.getName());
        }

        btn.setOnClickListener(v ->
        {
            Exercise exercise = new Exercise(exerciseName.getText().toString(), Integer.parseInt(setsAmount.getText().toString()), useruid);
            if (exercise_edit == null) {
                if (edit_name.length() <= 0) {
                    edit_name.setError("Name must be at least 1 character");
                    edit_name.requestFocus();
                    return;
                } else {
                    dao.add(exercise).addOnSuccessListener(suc ->
                    {
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
            } else {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("name", edit_name.getText().toString());
                if (edit_name.length() <= 0) {
                    edit_name.setError("Name must be at least 1 character");
                    edit_name.requestFocus();
                    return;
                } else {
                    dao.update(exercise_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                    {
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

    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        Intent intent = new Intent(this, ExercisesActivity.class);
//        intent.putExtra("workoutID", workoutID);
        startActivity(intent);
        this.finish();
    }
}
