package com.example.maikhoi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private Toast toast;

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
                    toast = Toast.makeText(getApplicationContext(), "You did not enter an address", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                else if(zipInput.matches("")){
                    toast = Toast.makeText(getApplicationContext(), "You did not enter a zip code", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                else if(cityInput.matches("")){
                    toast = Toast.makeText(getApplicationContext(), "You did not enter a city", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                else if(stateInput.matches("")){
                    toast = Toast.makeText(getApplicationContext(), "You did not enter a state", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                    return;
                }
                ENDPOINT = new StringBuilder().append("https://us-street.api.smartystreets.com/street-address?auth-id=9e67ba70-9f50-b45a-2d0e-5b94f786c803&auth-token=weW759b91nUH1KyjpUCG").append("&street=").append(addressInput).append("&city=")
                        .append(cityInput).append("&state=").append(stateInput).append("&zipcode=").append(zipInput).toString();
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


            if(!response.equalsIgnoreCase("[]")){
                Resp[] address = gson.fromJson(response, Resp[].class);
                result.setTextColor(Color.GREEN);
                result.setText("Valid Address:\n" + address[0].components.primary_number + " " +  address[0].components.street_name + " " +  address[0].components.street_suffix + "\n" +
                        address[0].components.city_name + "\n" +
                        address[0].components.state_abbreviation + "\n" +
                        address[0].components.zipcode + "\n");
            }else{
                result.setTextColor(Color.RED);
                result.setText("Address is not valid");
            }

            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
    private final Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),"Oops, Something went wrong",Toast.LENGTH_SHORT).show();
        }
    };



}
