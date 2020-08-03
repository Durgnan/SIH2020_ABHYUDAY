package com.sih2020.abhyuday.Ambulance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.sih2020.abhyuday.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.MyViewHolder> {
    AlertAdapterListener mListener;
    JSONArray alertsArray;
    Context context;

    public AlertAdapter(JSONArray jsonElements) {
        alertsArray = jsonElements;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_item, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view, mListener);
    }

    public void setOnItemClickListener(AlertAdapterListener alertAdapterListener) {
        mListener = alertAdapterListener;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            JSONObject jsonObject = (JSONObject) alertsArray.get(position);
            holder.tvEmail.setText(jsonObject.getString("email"));
            holder.tvLocation.setText(jsonObject.getString("latlng"));
            holder.btnAccept.setOnClickListener(v -> {
                Log.d("GFG", "onBindViewHolder: Accepted");
                context.startActivity(new Intent(context, HospitalsActivity.class));
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return alertsArray.length();
    }

    public interface AlertAdapterListener {
        void onAccepted(int position);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvEmail, tvLocation;
        MaterialButton btnAccept, btnReject;

        public MyViewHolder(@NonNull View itemView, AlertAdapterListener alertAdapterListener) {
            super(itemView);
            tvEmail = itemView.findViewById(R.id.user_email);
            tvLocation = itemView.findViewById(R.id.user_location);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnReject = itemView.findViewById(R.id.btn_reject);
            btnReject.setOnClickListener(v -> {
                if (alertAdapterListener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        alertAdapterListener.onAccepted(pos);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
        }
    }
}

