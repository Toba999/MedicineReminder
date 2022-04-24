package com.example.medicinereminder.tracker_screen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medicinereminder.R;
import java.util.List;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerAdapter.TrackerViewHolder> {
    private Context context;
    List<String> trackers;

    public TrackerAdapter(Context context, List<String> trackers){
        this.context = context;
        this.trackers = trackers;
    }

    @NonNull
    @Override
    public TrackerAdapter.TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackerAdapter.TrackerViewHolder(LayoutInflater.from(context).inflate(R.layout.tracker_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerAdapter.TrackerViewHolder holder, int position) {
        holder.emailTextView.setText(trackers.get(position));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return trackers.size();
    }

    class TrackerViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        View view;

        public TrackerViewHolder(@NonNull View view){
            super(view);
            this.view = view;
            emailTextView = view.findViewById(R.id.trackerEmail);
        }
    }
}
