package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityBoostRequestBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoostRequestActivity extends AppCompatActivity {
    private ActivityBoostRequestBinding binding;
    private AutoCompleteTextView serviceTypeDropdown;
    private AutoCompleteTextView paymentMethodDropdown;
    private AutoCompleteTextView boostersDropdown;
    private List<String> selectedBooster;
    private ArrayAdapter<String> boostersAdapter;
    private EditText durationInput;
    private EditText priceInput;
    private EditText messageInput;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoostRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Service Type Dropdown
        serviceTypeDropdown = findViewById(R.id.serviceTypeDropdown);
        String[] serviceTypeOptions = {"Grind", "Rank"};
        ArrayAdapter<String> serviceTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, serviceTypeOptions);
        serviceTypeDropdown.setAdapter(serviceTypeAdapter);

        // Payment Method Dropdown
        paymentMethodDropdown = findViewById(R.id.paymentMethodDropdown);
        String[] paymentMethodOptions = {"GCash", "Bank Transfer", "Card"};
        ArrayAdapter<String> paymentMethodAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, paymentMethodOptions);
        paymentMethodDropdown.setAdapter(paymentMethodAdapter);

        durationInput = findViewById(R.id.durationInput);
        priceInput = findViewById(R.id.priceInput);
        messageInput = findViewById(R.id.messageInput);
        gameId = getIntent().getStringExtra("gameId");

        boostersDropdown = findViewById(R.id.boostersDropdown);
        selectedBooster = new ArrayList<>();

        // Fetch boosters from Firebase
        fetchBoostersFromFirebase();

        boostersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, selectedBooster);
        boostersDropdown.setAdapter(boostersAdapter);

        // Submit Button
        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            // Handle submit button click
            String selectedServiceType = serviceTypeDropdown.getText().toString();
            String selectedPaymentMethod = paymentMethodDropdown.getText().toString();
            String selectedBooster = boostersDropdown.getText().toString();

            // You can add more logic here to get the values from other fields
            // and perform actions like sending the data to a server.

            // Example: Show a Toast message
            String message = "Boost Request Submitted!";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Cancel Button
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            // Handle cancel button click
            finish(); // Close the activity
        });
    }

    private void fetchBoostersFromFirebase() {
        Log.d("BoostRequestActivity", "fetchBoostersFromFirebase() called"); // Add this line
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        // Query only for users who reached booster level
        Query query = usersRef.orderByChild("accountLevel").startAt(4);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("BoostRequestActivity", "onDataChange() called"); // Add this line
                selectedBooster.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Log.d("BoostRequestActivity", "User found: " + userSnapshot.getKey()); // Add this line
                    // Get the user's account level
                    Long accountLevel = userSnapshot.child("accountLevel").getValue(Long.class);
                    // Check if accountLevel is not null and is 4 or greater
                    if (accountLevel != null && accountLevel >= 4) {
                        // Get the user's email
                        String email = userSnapshot.child("email").getValue(String.class);
                        if (email != null) {
                            selectedBooster.add(email);
                        }
                    }
                }
                boostersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
                Log.e("BoostRequestActivity", "Error fetching boosters", error.toException());
            }
        });
    }
}