package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class savingsActivity extends AppCompatActivity {
    View paymentCardView, savingsCardView, cryptoCardView, settingsCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        paymentCardView = findViewById(R.id.budget);

        paymentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, BudgetActivity.class));
            }
        });
        //when user clicks savings on card view
        savingsCardView = findViewById(R.id.savingsFeature);

        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, savingsActivity.class));
            }
        });

        cryptoCardView = findViewById(R.id.cryptoFeature);

        cryptoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, cryptoActivity.class));
            }
        });

        settingsCardView = findViewById(R.id.settingsFeature);

        settingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, settingsActivity.class));
            }
        });
    }


}