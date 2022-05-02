package com.alan.wave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button button;
    EditText nEmail, nPassword, nConfirmPassword;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        button = findViewById(R.id.registerBtn);

        nEmail = findViewById(R.id.email2);
        nPassword = findViewById(R.id.rpassword);
        nConfirmPassword = findViewById(R.id.rpassword2);

        fAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        button = findViewById(R.id.registerBtn);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), featuresActivity.class));
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = nEmail.getText().toString().trim();
                String password = nPassword.getText().toString().trim();
                String confirmPassword = nConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    nEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    nPassword.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    nConfirmPassword.setError("Must confirm password");
                    return;
                }
                if(!(confirmPassword.equals(password))){
                    nConfirmPassword.setError("Passwords do not match");
                    return;
                }
                if(password.length() < 6){
                    nPassword.setError("Password must contain more than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //this registers the user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "user created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), featuresActivity.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButtonRegister);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}
