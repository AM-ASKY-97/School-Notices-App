package com.dharussalam.schoolnoticesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class ExamActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    TextView txtBack;
    UserExamAdapter userExamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new ExamActivity.WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<AdminExamModel> options =
                new FirebaseRecyclerOptions.Builder<AdminExamModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("exam"), AdminExamModel.class)
                        .build();

        userExamAdapter = new UserExamAdapter(options);
        recyclerView.setAdapter(userExamAdapter);

        txtBack = (TextView) findViewById(R.id.txtBack);
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        userExamAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userExamAdapter.stopListening();
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