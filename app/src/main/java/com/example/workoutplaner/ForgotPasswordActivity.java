package com.example.workoutplaner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = findViewById(R.id.forgotten_email);
        auth = FirebaseAuth.getInstance();
    }

    private void onResetClick(View view) {
        String enteredEmail = email.getText().toString().trim();

        if(enteredEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(enteredEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this, "Try again! Something wrong happened", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}