package com.example.workoutplaner;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workoutplaner.Workouts.WorkoutActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    EditText email, password;
    Button login, register;
    TextView forgotPassword;
    ProgressBar progressBar;
    boolean isLoading;
    private DatabaseReference mDatabase;



    public LoginFragment() {
        // Required empty public constructor
    }
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null)
       {
           Log.i("infoo" , "logged in");
       }
       else
       {
           Log.i("infoo" , "logged out");
       }
    }*/
    private void check()
    {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.i("logged" , "logged in");
        } else {
            Log.i("logged" , "logged out");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((OfflineActivity)getActivity()).setCurrentFragment(LoginFragment.class.getSimpleName());
      /*  if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
           redirectToApp();
           //when user logs out current user becomes null
        }
*/
        // Inflate the layout for this fragment
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //redirectToApp();
            Log.i("loged" , "logged in");
        }
        View v =  inflater.inflate(R.layout.activity_login, container, false);
        //View v =  inflater.inflate(R.layout.fragment_login, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference("log");
        mAuth = FirebaseAuth.getInstance();

        email = v.findViewById(R.id.email);
        password = v.findViewById(R.id.password);
        login = v.findViewById(R.id.login);
        login.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick(v);
            }
        }));
        register = v.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick(v);
            }
        });
        forgotPassword = v.findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgotPasswordClick(v);
            }
        });
        progressBar = v.findViewById(R.id.progressBar);
        isLoading = false;
        return v;
    }
    public void onLoginClick(View view) {
        String enteredEmail = email.getText().toString().trim();
        String enteredPassword = password.getText().toString().trim();

        if(enteredEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()) {
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }

        if(enteredPassword.isEmpty()) {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(isLoading) {
            return;
        }

        startLoading();

        mAuth.signInWithEmailAndPassword(enteredEmail, enteredPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {

                    if(mAuth.getCurrentUser().isEmailVerified()) {
                        redirectToApp();
                    }
                    else {

                        Toast.makeText(getContext(), "Email is still not verified!", Toast.LENGTH_LONG).show();
                        stopLoading();
                    }
                }
                else {
                    Toast.makeText(getContext(), "   ", Toast.LENGTH_SHORT).show();
                    stopLoading();
                    Context context = getContext();
                    Toast.makeText(context, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void onForgotPasswordClick(View view) {
        ForgotPasswordFragment nextFrag= new ForgotPasswordFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right)
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private boolean isUserVerified(FirebaseUser user) {
        if(user != null) {
            return true;
        }

        return user.isEmailVerified();
    }

    private void redirectToApp() {
        Intent intent = new Intent(getContext(), WorkoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void startLoading() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        isLoading = false;
        progressBar.setVisibility(View.GONE);
    }

    public void onRegisterClick(View view) {
        RegisterFragment nextFrag= new RegisterFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_left,
                        R.anim.slide_out_right)
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit();
    }

}
//https://guides.codepath.com/android/creating-and-using-fragments