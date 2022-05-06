package com.alan.wave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class    paymentActivity extends AppCompatActivity {
    View paymentCardView, savingsCardView, paymentBtn, settingsCardView;

    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration paypalconfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);

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

        paymentBtn = findViewById(R.id.paymentBtn);

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
        startService(intent);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaypalPaymentsMethod();
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

    private void PaypalPaymentsMethod() {

        PayPalPayment payment = new PayPalPayment(new BigDecimal(100), "USD"
        ,"Test Payment", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class); //payment activity is customly created by paypal dep
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(requestCode == PAYPAL_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}


