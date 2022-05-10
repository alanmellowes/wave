package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class savingsActivity extends AppCompatActivity {
    View vaultCardView, todayCardView, weekCardView, montyhCardView, analyticsCardView, homeCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        vaultCardView = findViewById(R.id.budget);

        vaultCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, VaultActivity.class));
            }
        });
        //when user clicks savings on card view
        todayCardView = findViewById(R.id.todaysActivity);

        todayCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, MoneySpentTodayActivity.class));
            }
        });

        weekCardView = findViewById(R.id.weekActivity);

        weekCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(savingsActivity.this, WeekSpendingActivity.class);
                intent.putExtra("type","week");
                startActivity(intent);
            }
        });

        montyhCardView = findViewById(R.id.monthActivity);

        montyhCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(savingsActivity.this, WeekSpendingActivity.class);
                intent.putExtra("type","month");
                startActivity(intent);
            }
        });

        //when user clicks savings on card view
        analyticsCardView = findViewById(R.id.analytics);

        analyticsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, DailyAnalyticsActivity.class));
            }
        });


        homeCardView = findViewById(R.id.homeActivity);

        homeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, featuresActivity.class));
            }
        });
    }

}