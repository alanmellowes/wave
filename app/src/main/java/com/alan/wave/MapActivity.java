package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {


    EditText etSource, etDestination;
    Button btTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //assigning variables
        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);

        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                //checking condition
                if(sSource.equals("")&& sDestination.equals("")){
                    //when both values are blank
                    Toast.makeText(getApplicationContext(), "Enter both locations", Toast.LENGTH_SHORT).show();
                }else{
                    //considering both values are entered, display track
                    DisplayTrack(sSource, sDestination);

                }
            }
        });
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