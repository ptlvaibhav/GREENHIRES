package com.agriconnect.agrilink;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AirTableApiService {
    @Headers("Authorization: Bearer pat0h7EQw0GwMJ7G8.08464c93c7705eff3069e37f7a7749ba3f95cd0de748e3c7fc9fa2f4dc1888bd")
    @GET("user")
    Call<AirtableResponse> getUser(@Query("filterByFormula") String filter);
    @Headers({"Authorization: Bearer pat0h7EQw0GwMJ7G8.08464c93c7705eff3069e37f7a7749ba3f95cd0de748e3c7fc9fa2f4dc1888bd",
            "Content-Type: application/json"})
    @POST("user")
    Call<AirtableResponse> createUser(@Body AirtableUser user);
}