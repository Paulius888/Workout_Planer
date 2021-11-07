package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void onWorkoutClick(View view) {
        Intent intent = new Intent(this, RVWorkout.class);
        this.startActivity(intent);
    }

    public void onLogoutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            Log.i("loged out", "loged out");
        }
        else
        {
            Log.i("loged ", "loged ");
        }
        finishAffinity();

        System.exit(0);
        /*
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);*/
       // Intent intent = new Intent(this, LoginFragment.class);
     //   startActivity(intent);
    }

}