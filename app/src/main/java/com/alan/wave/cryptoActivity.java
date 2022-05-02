package com.alan.wave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class cryptoActivity extends AppCompatActivity {

    private EditText searchET;
    private RecyclerView currenciesRV;
    private ProgressBar PB;
    private ArrayList<CurrencyRVModal> currencyRVModalArrayList;
    private CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);

        searchET = findViewById(R.id.EditSearch);
        currenciesRV = findViewById(R.id.RVCurrencies);
        PB = findViewById(R.id.PBloading);
        currencyRVModalArrayList = new ArrayList<>();
        currencyAdapter = new CurrencyAdapter(currencyRVModalArrayList, this);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        currenciesRV.setAdapter(currencyAdapter);
        getCurrencyData();

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterCurrencies(editable.toString());
            }
        });

    }

    private void filterCurrencies(String currency){ //passing string as currency
        ArrayList<CurrencyRVModal> filteredList = new ArrayList<>(); //create arrayList
        for(CurrencyRVModal item : currencyRVModalArrayList){
            if(item.getName().toLowerCase().contains(currency.toLowerCase(Locale.ROOT))){
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){ //check if filter list is empty then no currency found
            Toast.makeText(this, "No Currency found", Toast.LENGTH_SHORT).show();
        }else{
            currencyAdapter.filterList(filteredList); //else call filteredList method from currencyRVAdapter class
        }
    }

    private void getCurrencyData(){ //function to fetch the data from coinmarketcap using their API
        PB.setVisibility(View.VISIBLE);  //makes loading bar visible
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"; //url for cmc API
        RequestQueue requestQueue = Volley.newRequestQueue(this); //volley is an HTTP library that makes networking for android apps easier
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() { //get request
            @Override
            public void onResponse(JSONObject response) {
                PB.setVisibility(View.GONE); //makes loading bar invisible
                try {   //handles error exception
                    JSONArray dataArray = response.getJSONArray("data"); //calls data from json file
                    for(int i=0; i<dataArray.length();i++){ //gets all objects inside data array
                        JSONObject dataObj = dataArray.getJSONObject(i); //get data object
                        String name = dataObj.getString("name"); //gets name
                        String symbol = dataObj.getString("symbol"); //gets symbol
                        JSONObject quote = dataObj.getJSONObject("quote"); //go to quote json object
                        JSONObject USD = quote.getJSONObject("USD"); //-> go to USD json object
                        double price = USD.getDouble("price"); //gets price

                        currencyRVModalArrayList.add(new CurrencyRVModal(name,symbol,price));
                    }
                    currencyAdapter.notifyDataSetChanged(); //notify data has been changed in array list
                }catch (JSONException e){
                 e.printStackTrace();
                 Toast.makeText(cryptoActivity.this, "Failed to extract", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //handles errors
            PB.setVisibility(View.GONE);
                Toast.makeText(cryptoActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>(); //pass headers using key value pair
                headers.put("X-CMC_PRO_API_KEY","db9dec82-25d7-4435-93e4-6c08fb483c6f");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}