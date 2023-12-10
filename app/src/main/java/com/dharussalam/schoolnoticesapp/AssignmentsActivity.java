package com.dharussalam.schoolnoticesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AssignmentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdminAssigmentAdapter adminAssigmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new AssignmentsActivity.WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<AdminAssigmentModel> options =
                new FirebaseRecyclerOptions.Builder<AdminAssigmentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("assigments"), AdminAssigmentModel.class)
                        .build();

        adminAssigmentAdapter = new AdminAssigmentAdapter(options);
        recyclerView.setAdapter(adminAssigmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adminAssigmentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adminAssigmentAdapter.stopListening();
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