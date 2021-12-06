package com.example.workoutplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText email, password;
    Button login, register;
    LoginButton loginButton;
    TextView forgotPassword;
    ProgressBar progressBar;
    boolean isLoading;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        forgotPassword = findViewById(R.id.forgotPassword);
        progressBar = findViewById(R.id.progressBar);
        isLoading = false;
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
                        Context context = getApplicationContext();
                        Toast.makeText(context, "Email is still not verified!", Toast.LENGTH_LONG).show();
                        stopLoading();
                    }
                }
                else {
                    stopLoading();
                    Context context = getApplicationContext();
                    Toast.makeText(context, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onForgotPasswordClick(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    private boolean isUserVerified(FirebaseUser user) {
        if(user != null) {
            return true;
        }

        return user.isEmailVerified();
    }

    private void redirectToApp() {
        Intent intent = new Intent(this, WorkoutActivity.class);
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
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}