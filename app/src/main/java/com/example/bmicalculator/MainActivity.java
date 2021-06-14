package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText height, weight;
    TextView txtRes, txtInter;
    TextView linkTextView;
    Button btnCal, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = (EditText) findViewById(R.id.w);
        height = (EditText) findViewById(R.id.h);

        txtInter = (TextView) findViewById(R.id.txtinter);
        txtRes = (TextView) findViewById(R.id.txtres);

        btnCal = (Button) findViewById(R.id.btncal);
        btnReset = (Button) findViewById(R.id.btnreset);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strweight = weight.getText().toString();
                String strheight = height.getText().toString();

                if (strweight.equals("0")) {
                    weight.setError("Please Enter You Weight");
                    weight.requestFocus();
                    return;
                }
                if (strheight.equals("0")) {
                    height.setError("Please Enter Your Height");
                    height.requestFocus();
                    return;
                }
                if (strweight.equals("0.0")) {
                    weight.setError("Please Enter You Weight");
                    weight.requestFocus();
                    return;
                }
                if (strheight.equals("0.0")) {
                    height.setError("Please Enter Your Height");
                    height.requestFocus();
                    return;
                }
                float weight = Float.parseFloat(strweight);
                float height = Float.parseFloat(strheight) / 100;

                float bmiVal = calculateBMI(weight, height);

                txtInter.setText(interpreteBMI(bmiVal));
                txtRes.setText("BMI= " + bmiVal + " kg/m2");

            }

        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weight.setText(String.valueOf("0"));
                height.setText(String.valueOf("0"));
                txtInter.setText("Enter your details");
                txtRes.setText("BMI?");
            }
        });


    }


    // Fetch the stored data in onResume()
    // Because this is what will be called
    // when the app opens again
    @Override
    protected void onResume() {
        super.onResume();

        // Fetching the stored data
        // from the SharedPreference
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        float w = sh.getFloat("weight", 0.0f);
        float h = sh.getFloat("height", 0.0f);

        // Setting the fetched data
        // in the EditTexts
        weight.setText(String.valueOf(w));
        height.setText(String.valueOf(h));
    }

    // Store the data in the SharedPreference
    // in the onPause() method
    // When the user closes the application
    // onPause() will be called
    // and data will be stored
    @Override
    protected void onPause() {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref"
        // in private mode
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        // write all the data entered by the user in SharedPreference and apply
        myEdit.putFloat("weight", Float.parseFloat(weight.getText().toString()));
        myEdit.putFloat("height", Float.parseFloat(height.getText().toString()));
        myEdit.apply();
    }

    public float calculateBMI(float weight, float height) {
        return weight / (height * height);
    }

    public String interpreteBMI(float bmiValue) {
        if (bmiValue <= 18.4) {
            return "Underweight \n Malnutrition risk";
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            return "Normal weight \n Low risk";
        } else if (bmiValue >= 25 && bmiValue <= 29.9) {
            return "Overweight \n Enhanced risk";
        } else if (bmiValue >= 30 && bmiValue <= 34.9) {
            return "Moderately obese \n Medium risk";
        } else if (bmiValue >= 35 && bmiValue <= 39.9) {
            return "Severely obese \n High risk";
        } else if (bmiValue >= 40) {
            return "Very severely obese \n Very high risk";
        } else {
            return "Not correct info";
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about :
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);

    }

}