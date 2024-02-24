package com.example.formation1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SignInActivity extends AppCompatActivity {
    private TextView goToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        goToSignUp = findViewById(R.id.goToSignUp);
        goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });
    }
}