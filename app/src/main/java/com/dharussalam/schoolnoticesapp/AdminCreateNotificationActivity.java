package com.dharussalam.schoolnoticesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class AdminCreateNotificationActivity extends AppCompatActivity {

    EditText tittle, date, description;

    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_notification);

        tittle = (EditText) findViewById(R.id.edTittle);
        description = (EditText) findViewById(R.id.edDescribtion);

        btnAdd = (Button) findViewById(R.id.btnSave);
        btnBack = (Button) findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eTittle = tittle.getText().toString().trim();
                String eDescription = description.getText().toString().trim();

                if(TextUtils.isEmpty(eTittle) || TextUtils.isEmpty(eDescription))
                {
                    AlertUtils.showAlert(AdminCreateNotificationActivity.this, "Alert", "All filed is required.");
                }
                else {
                    insertData();
                    clearAll();
                }
            }
        });

    }

    private void insertData()
    {
        Map<String, Object> map = new HashMap<>();
        map.put("tittle", tittle.getText().toString());
        map.put("description", description.getText().toString());
        map.put("timestamp", ServerValue.TIMESTAMP);

        FirebaseDatabase.getInstance().getReference().child("notification").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        AlertUtils.showSuccessAlert(AdminCreateNotificationActivity.this, findViewById(android.R.id.content), "Success! Data saved successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminCreateNotificationActivity.this, "Error While Inseting", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll()
    {
        tittle.setText("");
        description.setText("");
    }
}