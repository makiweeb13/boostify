package com.example.gamelist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dashboard.R;
import com.example.user.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView returnToLogin;
    FirebaseAuth mAuth;
    TextInputEditText editTextEmail;
    Button sendEmailButton;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        returnToLogin = findViewById(R.id.returnToLogin);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        sendEmailButton = findViewById(R.id.sendEmailButton);


        returnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    sendEmailButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            str = editTextEmail.getText().toString();
            if (!TextUtils.isEmpty(str)) {
                    ResetPassword();
            } else{
                editTextEmail.setError("Enter Email!");
            }
        }
    });

    }
    private void ResetPassword(){

        mAuth.sendPasswordResetEmail(str).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPasswordActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
