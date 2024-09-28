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

public class ChatMerchantsAdapter extends RecyclerView.Adapter<ChatMerchantsAdapter.MerchantViewHolder> {

    private List<AirtableResponse.UserRecord> merchantList;

    public ChatMerchantsAdapter(List<AirtableResponse.UserRecord> merchantList) {
        this.merchantList = merchantList;
    }

    @NonNull
    @Override
    public com.agriconnect.agrilink.adapters.ChatMerchantsAdapter.MerchantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatusers_list, parent, false);
        return new ChatMerchantsAdapter.MerchantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.agriconnect.agrilink.adapters.ChatMerchantsAdapter.MerchantViewHolder holder, int position) {
        AirtableResponse.UserRecord merchant = merchantList.get(position);
        holder.nameTextView.setText(merchant.getFields().getName());
        holder.phoneTextView.setText(merchant.getFields().getPhone());
        holder.emailTextView.setText(merchant.getFields().getEmail());
    }

    @Override
    public int getItemCount() {
        return merchantList.size();
    }

    static class MerchantViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView phoneTextView;
        TextView emailTextView;

        public MerchantViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_name);
            phoneTextView = itemView.findViewById(R.id.textView_phone);
            emailTextView = itemView.findViewById(R.id.textView_email);
        }
    }
}
