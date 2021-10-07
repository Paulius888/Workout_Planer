package com.example.workoutplaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText email, password;
    Button login, register;
    TextView forgotPassword;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRegisterPage(true);
            }
        });

        forgotPassword = findViewById(R.id.forgotPassword);
    }
    
    public void onLoginClick(View view) {
        String enteredEmail = email.getText().toString();
        String enteredPassword = password.getText().toString();

        if(enteredEmail.equals("test@test.com") && enteredPassword.equals("test")) {
            redirectToMain();
        }
    }

    private void redirectToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void redirectToRegister() {
        // TODO: Implement a redirect to Register
    }

    @Override
    public void onClick(View v) {

    }
    public void runRegisterPage (boolean flag)
    {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}