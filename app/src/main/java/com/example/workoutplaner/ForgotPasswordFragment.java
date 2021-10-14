package com.example.workoutplaner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ForgotPasswordFragment extends Fragment {

    EditText email;
    FirebaseAuth auth;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_forgot_password, container, false);

        email = v.findViewById(R.id.forgotten_email);
        auth = FirebaseAuth.getInstance();
        return v;
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
                    Toast.makeText(getContext(), "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Try again! Something wrong happened", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}