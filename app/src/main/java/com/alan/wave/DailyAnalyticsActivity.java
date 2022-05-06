package com.alan.wave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;

public class DailyAnalyticsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_daily_analytics);

        SettingsToolbar = findViewById(R.id.SettingsToolbar);
        setSupportActionBar(SettingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Today Analytics");


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

        anyChartView = findViewById(R.id.anyChartView);

        getTotalWeekPersonalExpenses();
        getTotalWeekTransportExpenses();
        getTotalWeekEntertainmentExpenses();
        getTotalWeekFoodExpenses();
        getTotalWeekOtherExpenses();
        getTotalDaySpending();

    }

    private void getTotalWeekPersonalExpenses(){
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Personal"+date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalAmount = 0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsPersonalAmount.setText("Spent "+totalAmount);
                    }
                    personalRef.child("dayPers").setValue(totalAmount);
                }else{
                    relativeLayoutPersonal.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekTransportExpenses(){
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Transport"+date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalAmount = 0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsTransportAmount.setText("Spent "+totalAmount);
                    }
                    personalRef.child("dayTrans").setValue(totalAmount);
                }else{
                    relativeLayoutTransport.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekEntertainmentExpenses(){
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Entertainment"+date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalAmount = 0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsEntertainmentAmount.setText("Spent "+totalAmount);
                    }
                    personalRef.child("dayEnt").setValue(totalAmount);
                }else{
                    relativeLayoutEntertainment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekFoodExpenses(){
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Food"+date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalAmount = 0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsFoodAmount.setText("Spent "+totalAmount);
                    }
                    personalRef.child("dayFood").setValue(totalAmount);
                }else{
                    relativeLayoutFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getTotalWeekOtherExpenses(){
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Other"+date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int totalAmount = 0;
                    for(DataSnapshot ds : snapshot.getChildren()){
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsOtherAmount.setText("Spent "+totalAmount);
                    }
                    personalRef.child("dayOther").setValue(totalAmount);
                }else{
                    relativeLayoutOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalDaySpending(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);

        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>)ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                    }
                    totalBudgetAmountTextView.setText("Total day's spending: € "+totalAmount);
                    monthSpendAmount.setText("Total Spent: € "+totalAmount);
                }else {
                    totalBudgetAmountTextView.setText("No money spent today");
                    anyChartView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //load graph
    private void loadGraph(){
        personalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    int persTotal;
                    if (snapshot.hasChild("dayPers")) {
                        persTotal = Integer.parseInt(snapshot.child("dayPers").getValue().toString());
                    } else {
                        persTotal = 0;
                    }

                    int transTotal;
                    if (snapshot.hasChild("dayTrans")) {
                        transTotal = Integer.parseInt(snapshot.child("dayTrans").getValue().toString());
                    } else {
                        transTotal = 0;
                    }

                    int EnterTotal;
                    if (snapshot.hasChild("dayEnter")) {
                        EnterTotal = Integer.parseInt(snapshot.child("dayEnter").getValue().toString());
                    } else {
                        EnterTotal = 0;
                    }

                    int FoodTotal;
                    if (snapshot.hasChild("dayFood")) {
                        FoodTotal = Integer.parseInt(snapshot.child("dayFood").getValue().toString());
                    } else {
                        FoodTotal = 0;
                    }

                    int OtherTotal;
                    if (snapshot.hasChild("dayOther")) {
                        OtherTotal = Integer.parseInt(snapshot.child("dayOther").getValue().toString());
                    } else {
                        OtherTotal = 0;
                    }

                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Personal", persTotal));
                    data.add(new ValueDataEntry("Transport", transTotal));
                    data.add(new ValueDataEntry("Entertainment", EnterTotal));
                    data.add(new ValueDataEntry("Food", FoodTotal));
                    data.add(new ValueDataEntry("Other", OtherTotal));

                    pie.data(data);

                    pie.title("Daily Analytics");

                    pie.labels().position("outside");

                    pie.legend().title().enabled(true);
                    pie.legend().title()
                            .text("Items spent on")
                            .padding(0d, 0d, 10d, 0d);

                    pie.legend()
                            .position("center-bottom")
                            .itemsLayout(LegendLayout.HORIZONTAL)
                            .align(Align.CENTER);

                    anyChartView.setChart(pie);
                }
                    else{
                    Toast.makeText(DailyAnalyticsActivity.this, "Child does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setStatusAndImageResource(){
        personalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    float persTotal;
                    if (snapshot.hasChild("dayPers")) {
                        persTotal = Integer.parseInt(snapshot.child("dayPers").getValue().toString());
                    } else {
                        persTotal = 0;
                    }

                    float transTotal;
                    if (snapshot.hasChild("dayTrans")) {
                        transTotal = Integer.parseInt(snapshot.child("dayTrans").getValue().toString());
                    } else {
                        transTotal = 0;
                    }

                    float EnterTotal;
                    if (snapshot.hasChild("dayEnter")) {
                        EnterTotal = Integer.parseInt(snapshot.child("dayEnter").getValue().toString());
                    } else {
                        EnterTotal = 0;
                    }

                    float FoodTotal;
                    if (snapshot.hasChild("dayFood")) {
                        FoodTotal = Integer.parseInt(snapshot.child("dayFood").getValue().toString());
                    } else {
                        FoodTotal = 0;
                    }

                    float OtherTotal;
                    if (snapshot.hasChild("dayOther")) {
                        OtherTotal = Integer.parseInt(snapshot.child("dayOther").getValue().toString());
                    } else {
                        OtherTotal = 0;
                    }

                    float monthTotalSpentAmount;
                    if (snapshot.hasChild("today")) {
                        monthTotalSpentAmount = Integer.parseInt(snapshot.child("today").getValue().toString());
                    } else {
                        monthTotalSpentAmount = 0;
                    }

                    //get ratios
                    float persRatio;
                    if (snapshot.hasChild("dayPersRatio")) {
                        persRatio = Integer.parseInt(snapshot.child("dayPersRatio").getValue().toString());
                    } else {
                        persRatio = 0;
                    }

                    float transRatio;
                    if (snapshot.hasChild("dayTransRatio")) {
                        transRatio = Integer.parseInt(snapshot.child("dayTransRatio").getValue().toString());
                    } else {
                        transRatio = 0;
                    }

                    float enterRatio;
                    if (snapshot.hasChild("dayEnterRatio")) {
                        enterRatio = Integer.parseInt(snapshot.child("dayEnterRatio").getValue().toString());
                    } else {
                        enterRatio = 0;
                    }

                    float foodRatio;
                    if (snapshot.hasChild("dayFoodRatio")) {
                        foodRatio = Integer.parseInt(snapshot.child("dayFoodRatio").getValue().toString());
                    } else {
                        foodRatio = 0;
                    }

                    float otherRatio;
                    if (snapshot.hasChild("dayOtherRatio")) {
                        otherRatio = Integer.parseInt(snapshot.child("dayOtherRatio").getValue().toString());
                    } else {
                        otherRatio = 0;
                    }

                    float monthTotalSpentAmountRatio;
                    if (snapshot.hasChild("dailyBudget")) {
                        monthTotalSpentAmountRatio = Integer.parseInt(snapshot.child("dailyBudget").getValue().toString());
                    } else {
                        monthTotalSpentAmountRatio = 0;
                    }


                    float monthPercent = (monthTotalSpentAmount / monthTotalSpentAmountRatio) * 100;
                    if (monthPercent < 50) {
                        monthRatioSpending.setText(monthPercent + " %" + " used of " + monthTotalSpentAmountRatio + ". Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.green);
                    } else if (monthPercent >= 50 && monthPercent < 100) {
                        monthRatioSpending.setText(monthPercent + " %" + " used of " + monthTotalSpentAmountRatio + ". Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.brown);
                    } else {
                        monthRatioSpending.setText(monthPercent + " %" + " used of " + monthTotalSpentAmountRatio + ". Status:");
                        monthRatioSpending_Image.setImageResource(R.drawable.red);
                    }

                    float personalPercent = (persTotal / persRatio) * 100;
                    if (personalPercent < 50) {
                        progress_ratio_personal.setText(personalPercent + " %" + " used of " + persRatio + ". Status:");
                        status_image_personal.setImageResource(R.drawable.green);
                    } else if (personalPercent >= 50 && personalPercent < 100) {
                        progress_ratio_personal.setText(personalPercent + " %" + " used of " + persRatio + ". Status:");
                        status_image_personal.setImageResource(R.drawable.brown);
                    } else {
                        progress_ratio_personal.setText(personalPercent + " %" + " used of " + persRatio + ". Status:");
                        status_image_personal.setImageResource(R.drawable.red);
                    }

                    float transportPercent = (transTotal / transRatio) * 100;
                    if (transportPercent < 50) {
                        progress_ratio_transport.setText(transportPercent + " %" + " used of " + transRatio + ". Status:");
                        status_image_transport.setImageResource(R.drawable.green);
                    } else if (transportPercent >= 50 && transportPercent < 100) {
                        progress_ratio_transport.setText(transportPercent + " %" + " used of " + transRatio + ". Status:");
                        status_image_transport.setImageResource(R.drawable.brown);
                    } else {
                        progress_ratio_transport.setText(transportPercent + " %" + " used of " + transRatio + ". Status:");
                        status_image_transport.setImageResource(R.drawable.red);
                    }

                    float entertainmentPercent = (EnterTotal / enterRatio) * 100;
                    if (entertainmentPercent < 50) {
                        progress_ratio_entertainment.setText(entertainmentPercent + " %" + " used of " + enterRatio + ". Status:");
                        status_image_entertainment.setImageResource(R.drawable.green);
                    } else if (entertainmentPercent >= 50 && entertainmentPercent < 100) {
                        progress_ratio_entertainment.setText(entertainmentPercent + " %" + " used of " + enterRatio + ". Status:");
                        status_image_entertainment.setImageResource(R.drawable.brown);
                    } else {
                        progress_ratio_entertainment.setText(entertainmentPercent + " %" + " used of " + enterRatio + ". Status:");
                        status_image_entertainment.setImageResource(R.drawable.red);
                    }

                    float foodPercent = (FoodTotal / foodRatio) * 100;
                    if (foodPercent < 50) {
                        progress_ratio_food.setText(foodPercent + " %" + " used of " + foodRatio + ". Status:");
                        status_image_food.setImageResource(R.drawable.green);
                    } else if (foodPercent >= 50 && foodPercent < 100) {
                        progress_ratio_food.setText(foodPercent + " %" + " used of " + foodRatio + ". Status:");
                        status_image_food.setImageResource(R.drawable.brown);
                    } else {
                        progress_ratio_food.setText(foodPercent + " %" + " used of " + foodRatio + ". Status:");
                        status_image_food.setImageResource(R.drawable.red);
                    }

                    float otherPercent = (OtherTotal / otherRatio) * 100;
                    if (otherPercent < 50) {
                        progress_ratio_other.setText(otherPercent + " %" + " used of " + otherRatio + ". Status:");
                        status_image_other.setImageResource(R.drawable.green);
                    } else if (otherPercent >= 50 && otherPercent < 100) {
                        progress_ratio_other.setText(otherPercent + " %" + " used of " + otherRatio + ". Status:");
                        status_image_other.setImageResource(R.drawable.brown);
                    } else {
                        progress_ratio_other.setText(otherPercent + " %" + " used of " + otherRatio + ". Status:");
                        status_image_other.setImageResource(R.drawable.red);
                    }
                } else {
                    Toast.makeText(DailyAnalyticsActivity.this, "setStatusAndImageResource Errors", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}