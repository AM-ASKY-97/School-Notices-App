package com.dharussalam.schoolnoticesapp;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminNotificationAdapter extends FirebaseRecyclerAdapter<NotificationModel, AdminNotificationAdapter.myViewHolder>  {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminNotificationAdapter(@NonNull FirebaseRecyclerOptions<NotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull NotificationModel model) {
        holder.tittle.setText(model.getTittle());
        holder.description.setText(model.getDescription());

        long timestamp = model.getTimestamp();
        // Format timestamp as desired (e.g., using SimpleDateFormat)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(new Date(timestamp));

        holder.date.setText(formattedTime);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.tittle.getContext())
                        .setContentHolder(new ViewHolder(R.layout.notification_popup_update))
                        .setGravity(Gravity.CENTER)
                        .create();

                //dialogPlus.show();
                View view = dialogPlus.getHolderView();

                EditText tittle = view.findViewById(R.id.edTittle);
                EditText description = view.findViewById(R.id.edDescription);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                tittle.setText(model.getTittle());
                description.setText(model.getDescription());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("tittle", tittle.getText().toString());
                        map.put("description", description.getText().toString());
                        map.put("timestamp", ServerValue.TIMESTAMP);

                        FirebaseDatabase.getInstance().getReference().child("notification").child(getRef(position).getKey())
                                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.tittle.getContext(), "Data Update Successfully...", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(holder.tittle.getContext(), "Error while Updating", Toast.LENGTH_SHORT);
                                    }
                                });
                    }
                });

            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_notification_list,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView tittle, date, description;

        Button btnEdit;



        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle = (TextView)itemView.findViewById(R.id.txtTittle);
            date = (TextView)itemView.findViewById(R.id.txtDate);
            description = (TextView) itemView.findViewById(R.id.txtDescription);
            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
        }
    }
}
