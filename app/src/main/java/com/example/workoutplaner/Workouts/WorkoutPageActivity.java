package com.example.workoutplaner.Workouts;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import com.example.workoutplaner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class WorkoutPageActivity extends AppCompatActivity {

    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutpage);
        //gets user id from database
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String useruid=user.getUid();
        // used for dispalying vector view
        ImageView done = findViewById(R.id.imageView_done);

        final EditText edit_name = findViewById(R.id.edit_name);
        //final EditText edit_position = findViewById(R.id.edit_position);
        Button btn = findViewById(R.id.btn_submit);
        Button btn_open = findViewById(R.id.btn_open);
        btn_open.setOnClickListener(v->
        {
            Intent intent = new Intent(WorkoutPageActivity.this, RVWorkout.class);
            startActivity(intent);
        });
        DAOWorkout dao = new DAOWorkout();
        Workout workout_edit = (Workout)getIntent().getSerializableExtra("EDIT");
        if(workout_edit != null){
            btn.setText("UPDATE");
            edit_name.setText(workout_edit.getName());
            //edit_position.setText(workout_edit.getPosition());
            btn_open.setVisibility(View.VISIBLE);
        }
        else
        {
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v->
        {
              Drawable drawable = done.getDrawable();
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
                          if(drawable instanceof AnimatedVectorDrawableCompat){
                              avd = (AnimatedVectorDrawableCompat) drawable;
                              avd.start();
                          } else if (drawable instanceof AnimatedVectorDrawable){
                              avd2 = (AnimatedVectorDrawable) drawable;
                              avd2.start();
                          }
                          Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                      }).addOnFailureListener(er ->
                      {
                          Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                      });
                  }
              }
              else{
                   HashMap<String,Object> hashMap = new HashMap<>();
                   hashMap.put("name", edit_name.getText().toString());
                   //hashMap.put("position", edit_position.getText().toString());
                  if(edit_name.length() <= 0){
                      edit_name.setError("Name must be at least 1 character");
                      edit_name.requestFocus();
                      return;
                  }
                  else {
                      dao.update(workout_edit.getKey(), hashMap).addOnSuccessListener(suc ->
                      {
                          if(drawable instanceof AnimatedVectorDrawableCompat){
                              avd = (AnimatedVectorDrawableCompat) drawable;
                              avd.start();
                          } else if (drawable instanceof AnimatedVectorDrawable){
                              avd2 = (AnimatedVectorDrawable) drawable;
                              avd2.start();
                          }
                          Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                          //finish();
                      }).addOnFailureListener(er ->
                      {
                          Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                      });
                  }
              }


        });

    }
}
