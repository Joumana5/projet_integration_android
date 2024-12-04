package com.example.projet_integration_android.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // The base URL for the API
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    // Private Retrofit instance
    private static Retrofit retrofit;

    // Get Retrofit client instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            // Initialize Retrofit with the base URL and Gson converter
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Set the base URL of the API
                    .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON response
                    .build();
        }
        return retrofit;
    }
}
