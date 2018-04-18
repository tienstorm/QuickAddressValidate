package com.example.maikhoi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Quick Address Validate";
    private EditText addressEditText, zipEditText, cityEditText, stateEditText;
    private TextView result;
    private String addressInput, zipInput, cityInput, stateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void sendMessage(View view){
        addressEditText = findViewById(R.id.editTextAddress);
        zipEditText = findViewById(R.id.editTextZip);
        cityEditText = findViewById(R.id.editTextCity);
        stateEditText = findViewById(R.id.editTextState);
        result = findViewById(R.id.textView9);

        addressInput = addressEditText.getText().toString();
        zipInput = zipEditText.getText().toString();
        cityInput = cityEditText.getText().toString();
        stateInput = stateEditText.getText().toString();

        if (addressInput.matches("")) {
            Toast.makeText(this, "You did not enter an address", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(zipInput.matches("")){
            Toast.makeText(this, "You did not enter a zip code", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(cityInput.matches("")){
            Toast.makeText(this, "You did not enter a city", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(stateInput.matches("")){
            Toast.makeText(this, "You did not enter a state", Toast.LENGTH_SHORT).show();
            return;
        }


        Log.i(TAG, "Submitted Text");
        Log.i(TAG, addressInput);
        Log.i(TAG, zipInput);
        Log.i(TAG, cityInput);
        Log.i(TAG, stateInput);


    }
}
