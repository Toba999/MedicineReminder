package com.example.medicinereminder.tracker_screen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.TrackerDTO;
import java.util.List;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerAdapter.TrackerViewHolder> {
    private Context context;
    List<TrackerDTO> trackers;
    TrakerActivityInterface activity;

    public TrackerAdapter(Context context, List<TrackerDTO> trackers,TrakerActivityInterface activity){
        this.context = context;
        this.trackers = trackers;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TrackerAdapter.TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrackerAdapter.TrackerViewHolder(LayoutInflater.from(context).inflate(R.layout.tracker_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerAdapter.TrackerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.emailTextView.setText(trackers.get(position).getName());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String  trackerse = trackers.get(position).getName();
              String patient = trackers.get(position).getPatientEmail();
                activity.deleteTracker(trackers.get(position).getName(),trackers.get(position).getPatientEmail());
                notifyDataSetChanged();
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setListToAdapter(List<TrackerDTO> trackers){
        this.trackers = trackers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return trackers.size();
    }

    class TrackerViewHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        ImageView image;
        View view;

        public TrackerViewHolder(@NonNull View view){
            super(view);
            this.view = view;
            emailTextView = view.findViewById(R.id.trackerEmail);
            image = view.findViewById(R.id.deleteTracker);
        }
    }
}
