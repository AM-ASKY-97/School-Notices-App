package com.dharussalam.schoolnoticesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import cn.pedant.SweetAlert.SweetAlertDialog;
public class ResetPasswordActivity extends AppCompatActivity {
    Button btnResetPassword; EditText mEmail;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        mEmail = findViewById(R.id.email);
        //Validation For email Valid Pattern
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        fAuth = FirebaseAuth.getInstance();
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = mEmail.getText().toString();
                //Validation For email
                if(TextUtils.isEmpty(userEmail)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                //Validation For email Valid Pattern
                if (!userEmail.matches(emailPattern)) {
                    mEmail.setError("Invalid email address.");
                    return;
                }

                //Send Password Reset Email
                fAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            AlertUtils.showAlert(ResetPasswordActivity.this, "Good Job!", "Check Your Email");
                        }else {
                            AlertUtils.showAlert(ResetPasswordActivity.this, "Oops...", "Unable to send, failed");
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