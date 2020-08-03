package com.sih2020.abhyuday.Ambulance;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sih2020.abhyuday.R;
import com.sih2020.abhyuday.Util.Utils;
import com.sih2020.abhyuday.models.Results;

import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder> {

    private List<Results> results;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private Location location;


    public HospitalAdapter(List<Results> results, Location location) {
        this.results = results;
        this.location = location;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hospital_item, viewGroup, false);
        context = viewGroup.getContext();
        return new MyViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Results result = results.get(i);

        Glide.with(context)
                .load(result.getIcon())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

        holder.name.setText(result.getName());
        holder.vicinity.setText(result.getVicinity());
        holder.contact.setText(new Utils().getContacts().get(new Random().nextInt(4)));
        double distanceInKilo = new Utils().distance(location.getLatitude(), location.getLongitude(), result.getGeometry().getLocation().getLat(), result.getGeometry().getLocation().getLng());
        double distanceOneDP = (double) Math.round(distanceInKilo * 10) / 10;

        holder.distance.setText(String.valueOf(distanceOneDP).concat(" km"));
        try {
            Object o = result.getOpeningHours().getOpenNow();
            if (o.equals(false)) {
                holder.status.setTextColor(Color.parseColor("#FFFF0000"));
                holder.status.setText("Closed");
            } else {
                holder.status.setTextColor(Color.parseColor("#FF086B0D"));
                holder.status.setText("Open");
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, vicinity, status, contact, distance;
        ImageView imageView;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {

            super(itemView);

            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.tvName);
            vicinity = itemView.findViewById(R.id.tvVicinity);
            status = itemView.findViewById(R.id.tvOpen);
            imageView = itemView.findViewById(R.id.ivImg);
            contact = itemView.findViewById(R.id.tvContact);
            distance = itemView.findViewById(R.id.tvDistance);

            this.onItemClickListener = onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }


    }

}
