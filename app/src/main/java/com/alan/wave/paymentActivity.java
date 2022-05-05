package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class    paymentActivity extends AppCompatActivity {
    View paymentCardView, savingsCardView, cryptoCardView, settingsCardView;

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
        //when user clicks savings on card view
        savingsCardView = findViewById(R.id.savingsFeature);

        savingsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, savingsActivity.class));
            }
        });

        cryptoCardView = findViewById(R.id.cryptoFeature);

        cryptoCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(paymentActivity.this, cryptoActivity.class));
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


