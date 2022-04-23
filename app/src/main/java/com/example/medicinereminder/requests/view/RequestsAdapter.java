package com.example.medicinereminder.requests.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.medicinereminder.R;
import com.example.medicinereminder.model.RequestDTO;
import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {

    private Context context;
    List<RequestDTO> requests;

    public RequestsAdapter(Context context, List<RequestDTO> requests){
        this.context = context;
        this.requests = requests;
    }
    @NonNull
    @Override
    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new RequestsAdapter.RequestsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.request_row, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RequestsViewHolder holder, int position) {
        holder.txtRequest.setText(requests.get(position).getName());
        holder.btnConfirmRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "confirm request", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "delete request", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

   

    @Override
    public int getItemCount() {
        return requests.size();
    }

    class RequestsViewHolder extends RecyclerView.ViewHolder {
        TextView txtRequest;
        Button btnConfirmRequest,btnDeleteRequest;
        View view;

        public RequestsViewHolder( View view){
            super(view);
            txtRequest = view.findViewById(R.id.txtUserNameRequest);
            btnConfirmRequest = view.findViewById(R.id.btnconfirmRequest);
            btnDeleteRequest = view.findViewById(R.id.btnDeleteRequest);
            this.view = view;

        }
    }

}
