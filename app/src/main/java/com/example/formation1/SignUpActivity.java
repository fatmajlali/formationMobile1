package com.example.formation1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Models.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullName, email, cin, phone, password;
    private Button btnSignUp;
    private TextView goToSignIn;
    private String fullnameString, emailString, cinString,phoneString, passwordString;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.fullNameSignUp);
        email = findViewById(R.id.emailSignUp);
        cin = findViewById(R.id.cinSignUp);
        phone = findViewById(R.id.phoneSignUp);
        password = findViewById(R.id.passwordSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        goToSignIn = findViewById(R.id.gotoSignIn);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        
        goToSignIn.setOnClickListener(v ->{
            startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        });
        btnSignUp.setOnClickListener(v ->{
            if(validate()){
                progressDialog.setMessage("Please Wait..!");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    sendEmailVerification();

                } else{
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                });
        }
        });

    }

    private void sendEmailVerification() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user !=null){
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sendUserData();
                    Toast.makeText(this,"Registration done! Please verify your Email!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    firebaseAuth.signOut();
                    finish();
                }else {
                    Toast.makeText(this,"Failed to send email verification !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendUserData() {
        DatabaseReference myRef = firebaseDatabase.getReference("Users");
        User user = new User(fullnameString, emailString, cinString, phoneString);
        myRef.child(""+firebaseAuth.getUid()).setValue(user);
    }

    private boolean validate() {
        boolean result = false;
        fullnameString = fullName.getText().toString().trim();
        emailString = email.getText().toString().trim();
        cinString = cin.getText().toString().trim();
        phoneString = phone.getText().toString().trim();
        passwordString = password.getText().toString().trim();

        if (fullnameString.length() < 7) {
            fullName.setError("fullName is invalid");
        }else if(!isValidEmail(emailString)){
            email.setError("email is Invalid!");

        }else if (cin.length() !=8){
            cin.setError("CIN is Invalid!");
        }else if (phoneString.length() !=8){
            phone.setError("Phone is Invalid");
        }else if(passwordString.length() <5){
            password.setError("Password is Invalid!");
        }else {
            result = true;
        }

        return result;
    }

    private boolean isValidEmail(String emailString){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailString);
        return matcher.matches();
    }

}