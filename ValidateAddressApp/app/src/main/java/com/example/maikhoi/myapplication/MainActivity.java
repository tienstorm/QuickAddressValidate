package com.example.maikhoi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "Quick Address Validate";
    private EditText addressEditText, zipEditText, cityEditText, stateEditText;
    private TextView result;
    private String ENDPOINT;
    private RequestQueue requestQueue;
    private Button submit;
    private Gson gson;
    private String addressInput, zipInput, cityInput, stateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addressEditText = findViewById(R.id.editTextAddress);
        zipEditText = findViewById(R.id.editTextZip);
        cityEditText = findViewById(R.id.editTextCity);
        stateEditText = findViewById(R.id.editTextState);
        submit = findViewById(R.id.submit);
        result = findViewById(R.id.textView9);
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressInput = addressEditText.getText().toString();
                zipInput = zipEditText.getText().toString();
                cityInput = cityEditText.getText().toString();
                stateInput = stateEditText.getText().toString();

                if (addressInput.matches("")) {
                    Toast.makeText(getApplicationContext(), "You did not enter an address", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(zipInput.matches("")){
                    Toast.makeText(getApplicationContext(), "You did not enter a zip code", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(cityInput.matches("")){
                    Toast.makeText(getApplicationContext(), "You did not enter a city", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(stateInput.matches("")){
                    Toast.makeText(getApplicationContext(), "You did not enter a state", Toast.LENGTH_SHORT).show();
                    return;
                }
                ENDPOINT = new StringBuilder().append("http://apilayer.net/api/validate?access_key=20549b7e96811406c840caedf769e804").append("&address1=").append(addressInput).append("&postal_code=")
                        .append(zipInput).append("&locality=").append(cityInput).append("&country_code=").append("US").append("&region=").append(stateInput).toString();
                fetchAddress(ENDPOINT);
            }
        });
    }


    private void fetchAddress(String endpoint)
    {
        StringRequest request = new StringRequest(Request.Method.GET,endpoint,onLoaded,onError);
        requestQueue.add(request);
    }
    private final Response.Listener<String> onLoaded  = new Response.Listener<String>(){
        @Override
        public void onResponse(String response){
            Log.i("INFO",response);
            Address address = gson.fromJson(response,Address.class);
            if(address.validation_status.matches("valid")){
                result.setText("Address is valid");
            }else{
                result.setText("Address is not valid");
            }
        }
    };
    private final Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),"Oops, Something went wrong",Toast.LENGTH_SHORT).show();
        }
    };

}
