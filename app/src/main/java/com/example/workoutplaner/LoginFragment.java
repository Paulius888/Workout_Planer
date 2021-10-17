package com.example.workoutplaner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    EditText email, password;
    Button login, register;
    TextView forgotPassword;
    ProgressBar progressBar;
    boolean isLoading;


        public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_login, container, false);
        //View v =  inflater.inflate(R.layout.fragment_login, container, false);
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

                RegisterFragment nextFrag= new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, nextFrag, "findThisFragment")
                        .setReorderingAllowed(true)
                        .commit();
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
                    if(isUserVerified(mAuth.getCurrentUser())) {

                        redirectToApp();
                    }
                    else {
                        Context context = getContext();
                        Toast.makeText(context, "Email is still not verified!", Toast.LENGTH_LONG).show();
                        stopLoading();
                    }
                }
                else {
                    stopLoading();
                    Context context = getContext();
                    Toast.makeText(context, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onForgotPasswordClick(View view) {
        ForgotPasswordFragment nextFrag= new ForgotPasswordFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .commit();
    }

    private boolean isUserVerified(FirebaseUser user) {
        if(user != null) {
            return true;
        }

        return user.isEmailVerified();
    }

    private void redirectToApp() {
        Intent intent = new Intent(getContext(), RVWorkout.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .setReorderingAllowed(true)
                .commit();
    }

}