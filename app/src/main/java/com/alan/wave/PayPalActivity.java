package com.alan.wave;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class PayPalActivity extends AppCompatActivity {


    EditText etAmount, etRecipient;
    Button btPay;

    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration paypalconfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PaypalClientID.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        etAmount = findViewById(R.id.et_amount);
        etRecipient = findViewById(R.id.et_recipient);
        btPay = findViewById(R.id.bt_Pay);


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
        startService(intent);

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaypalPaymentsMethod();
            }
        });
    }


    private void PaypalPaymentsMethod () {


        String dRecipient = etRecipient.getText().toString();
        String sAmount = etAmount.getText().toString();
        int amount =Integer.parseInt(sAmount);

        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "EUR"
                , dRecipient, PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class); //payment activity is created by paypal dependency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, paypalconfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }


    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy () {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}