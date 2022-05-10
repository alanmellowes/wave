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

    private Toolbar settingsToolbar;

    private FirebaseAuth mAuth;
    private String onlineUserId = "";
    private DatabaseReference expensesRef, personalRef;

    private TextView totalBudgetAmountTextView, analyticsTransportAmount, analyticsFoodAmount, analyticsPersonalAmount, analyticsEntertainmentAmount, analyticsOtherAmount, monthSpentAmount;

    private RelativeLayout linearLayoutFood, linearLayoutTransport, linearLayoutFoodHouse, linearLayoutEntertainment, linearLayoutOther, linearLayoutAnalysis;

    private AnyChartView anyChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_analytics);

        settingsToolbar = findViewById(R.id.my_Feed_Toolbar);
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Today Analytics");


        mAuth = FirebaseAuth.getInstance();
        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        expensesRef = FirebaseDatabase.getInstance(strURL).getReference("expenses").child(onlineUserId);
        personalRef = FirebaseDatabase.getInstance(strURL).getReference("personal").child(onlineUserId);

        totalBudgetAmountTextView = findViewById(R.id.totalBudgetAmountTextView);

        //general
        monthSpentAmount = findViewById(R.id.monthSpendAmount);
        linearLayoutAnalysis = findViewById(R.id.linearLayoutAnalysis);

        //analytics views
        analyticsTransportAmount = findViewById(R.id.analyticsTransportAmount);
        analyticsFoodAmount = findViewById(R.id.analyticsFoodAmount);
        analyticsPersonalAmount = findViewById(R.id.analyticsTransportAmount);
        analyticsEntertainmentAmount = findViewById(R.id.analyticsEntertainmentAmount);
        analyticsOtherAmount = findViewById(R.id.analyticsOtherAmount);


        //recycler views
        linearLayoutTransport = findViewById(R.id.linearLayoutTransport);
        linearLayoutFood = findViewById(R.id.linearLayoutFood);
        linearLayoutFoodHouse = findViewById(R.id.linearLayoutFoodHouse);
        linearLayoutEntertainment = findViewById(R.id.linearLayoutEntertainment);
        linearLayoutOther = findViewById(R.id.linearLayoutOther);

        anyChartView = findViewById(R.id.anyChartView);


        getTotalWeekTransportExpenses();
        getTotalWeekFoodExpenses();
        getTotalWeekPersonal();
        getTotalWeekEntertainmentExpenses();
        getTotalWeekOtherExpenses();
        getTotalDaySpending();

        loadGraph();
    }

    private void getTotalWeekTransportExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Transport" + date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsTransportAmount.setText("Spent " + totalAmount);
                    }
                    personalRef.child("dayTrans").setValue(totalAmount);
                } else {
                    linearLayoutTransport.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekFoodExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Food" + date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsFoodAmount.setText("Spent " + totalAmount);
                    }
                    personalRef.child("dayFood").setValue(totalAmount);
                } else {
                    linearLayoutFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekPersonal() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "House Expenses" + date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsPersonalAmount.setText("Spent " + totalAmount);
                    }
                    personalRef.child("dayHouse").setValue(totalAmount);
                } else {
                    linearLayoutFoodHouse.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalWeekEntertainmentExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Entertainment" + date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsEntertainmentAmount.setText("Spent " + totalAmount);
                    }
                    personalRef.child("dayEnt").setValue(totalAmount);
                } else {
                    linearLayoutEntertainment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void getTotalWeekOtherExpenses() {
        MutableDateTime epoch = new MutableDateTime();
        epoch.setDate(0);
        DateTime now = new DateTime();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String itemNday = "Other" + date;

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("itemNday").equalTo(itemNday);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                        analyticsOtherAmount.setText("Spent " + totalAmount);
                    }
                    personalRef.child("dayOther").setValue(totalAmount);
                } else {
                    linearLayoutOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTotalDaySpending() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());

        String strURL = "https://wave-ccbfd-default-rtdb.europe-west1.firebasedatabase.app/";
        DatabaseReference reference = FirebaseDatabase.getInstance(strURL).getReference("expenses").child("mYetIoEY7hMEDhw15jt0epLsP0Z2");

        Query query = reference.orderByChild("date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    int totalAmount = 0;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Map<String, Object> map = (Map<String, Object>) ds.getValue();
                        Object total = map.get("amount");
                        int pTotal = Integer.parseInt(String.valueOf(total));
                        totalAmount += pTotal;
                    }
                    totalBudgetAmountTextView.setText("Total day's spending: € " + totalAmount);
                    monthSpentAmount.setText("Total Spent: € " + totalAmount);
                } else {
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
    private void loadGraph() {
        personalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    int traTotal;
                    if (snapshot.hasChild("dayTrans")) {
                        traTotal = Integer.parseInt(snapshot.child("dayTrans").getValue().toString());
                    } else {
                        traTotal = 0;
                    }

                    int foodTotal;
                    if (snapshot.hasChild("dayFood")) {
                        foodTotal = Integer.parseInt(snapshot.child("dayFood").getValue().toString());
                    } else {
                        foodTotal = 0;
                    }

                    int entTotal;
                    if (snapshot.hasChild("dayEnt")) {
                        entTotal = Integer.parseInt(snapshot.child("dayEnt").getValue().toString());
                    } else {
                        entTotal = 0;
                    }

                    int otherTotal;
                    if (snapshot.hasChild("dayOther")) {
                        otherTotal = Integer.parseInt(snapshot.child("dayOther").getValue().toString());
                    } else {
                        otherTotal = 0;
                    }

                    int persTotal;
                    if (snapshot.hasChild("dayPers")) {
                        persTotal = Integer.parseInt(snapshot.child("dayPers").getValue().toString());
                    } else {
                        persTotal = 0;
                    }


                    Pie pie = AnyChart.pie();
                    List<DataEntry> data = new ArrayList<>();
                    data.add(new ValueDataEntry("Transport", traTotal));
                    data.add(new ValueDataEntry("Food", foodTotal));
                    data.add(new ValueDataEntry("Entertainment", entTotal));
                    data.add(new ValueDataEntry("Other", otherTotal));

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
                } else {
                    Toast.makeText(DailyAnalyticsActivity.this, "Child does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}