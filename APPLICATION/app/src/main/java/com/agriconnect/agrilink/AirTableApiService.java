package com.agriconnect.agrilink;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AirTableApiService {
    @Headers("Authorization: Bearer pat0h7EQw0GwMJ7G8.d3934955ea973a65713953b1d6688f844033bc8fd3bed03498656377ca0e0d2e")
    @GET("user")
    Call<AirtableResponse> getUser(@Query("filterByFormula") String filter);
    @Headers({"Authorization: Bearer pat0h7EQw0GwMJ7G8.d3934955ea973a65713953b1d6688f844033bc8fd3bed03498656377ca0e0d2e",
            "Content-Type: application/json"})
    @POST("user")
    Call<AirtableResponse> createUser(@Body AirtableUser user);
}
