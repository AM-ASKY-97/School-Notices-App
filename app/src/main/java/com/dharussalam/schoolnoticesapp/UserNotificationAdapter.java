package com.dharussalam.schoolnoticesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserNotificationAdapter extends FirebaseRecyclerAdapter <NotificationModel, UserNotificationAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserNotificationAdapter(@NonNull FirebaseRecyclerOptions<NotificationModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull NotificationModel model) {
        holder.tittle.setText(model.getTittle());
        holder.description.setText(model.getDescription());

        long timestamp = model.getTimestamp();
        // Format timestamp as desired (e.g., using SimpleDateFormat)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTime = sdf.format(new Date(timestamp));

        holder.date.setText(formattedTime);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_notification_list,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView tittle, date, description;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tittle = (TextView)itemView.findViewById(R.id.txtTittle);
            date = (TextView)itemView.findViewById(R.id.txtDate);
            description = (TextView) itemView.findViewById(R.id.txtDescription);
        }
    }
}
