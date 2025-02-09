package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BoostRequestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boost_request);

        // Service Type Dropdown
        AutoCompleteTextView serviceTypeDropdown = findViewById(R.id.serviceTypeDropdown);
        String[] serviceTypeOptions = {"Grind", "Rank"};
        ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, serviceTypeOptions);
        serviceTypeDropdown.setAdapter(serviceTypeAdapter);

        // Payment Method Dropdown
        AutoCompleteTextView paymentMethodDropdown = findViewById(R.id.paymentMethodDropdown);
        String[] paymentMethodOptions = {"GCash", "Banks"};
        ArrayAdapter<String> paymentMethodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, paymentMethodOptions);
        paymentMethodDropdown.setAdapter(paymentMethodAdapter);

        // Submit Button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            // Handle submit button click
            String selectedServiceType = serviceTypeDropdown.getText().toString();
            String selectedPaymentMethod = paymentMethodDropdown.getText().toString();

            // You can add more logic here to get the values from other fields
            // and perform actions like sending the data to a server.

            // Example: Show a Toast message
            String message = "Submitted! Service: " + selectedServiceType + ", Payment: " + selectedPaymentMethod;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Cancel Button
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            // Handle cancel button click
            finish(); // Close the activity
        });
    }
}