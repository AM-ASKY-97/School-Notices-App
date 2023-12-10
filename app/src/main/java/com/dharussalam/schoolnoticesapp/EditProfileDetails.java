package com.dharussalam.schoolnoticesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EditProfileDetails extends AppCompatActivity {

    EditText getPhone, getUser;
    Button btnUserEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_details);

        getPhone =(EditText) findViewById(R.id.edPhone);
        getUser =(EditText) findViewById(R.id.edUser);
        btnUserEdit = (Button) findViewById(R.id.btnUserEdit);

        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        String userID;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                getUser.setText(value.getString("uName"));
                getPhone.setText(value.getString("phone"));
            }
        });

        btnUserEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef = fStore.collection("users").document(userID);
                Map<String, Object> updates = new HashMap<>();
                updates.put("uName", getUser.getText().toString());
                updates.put("phone", getPhone.getText().toString());

                docRef.update(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                AlertUtils.showSuccessAlert(EditProfileDetails.this, findViewById(android.R.id.content), "Successfully Updated");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

    }
}