package com.agriconnect.agrilink;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AirTableApiService {
    @Headers("Authorization: Bearer pat0h7EQw0GwMJ7G8.a0e83d2a8caba436893708229e3183754b278b16e537c339b76df09fb37af951")
    @GET("user")
    Call<AirtableResponse> getUser(@Query("filterByFormula") String filter);
    @Headers({"Authorization: Bearer pat0h7EQw0GwMJ7G8.a0e83d2a8caba436893708229e3183754b278b16e537c339b76df09fb37af951",
            "Content-Type: application/json"})
    @POST("user")
    Call<AirtableResponse> createUser(@Body AirtableUser user);
}
