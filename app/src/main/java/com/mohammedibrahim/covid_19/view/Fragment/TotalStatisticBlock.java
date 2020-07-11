package com.mohammedibrahim.covid_19.view.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mohammedibrahim.covid_19.Api.CovidApiClient;
import com.mohammedibrahim.covid_19.Data.GetData;
import com.mohammedibrahim.covid_19.R;
import com.mohammedibrahim.covid_19.model.CovidData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TotalStatisticBlock extends Fragment {

    private TextView activeCases, recovered, deaths, totalCases, lastUpdate;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String LOCAL = "local";
    private TextView activeDiffStats, recoverDiffStats, deathDiffStats, totalCaseDiffStats;
    private ImageView refresh;
    private RotateAnimation rotateAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_total_statistic_block, container, false);

        sharedPreferences = getActivity().getSharedPreferences(LOCAL, Context.MODE_PRIVATE);

        activeCases = view.findViewById(R.id.activeCases);
        recovered = view.findViewById(R.id.recovered);
        deaths = view.findViewById(R.id.deaths);
        totalCases = view.findViewById(R.id.totalCases);
        lastUpdate = view.findViewById(R.id.lastUpdatedAtApify);
        refresh = view.findViewById(R.id.refresh);

        activeDiffStats = view.findViewById(R.id.activeStat);
        recoverDiffStats = view.findViewById(R.id.recoverStat);
        deathDiffStats = view.findViewById(R.id.deathStat);
        totalCaseDiffStats = view.findViewById(R.id.totalStats);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(2000);
        requestApiData();


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotateAnimation.start();
                requestApiData();
                Toast.makeText(getContext(),"Refreshed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestApiData() {
        GetData service = CovidApiClient.getRetrofitInstance().create(GetData.class);
        Call<CovidData> call = service.getAllData();
        call.enqueue(new Callback<CovidData>() {
            @Override
            public void onResponse(Call<CovidData> call, Response<CovidData> response) {
                CovidData covidData = response.body();
                loadData(covidData);
                rotateAnimation.cancel();
            }

            @Override
            public void onFailure(Call<CovidData> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to load" + t, Toast.LENGTH_LONG).show();
            }
        });

    }


    private void loadData(CovidData list) {
        Log.e("demo", "" + list.getActiveCases());
//        Toast.makeText(getActivity(), "" + list.getActiveCases(), Toast.LENGTH_LONG).show();
        editor = sharedPreferences.edit();
        if(sharedPreferences.getString("oldLastUpdate", null)==null) {
            editor.putInt("oldActiveCase", list.getActiveCases());
            editor.putInt("oldRecovered", list.getRecovered());
            editor.putInt("oldDeaths", list.getDeaths());
            editor.putInt("oldTotalCases", list.getTotalCases());
            editor.putString("oldLastUpdate", list.getLastUpdatedAtApify());
            editor.apply();
        }
        activeCases.setText(String.valueOf(list.getActiveCases()));
        recovered.setText(String.valueOf(list.getRecovered()));
        deaths.setText(String.valueOf(list.getDeaths()));
        totalCases.setText(String.valueOf(list.getTotalCases()));
        lastUpdate.setText(getFormat(list.getLastUpdatedAtApify()));
        updateStatsUpandDown(list);
    }

    private String getFormat(String lastUpdatedAtApify) {
        String format = lastUpdatedAtApify.replace("T", "  ");
        format = format.replace(":00.000Z", "");
        return format;
    }


    private String getTime() {
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(calendar.getTime());
        return date;
    }

    private void updateStatsUpandDown(CovidData list) {
        activeDiffStats.setText(getDiffCount(list, sharedPreferences.getInt("oldActiveCase", 0), list.getActiveCases(), sharedPreferences.getString("oldLastUpdate", null)));
        recoverDiffStats.setText(getDiffCount(list, sharedPreferences.getInt("oldRecovered", 0), list.getRecovered(), sharedPreferences.getString("oldLastUpdate", null)));
        deathDiffStats.setText(getDiffCount(list, sharedPreferences.getInt("oldDeaths", 0), list.getDeaths(), sharedPreferences.getString("oldLastUpdate", null)));
        totalCaseDiffStats.setText(getDiffCount(list, sharedPreferences.getInt("oldTotalCases", 0), list.getTotalCases(), sharedPreferences.getString("oldLastUpdate", null)));

    }

    private String getDiffCount(CovidData list, int oldValue, int newValue, String oldLastUpdate) {
        if (oldLastUpdate.contains(getTime())) {
            int count = newValue - oldValue;
            Log.e("count", "" + count);
            if (count > 0)
                return "+" + count;
            else if (count == 0)
                return "";
            else
                return String.valueOf(count);

        } else {
            editor.putString("oldLastUpdate", list.getLastUpdatedAtApify());
            Log.e("count", "else");
            return "";
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
