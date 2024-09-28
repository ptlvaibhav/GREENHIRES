package com.agriconnect.agrilink.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.agriconnect.agrilink.AirTableApiService;
import com.agriconnect.agrilink.AirtableResponse;
import com.agriconnect.agrilink.R;
import com.agriconnect.agrilink.adapters.ChatFarmersAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FarmerFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatFarmersAdapter chatfarmerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_farmer, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_farmers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchFarmersFromAirtable();

        return view;
    }

    private void fetchFarmersFromAirtable() {
        // Setup Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.airtable.com/v0/appPUhJG6Yhxb4AWI/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AirTableApiService apiService = retrofit.create(AirTableApiService.class);

        // Filter for users with Role = Farmer
        String filterFormula = "Role='Farmer'";
        Call<AirtableResponse> call = apiService.getUser(filterFormula);

        call.enqueue(new Callback<AirtableResponse>() {
            @Override
            public void onResponse(Call<AirtableResponse> call, Response<AirtableResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<AirtableResponse.UserRecord> farmerList = response.body().getRecords();
                    chatfarmerAdapter = new ChatFarmersAdapter(farmerList);
                    recyclerView.setAdapter(chatfarmerAdapter);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AirtableResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
