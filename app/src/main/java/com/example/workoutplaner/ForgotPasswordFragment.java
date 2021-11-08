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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordFragment extends Fragment {

    EditText email;
    FirebaseAuth auth;
    Button button;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((OfflineActivity)getActivity()).setCurrentFragment(ForgotPasswordFragment.class.getSimpleName());
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.activity_forgot_password, container, false);
        //View v =  inflater.inflate(R.layout.fragment_forgot_password, container, false);

        email = v.findViewById(R.id.forgotten_email);
        auth = FirebaseAuth.getInstance();
        button = v.findViewById(R.id.reset_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetClick(v);
            }
        });
        return v;
    }
    private void onResetClick(View view) {
        String enteredEmail = email.getText().toString().trim();

        if(enteredEmail.isEmpty()) {
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()) {
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(enteredEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getContext(), "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                    toLogin();
                }
                else {
                    Toast.makeText(getContext(), "Try again! Something wrong happened", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void toLogin()
    {
        LoginFragment nextFrag= new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right)
                .replace(R.id.fragment, nextFrag, "findThisFragment")
                .setReorderingAllowed(true)
                .commit();

    }
}