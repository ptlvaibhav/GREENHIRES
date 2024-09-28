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

public class ChatLandlordsAdapter extends RecyclerView.Adapter<ChatLandlordsAdapter.LandlordViewHolder> {

    private List<AirtableResponse.UserRecord> landlordList;

    public ChatLandlordsAdapter(List<AirtableResponse.UserRecord> landlordList) {
        this.landlordList = landlordList;
    }

    @NonNull
    @Override
    public LandlordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatusers_list, parent, false);
        return new LandlordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LandlordViewHolder holder, int position) {
        AirtableResponse.UserRecord landlord = landlordList.get(position);
        holder.nameTextView.setText(landlord.getFields().getName());
        holder.phoneTextView.setText(landlord.getFields().getPhone());
        holder.emailTextView.setText(landlord.getFields().getEmail());
    }

    @Override
    public int getItemCount() {
        return landlordList.size();
    }

    static class LandlordViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public LandlordViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_name);
            phoneTextView = itemView.findViewById(R.id.textView_phone);
            emailTextView = itemView.findViewById(R.id.textView_email);
        }
    }
}
