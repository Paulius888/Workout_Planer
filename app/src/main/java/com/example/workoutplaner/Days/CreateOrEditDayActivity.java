package com.example.workoutplaner.Days;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class CreateOrEditDayActivity extends AppCompatActivity {

    AnimatedVectorDrawable avd;
    String workoutID;

    @Override
    public void onBackPressed() {
        endActivity();
    }

    private void endActivity() {
        Intent intent = new Intent(CreateOrEditDayActivity.this, DayActivity.class);
        intent.putExtra("workoutID", workoutID);
        startActivity(intent);
        avd.stop();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_day);

        Bundle bundle = getIntent().getExtras();
        workoutID = bundle.getString("workoutID");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String useruid = user.getUid();
        ImageView done = findViewById(R.id.imageView_done);
        avd = (AnimatedVectorDrawable) done.getDrawable();

        final EditText edit_name = findViewById(R.id.edit_name);

        Button btn = findViewById(R.id.btn_submit);
        DAODay dao = new DAODay();
        Day day_Item_edit = (Day)getIntent().getSerializableExtra("EDIT");
        if(day_Item_edit != null){
            edit_name.setText(day_Item_edit.getName());
            workoutID = day_Item_edit.getWorkoutID();
        }
        btn.setOnClickListener(v->
        {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            View view = this.getCurrentFocus();
            if (view == null) {
                view = new View(this);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            Day day = new Day(edit_name.getText().toString(), useruid, workoutID);
            if(day_Item_edit ==null) {
                if(edit_name.length() <= 0){
                    edit_name.setError("Name must be at least 1 character");
                    edit_name.requestFocus();
                    return;
                }
                else {
                    dao.add(day).addOnSuccessListener(suc ->
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
                      dao.update(day_Item_edit.getKey(), hashMap).addOnSuccessListener(suc ->
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
