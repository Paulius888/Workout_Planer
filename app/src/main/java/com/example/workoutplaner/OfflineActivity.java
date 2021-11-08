package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.BackStackEntry;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.workoutplaner.Workouts.WorkoutActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class OfflineActivity extends AppCompatActivity {

    private String currentFragment;
    public  void setCurrentFragment(String fragmentName)
    {
        currentFragment = fragmentName;
    }
    @Override
    public void onBackPressed() {
        if (currentFragment == LoginFragment.class.getSimpleName())
        {
            super.onBackPressed();
            return;
        }

        LoginFragment loginFragment = new LoginFragment();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left)
                .replace(R.id.fragment, loginFragment , "findThisFragment")
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            redirectToApp();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        LoginFragment fragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }
    private void redirectToApp() {
        Intent intent = new Intent(this, WorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}