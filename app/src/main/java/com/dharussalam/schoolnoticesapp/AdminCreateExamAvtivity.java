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

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminCreateExamAvtivity extends AppCompatActivity {

    EditText name, date, time;

    Button btnAdd, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_exam_avtivity);

        name = (EditText) findViewById(R.id.edExamName);
        date = (EditText) findViewById(R.id.edExamDate);
        time = (EditText) findViewById(R.id.edExamTime);

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
                String eName = name.getText().toString().trim();
                String eDate = date.getText().toString().trim();
                String eTime = time.getText().toString().trim();

                if(TextUtils.isEmpty(eName) || TextUtils.isEmpty(eDate) || TextUtils.isEmpty(eTime))
                {
                    AlertUtils.showAlert(AdminCreateExamAvtivity.this, "Alert", "All filed is required.");
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
        map.put("name", name.getText().toString());
        map.put("date", date.getText().toString());
        map.put("time", time.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("exam").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        AlertUtils.showSuccessAlert(AdminCreateExamAvtivity.this, findViewById(android.R.id.content), "Success! Data saved successfully.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminCreateExamAvtivity.this, "Error While Inseting", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void clearAll()
    {
        name.setText("");
        time.setText("");
        date.setText("");
    }
}