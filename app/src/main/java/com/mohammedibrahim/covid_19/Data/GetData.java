package com.mohammedibrahim.covid_19.Data;

import android.database.Observable;

import com.mohammedibrahim.covid_19.model.CovidData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetData {

    @GET("LATEST?disableRedirect=true")
    Call<CovidData> getAllData();
}
