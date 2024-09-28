package com.agriconnect.agrilink.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.agriconnect.agrilink.AirtableResponse;
import com.agriconnect.agrilink.R;

public class ChatIndustriesAdapter extends RecyclerView.Adapter<ChatIndustriesAdapter.IndustryViewHolder> {

    private List<AirtableResponse.UserRecord> industryList;

    public ChatIndustriesAdapter(List<AirtableResponse.UserRecord> industryList) {
        this.industryList = industryList;
    }

    @NonNull
    @Override
    public com.agriconnect.agrilink.adapters.ChatIndustriesAdapter.IndustryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatusers_list, parent, false);
        return new ChatIndustriesAdapter.IndustryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.agriconnect.agrilink.adapters.ChatIndustriesAdapter.IndustryViewHolder holder, int position) {
        AirtableResponse.UserRecord industry = industryList.get(position);
        holder.nameTextView.setText(industry.getFields().getName());
        holder.phoneTextView.setText(industry.getFields().getPhone());
        holder.emailTextView.setText(industry.getFields().getEmail());
    }

    @Override
    public int getItemCount() {
        return industryList.size();
    }

    static class IndustryViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public IndustryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_name);
            phoneTextView = itemView.findViewById(R.id.textView_phone);
            emailTextView = itemView.findViewById(R.id.textView_email);
        }
    }
}
