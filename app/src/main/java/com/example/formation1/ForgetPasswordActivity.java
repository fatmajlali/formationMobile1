package com.example.formation1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
private EditText email;
private Button btnForget,btnBack;

private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.emailForget);
        btnForget = findViewById(R.id.btnForget);
        btnBack = findViewById(R.id.btnBack);

        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnBack.setOnClickListener(v-> {
            startActivity(new Intent(ForgetPasswordActivity.this,SignInActivity.class));

        });
        btnForget.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait...!");
            progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this,"Password reset email sent!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ForgetPasswordActivity.this, SignInActivity.class));
                    finish();
                }else {
                Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                }

        });
    });
}
}