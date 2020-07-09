package com.mohammedibrahim.covid_19.view.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.mohammedibrahim.covid_19.Adapter.ByStateAdapter;
import com.mohammedibrahim.covid_19.Api.CovidApiClient;
import com.mohammedibrahim.covid_19.Data.GetData;
import com.mohammedibrahim.covid_19.R;
import com.mohammedibrahim.covid_19.model.ByStateDataModel;
import com.mohammedibrahim.covid_19.model.CovidData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Collections.sort;

public class StateListBlock extends Fragment {



    private List<ByStateDataModel> byStateDataModelList;
    private ListView byStateListView;
    private ProgressDialog progressDialog;

    public StateListBlock() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_state_list_block, container, false);

        byStateListView = view.findViewById(R.id.byStateList);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching...");
        GetData service = CovidApiClient.getRetrofitInstance().create(GetData.class);
        Call<CovidData> call = service.getAllData();

        progressDialog.show();
        call.enqueue(new Callback<CovidData>() {
            @Override
            public void onResponse(Call<CovidData> call, Response<CovidData> response) {
                CovidData covidData = response.body();
                loadData(covidData);
            }

            @Override
            public void onFailure(Call<CovidData> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to load" + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadData(CovidData covidData) {
        List<CovidData.byState> byStatesList = covidData.getRegionData();
        Log.e("demo",""+byStatesList.get(0).getRegion());
        byStateDataModelList = new ArrayList<>();


        for(int i=0;i<byStatesList.size();i++){
            byStateDataModelList.add(new ByStateDataModel(byStatesList.get(i).getRegion(),byStatesList.get(i).getTotalInfected(),byStatesList.get(i).getRecovered(),byStatesList.get(i).getDeceased(),byStatesList.get(i).getTotalCases()));
        }


        ByStateAdapter byStateAdapter = new ByStateAdapter(getContext(),R.layout.bystate_list_layout,byStateDataModelList);
        byStateListView.setAdapter(byStateAdapter);
        progressDialog.cancel();
    }



}
