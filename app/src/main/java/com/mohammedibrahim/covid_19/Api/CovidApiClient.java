package com.mohammedibrahim.covid_19.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidApiClient {
    private static Retrofit retrofit;

    private static final String BASE_URL = "https://api.apify.com/v2/key-value-stores/toDWvRj1JpTXiM8FF/records/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
