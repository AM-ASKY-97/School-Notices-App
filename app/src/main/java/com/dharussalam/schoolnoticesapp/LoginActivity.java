package com.dharussalam.schoolnoticesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import cn.pedant.SweetAlert.SweetAlertDialog;
public class LoginActivity extends AppCompatActivity {
    EditText mEmail, mPassword; Button btnLogin;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edEmail);
        mPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //Validation For Email Valid Pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        fAuth = FirebaseAuth.getInstance();

        //Progress Bar
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait...");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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
                progressDialog.show();
                //Sign In With Email And Password
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if(task.isSuccessful()){
                            if(fAuth.getCurrentUser().isEmailVerified()){
                                AlertUtils.showSuccessAlert(LoginActivity.this, findViewById(android.R.id.content), "successfully login.");
                                        //Start New Activity
                                checkUserAccessLevel();
                            }else {
                                AlertUtils.showAlert(LoginActivity.this, "Alert", "Please verify your email id");
                            }
                        }else {
                            AlertUtils.showAlert(LoginActivity.this, "Alert", "Error !");
                        }
                    }
                });
            }
        });
    }

    public void clickSignUp(View view){
        Intent intentRegistrationPage = new Intent(this, RegistrationActivity.class);
        startActivity(intentRegistrationPage);
    }

    public void btnForgotPassword(View view){
        Intent intentForgotPasswordPage = new Intent(this, ResetPasswordActivity.class);
        startActivity(intentForgotPasswordPage);
    }

    public void checkUserAccessLevel()
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String userType = documentSnapshot.getString("role");
                    if (userType != null) {
                        // Check the user type and perform actions accordingly
                        if (userType.equals("1")) {
                            startActivity(new Intent(getApplicationContext(), AdminDashboardActivity.class));
                        }else if (userType.equals("0")){
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }
                    }
                } else {
                    // User document does not exist
                }
            }).addOnFailureListener(e -> {
                // Error retrieving user information
            });
        }
    }
}