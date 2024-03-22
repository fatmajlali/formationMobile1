package com.example.formation1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private TextView goToSignUp, gotoForgetPass;

    private EditText email, password;
    private Button btnSignIn;
    private CheckBox rememberMe;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
private  String emailS,passwordS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        goToSignUp = findViewById(R.id.goToSignUp);
        gotoForgetPass = findViewById(R.id.goToForgetPassword);
        email = findViewById(R.id.emailSignIn);
        password = findViewById(R.id.passwordSignIn);
        btnSignIn = findViewById(R.id.btnSignIn);
        rememberMe = findViewById(R.id.rememberMe);


        firebaseAuth = firebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        boolean isChacked = preferences.getBoolean("remember",false);
        if (isChacked){
            startActivity(new Intent(SignInActivity.this,HomeActivity.class));
        }

        rememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isChecked()){

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("remember", true);
                editor.apply();

            }else if (!buttonView.isChecked()) {

                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("remember", false);
                editor.apply();
            }
        });
        btnSignIn.setOnClickListener(v->{
            progressDialog.setMessage("Please Wait...!");
            progressDialog.show();
            emailS = email.getText().toString().trim();
            passwordS = password.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(emailS, passwordS).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    checkEmailVerification();
                }else {
                    Toast.makeText(this,"Please verify that your data is correct",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                }
            });
        });



        goToSignUp.setOnClickListener(v -> {
            startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        });

        gotoForgetPass.setOnClickListener(v->{
            startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
        });
    }

    private void checkEmailVerification() {
        FirebaseUser logedUser = firebaseAuth.getCurrentUser();
        if (logedUser !=null){

            if(logedUser.isEmailVerified()){
                startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                progressDialog.dismiss();
                finish();

            }else {
                logedUser.sendEmailVerification();
                Toast.makeText(this,"Please Verify your Email Adress!", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }
}