package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class    paymentActivity extends AppCompatActivity {
    View paymentCardView, savingsCardView, paymentBtn, settingsCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentCardView = findViewById(R.id.googlePay);

        paymentCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, GooglePaymentActivity.class));
            }
        });
        //when user clicks home on card view
        savingsCardView = findViewById(R.id.homePageFeature);

        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, featuresActivity.class));
            }
        });

        paymentBtn = findViewById(R.id.paymentBtn);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, PayPalActivity.class));
            }
        });

        settingsCardView = findViewById(R.id.purchaseCardFeature);

        settingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, cardActivity.class));
            }
        });
    }
}