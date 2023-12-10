package com.dharussalam.schoolnoticesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class DashboardActivity extends AppCompatActivity {

    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        userName = findViewById(R.id.txtAdminName);

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
                userName.setText(value.getString("uName"));
            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    return true;
                case R.id.bottom_notification:
                    startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_person:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        });
    }

    public void goMaps(View view){
        Intent intentDashboard = new Intent(this, MapsActivity.class);
        startActivity(intentDashboard);
    }

    public void goToAssignments(View view){
        Intent intentAssigments = new Intent(this, AssignmentsActivity.class);
        startActivity(intentAssigments);
    }

    public void goToAttendance(View view){
        Intent intentAttendance = new Intent(this, AttendanceAvtivity.class);
        startActivity(intentAttendance);
    }

    public void goToExam(View view){
        Intent intentExam = new Intent(this, ExamActivity.class);
        startActivity(intentExam);
    }

    public void goToGallery(View view){
        Intent intentGallery = new Intent(this, GalleryActivity.class);
        startActivity(intentGallery);
    }

    public void goToResult(View view){
        Intent intentResultActivity = new Intent(this, ResultActivity.class);
        startActivity(intentResultActivity);
    }
}