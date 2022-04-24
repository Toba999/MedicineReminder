package com.example.medicinereminder.requests.view;

import android.annotation.SuppressLint;
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
import com.example.medicinereminder.model.PatientDTO;
import com.example.medicinereminder.model.RequestDTO;
import com.example.medicinereminder.model.TrackerDTO;
import com.example.medicinereminder.requests.presenter.RequestsPresenter;
import com.example.medicinereminder.requests.presenter.RequestsPresenterInterface;

import java.util.List;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsViewHolder> {//implements RequestsActivityInterface{

    private Context context;
    List<RequestDTO> requests;
    RequestsPresenterInterface prsenter;
    String trakerUserName,trakerEmail,senderEmai,senderUserName,requestID;
    TrackerDTO tracker ;
    PatientDTO patient;
    RequestsActivityInterface activity ;

    public RequestsAdapter(Context context, List<RequestDTO> requests,RequestsActivityInterface activity){
        this.context = context;
        this.requests = requests;
        this.activity = activity;
      //  prsenter = new RequestsPresenter(context,RequestsAdapter.this);

    }

    @NonNull
    @Override
    public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new RequestsAdapter.RequestsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.request_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RequestsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtRequest.setText(requests.get(position).getName());
        senderEmai = requests.get(position).getMyEmail();//prsenter.currentUser().getEmail();
        trakerEmail  = requests.get(position).getEmail();
        senderUserName = requests.get(position).getName();
        requestID= requests.get(position).getRequestID();
        holder.btnConfirmRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.getUserFromRealDB(trakerEmail);
               // prsenter.getUserFromRealDB(trakerEmail);
//                tracker = new TrackerDTO(senderEmai,trakerEmail,"tasnem",1,requestID);
//                patient = new PatientDTO(trakerEmail,senderEmai,senderUserName,1);
//               prsenter.onAccept(tracker,patient);
//               notifyDataSetChanged();
                Toast.makeText(view.getContext(), "confirm request", Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnDeleteRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete(requestID,trakerEmail);
               // prsenter.onReject(requestID,trakerEmail);
                Toast.makeText(view.getContext(), "delete request", Toast.LENGTH_SHORT).show();
            }
        });
        
    }

    public void setListToAdapter(List<RequestDTO> requests){
        this.requests = requests;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

//    @Override
//    public void setonSuccessRequest(List<RequestDTO> requestPojos) {
//
//    }

   // @Override
    public void setonSuccessReturn(String userName) {
        trakerUserName = userName;
        tracker = new TrackerDTO(senderEmai,trakerEmail,trakerUserName,1,requestID);
        patient = new PatientDTO(trakerEmail,senderEmai,senderUserName,1);
        activity.confirem(tracker,patient);
       // prsenter.onAccept(tracker,patient);
        notifyDataSetChanged();

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
