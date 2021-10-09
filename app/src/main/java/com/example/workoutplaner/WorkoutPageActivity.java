package com.example.workoutplaner;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class WorkoutPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutpage);
        final EditText edit_name = findViewById(R.id.edit_name);
        final EditText edit_position = findViewById(R.id.edit_position);
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
            edit_position.setText(workout_edit.getPosition());
            btn_open.setVisibility(View.VISIBLE);
        }
        else
        {
            btn.setText("SUBMIT");
            btn_open.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(v->
        {
              Workout workout = new Workout(edit_name.getText().toString(), edit_position.getText().toString());
              if(workout_edit==null) {
                  dao.add(workout).addOnSuccessListener(suc ->
                  {
                      Toast.makeText(this, "Record is inserted", Toast.LENGTH_SHORT).show();
                  }).addOnFailureListener(er ->
                  {
                      Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                  });
              }
              else{
                   HashMap<String,Object> hashMap = new HashMap<>();
                   hashMap.put("name", edit_name.getText().toString());
                   hashMap.put("position", edit_position.getText().toString());
                   dao.update(workout_edit.getKey(),hashMap).addOnSuccessListener(suc ->
                   {
                      Toast.makeText(this, "Record is updated", Toast.LENGTH_SHORT).show();
                      //finish();
                   }).addOnFailureListener(er ->
                   {
                       Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                   });
              }


        });

    }
}
