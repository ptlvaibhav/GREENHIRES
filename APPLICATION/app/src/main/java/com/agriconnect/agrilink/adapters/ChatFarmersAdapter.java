package com.agriconnect.agrilink.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agriconnect.agrilink.AirtableResponse;
import com.agriconnect.agrilink.R;

import java.util.List;

public class ChatFarmersAdapter extends RecyclerView.Adapter<ChatFarmersAdapter.FarmerViewHolder> {

    private List<AirtableResponse.UserRecord> farmerList;

    public ChatFarmersAdapter(List<AirtableResponse.UserRecord> farmerList) {
        this.farmerList = farmerList;
    }

    @NonNull
    @Override
    public FarmerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatusers_list, parent, false);
        return new FarmerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmerViewHolder holder, int position) {
        AirtableResponse.UserRecord farmer = farmerList.get(position);
        holder.nameTextView.setText(farmer.getFields().getName());
        holder.phoneTextView.setText(farmer.getFields().getPhone());
        holder.emailTextView.setText(farmer.getFields().getEmail());
    }

    @Override
    public int getItemCount() {
        return farmerList.size();
    }

    static class FarmerViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public FarmerViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_name);
            phoneTextView = itemView.findViewById(R.id.textView_phone);
            emailTextView = itemView.findViewById(R.id.textView_email);
        }
    }
}
