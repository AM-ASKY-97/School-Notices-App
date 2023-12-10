package com.dharussalam.schoolnoticesapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
public class RegistrationActivity extends AppCompatActivity {
    EditText mUserName, mEmail, mPassword, mPhone; Button btnRegistration;
    FirebaseAuth fAuth; FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mUserName = findViewById(R.id.edUserName);
        mEmail = findViewById(R.id.edEmail);
        mPassword = findViewById(R.id.edPassword);
        mPhone = findViewById(R.id.edPhone);
        //Email Valid Pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        btnRegistration = findViewById(R.id.btnRegistration);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait...");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String userName = mUserName.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();

                //Validation For user Name
                if(TextUtils.isEmpty(userName)){
                    mUserName.setError("User Name is Required.");
                    return;
                }

                //Validation For Email
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                //Validation For email Valid Pattern
                if (!email.matches(emailPattern)) {
                    mEmail.setError("Invalid email address.");
                    return;
                }

                //Validation For Password
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                //Validation For Password Valid Pattern
                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters.");
                    return;
                }

                //Validation For Phone
                if(TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone Number is Required.");
                    return;
                }

                //Validation For Phone Number Valid Pattern
                if(phone.length() < 10){
                    mPhone.setError("Phone Number Must be 10 Characters.");
                    return;
                }

                //Validation For Phone Number Valid Pattern
                if(phone.length() > 10){
                    mPhone.setError("Phone number must be less than 10 characters.");
                    return;
                }
                progressDialog.show();

                //Create User With Email And Password
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if(task.isSuccessful()){
                            //Send Email Verification
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        AlertUtils.showAlert(RegistrationActivity.this, "Alert", "User registerd successfully, Please verify your email id");

                                        userID = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fStore.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("uName", userName);
                                        user.put("email", email);
                                        user.put("phone", phone);
                                        user.put("role", "0");
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                            }
                                        });
                                        //After create account clear text box
                                        mUserName.setText(""); mEmail.setText(""); mPassword.setText(""); mPhone.setText("");
                                    }else {
                                        AlertUtils.showAlert(RegistrationActivity.this, "Alert", "Error !" + task.getException().getMessage());
                                    }
                                }
                            });
                        }else {
                            AlertUtils.showAlert(RegistrationActivity.this, "Alert", "Error !" + task.getException().getMessage());
                        }
                    }
                });
            }
        });
    }

    public void backHome(View view){
        Intent intentLoginPage = new Intent(this, LoginActivity.class);
        startActivity(intentLoginPage);
    }
}