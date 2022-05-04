package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class savingsActivity extends AppCompatActivity {
    View paymentCardView, savingsCardView, cryptoCardView, settingsCardView, analyticsCardView;

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
        savingsCardView = findViewById(R.id.todaysActivity);

        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(savingsActivity.this, MoneySpentTodayActivity.class));
            }
        });

        cryptoCardView = findViewById(R.id.weekActivity);

        cryptoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(savingsActivity.this, WeekSpendingActivity.class);
                intent.putExtra("type","week");
                startActivity(intent);
            }
        });

        settingsCardView = findViewById(R.id.monthActivity);

        settingsCardView.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(savingsActivity.this, ChooseAnalyticActivity.class));
            }
        });

        //when user clicks savings on card view
        //savingsCardView = findViewById(R.id.todaysActivity);

        //savingsCardView.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View view) {
              //  startActivity(new Intent(savingsActivity.this, MoneySpentTodayActivity.class));
            //}
        //});
    }

}