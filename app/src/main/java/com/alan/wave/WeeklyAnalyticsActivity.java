package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChartView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WeeklyAnalyticsActivity extends AppCompatActivity {

    private Toolbar SettingsToolbar;

    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef, personalRef;

    private TextView totalBudgetAmountTextView, analyticsPersonalAmount, analyticsTransportAmount, analyticsEntertainmentAmount, analyticsOtherAmount ,analyticsFoodAmount;

    private RelativeLayout relativeLayoutPersonal, relativeLayoutTransport, relativeLayoutEntertainment, relativeLayoutOther, relativeLayoutFood, linearLayoutAnalysis;

    private AnyChartView anyChartView;
    private TextView progress_ratio_personal, progress_ratio_transport, progress_ratio_entertainment, progress_ratio_other, progress_ratio_food, monthSpendAmount, monthRatioSpending;
    private ImageView status_image_personal, status_image_transport, status_image_entertainment, status_image_other, status_image_food, monthRatioSpending_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_analytics);

        SettingsToolbar = findViewById(R.id.SettingsToolbar);
        setSupportActionBar(SettingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Weekly Analytics");


        mAuth = FirebaseAuth.getInstance();
        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        expensesRef = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);
        personalRef = FirebaseDatabase.getInstance(strURL).getReference("personal").child(onlineUserId);

        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);

        //analytics
        linearLayoutAnalysis = findViewById(R.id.linearLayoutAnalysis);
        monthSpendAmount = findViewById(R.id.monthSpendAmount);
        monthRatioSpending = findViewById(R.id.monthRatioSpending);
        monthRatioSpending_Image = findViewById(R.id.monthRatioSpending_Image);

        relativeLayoutPersonal = findViewById(R.id.relativeLayoutPersonal);
        relativeLayoutTransport = findViewById(R.id.relativeLayoutTransport);
        relativeLayoutEntertainment = findViewById(R.id.relativeLayoutEntertainment);
        relativeLayoutOther = findViewById(R.id.relativeLayoutOther);
        relativeLayoutFood = findViewById(R.id.relativeLayoutFood);


        //text views
        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);
        analyticsPersonalAmount = findViewById(R.id.analyticsPersonalAmount);
        analyticsTransportAmount = findViewById(R.id.analyticsTransportAmount);
        analyticsEntertainmentAmount = findViewById(R.id.analyticsEntertainmentAmount);
        analyticsOtherAmount = findViewById(R.id.analyticsOtherAmount);
        analyticsFoodAmount = findViewById(R.id.analyticsFoodAmount);

        progress_ratio_personal = findViewById(R.id.progress_ratio_personal);
        progress_ratio_transport = findViewById(R.id.progress_ratio_transport);
        progress_ratio_entertainment = findViewById(R.id.progress_ratio_entertainment);
        progress_ratio_other = findViewById(R.id.progress_ratio_other);
        progress_ratio_food = findViewById(R.id.progress_ratio_food);

        //image views
        status_image_personal = findViewById(R.id.status_image_personal);
        status_image_transport = findViewById(R.id.status_image_transport);
        status_image_entertainment = findViewById(R.id.status_image_entertainment);
        status_image_other = findViewById(R.id.status_image_other);
        status_image_food = findViewById(R.id.status_image_food);
    }
}