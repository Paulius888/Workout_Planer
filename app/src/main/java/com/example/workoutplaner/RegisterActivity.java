package com.example.workoutplaner;

import static com.example.workoutplaner.R.layout.activity_register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createAcc;
    private EditText userName, email, cnfrmEmail, password, cnfrmPassword;
    private Context context = this;
    private FirebaseAuth mAuth;

    public void runLoginPage (boolean flag)
    {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(activity_register);

        createAcc = (Button) findViewById(R.id.button_crt_accnt);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        userName = (EditText) findViewById(R.id.register_user_name);
        email = (EditText) findViewById(R.id.register_email);
        cnfrmEmail = (EditText) findViewById(R.id.register_cnfrm_email);
        password = (EditText)  findViewById(R.id.register_password);
        cnfrmPassword = (EditText) findViewById(R.id.register_cnfrm_password);

        mAuth = FirebaseAuth.getInstance();
    }




    private void registerUser() {
        String userName2 = userName.getText().toString().trim();
        String email2 = email.getText().toString().trim();
        String cnfrmEmail2 = cnfrmEmail.getText().toString().trim();
        String password2 = password.getText().toString().trim();
        String cnfrmPassword2 = cnfrmPassword.getText().toString().trim();

        if (userName2.isEmpty()){
            userName.setError("Username is required");
            userName.requestFocus();
            return;
        }
        if (email2.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email2).matches()){
            email.setError("Please provide valid email");
            email.requestFocus();
            return;

        }
        if (cnfrmEmail2.isEmpty()){
            cnfrmEmail.setError("Confirmation of email is required");
            cnfrmEmail.requestFocus();
            return;
        }
        if (!email2.equals(cnfrmEmail2)){
            cnfrmEmail.setError("Emails doesn't match");
            cnfrmEmail.requestFocus();
            return;
        }
        if (password2.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (password2.length() < 6){
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;
        }
        if (cnfrmPassword2.isEmpty()){
            cnfrmPassword.setError("Confirmation of password is required");
            cnfrmPassword.requestFocus();
            return;
        }
        if (!password2.equals(cnfrmPassword2)){
            cnfrmPassword.setError("Passwords doesn't match");
            cnfrmPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email2, password2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()){
                            User user = new User(userName2, email2);

                            FirebaseDatabase.getInstance("https://workoutapp-dc337-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been registered succesfully", Toast.LENGTH_LONG).show();
                                        runLoginPage(true);
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Failed to register ! Try again !", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Failed to register ! Try again !", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    @Override
    public void onClick(View v) {

    }
}