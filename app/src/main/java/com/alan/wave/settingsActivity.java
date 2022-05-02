package com.alan.wave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class settingsActivity extends AppCompatActivity {


    TextView useremail;
    FirebaseAuth fAuth;
    String userID;

    View mapsCardView, homeCardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        useremail = findViewById(R.id.usersemail);
        fAuth = FirebaseAuth.getInstance();

        userID = fAuth.getCurrentUser().getEmail().toString();      //gets current user

        useremail.setText(userID);      //prints users email to screen



        mapsCardView = findViewById(R.id.hikeFeature);

        mapsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this, MainActivity.class));
            }
        });
        //when user clicks steps on card view
        homeCardView = findViewById(R.id.stepFeature);

        homeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this, featuresActivity.class));
            }
        });
    }



    //logouts user that's currently logged in
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
