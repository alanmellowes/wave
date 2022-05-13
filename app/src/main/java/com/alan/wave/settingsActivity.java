package com.alan.wave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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

        String sSource = "My Location";
        String sDestination = "ATM";



        mapsCardView = findViewById(R.id.hikeFeature);

        mapsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayTrack(sSource, sDestination);
            }
        });
        //when user clicks steps on card view
        homeCardView = findViewById(R.id.stepFeature);

        homeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(settingsActivity.this, StepActivity.class));
            }
        });
    }



    //logouts user that's currently logged in
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    private void DisplayTrack(String sSource, String sDestination) {
        //if the users device doesn't have a map installed then redirect them to the play store
        try {
            Uri uri = Uri.parse("http://www.google.co.in/maps/dir/"+sSource+ "/"
                    +sDestination);

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            //when maps isn't installed
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
