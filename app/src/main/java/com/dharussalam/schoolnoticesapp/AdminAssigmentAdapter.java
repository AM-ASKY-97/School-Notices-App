package com.dharussalam.schoolnoticesapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AdminAssigmentAdapter extends FirebaseRecyclerAdapter<AdminAssigmentModel, AdminAssigmentAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminAssigmentAdapter(@NonNull FirebaseRecyclerOptions<AdminAssigmentModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull AdminAssigmentModel model) {
        holder.name.setText(model.getTittle());
        holder.date.setText(model.getDate());
        holder.quiz.setText(model.getQuiz());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.name.getContext(), viewpdf.class);
                intent.putExtra("tittle", model.getTittle());
                intent.putExtra("fileurl", model.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.name.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_assigment,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView name, date, quiz;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txtTittle);
            date = (TextView) itemView.findViewById(R.id.txtDue);
            quiz = (TextView) itemView.findViewById(R.id.txtQuiz);
        }
    }
}
