package com.dharussalam.schoolnoticesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdminExamMainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminExamAdapter adminExamAdapter;

    Button btnExamAdd;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_exam_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new AdminExamMainActivity.WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<AdminExamModel> options =
                new FirebaseRecyclerOptions.Builder<AdminExamModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exam"), AdminExamModel.class)
                        .build();

        adminExamAdapter = new AdminExamAdapter(options);
        recyclerView.setAdapter(adminExamAdapter);

        btnExamAdd = (Button) findViewById(R.id.btnAddExam);
        btnExamAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenCreateNew = new Intent(getApplicationContext(), AdminCreateExamAvtivity.class);
                startActivity(intenCreateNew);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adminExamAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminExamAdapter.stopListening();
    }

    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "meet a IOOBE in RecyclerView");
            }
        }
    }
}