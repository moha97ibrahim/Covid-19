package com.mohammedibrahim.covid_19.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mohammedibrahim.covid_19.R;
import com.mohammedibrahim.covid_19.view.Fragment.TotalStatisticBlock;

public class DashboardActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8942221596028723/3553173641");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


      //  swipeRefreshLayout = findViewById(R.id.refreshPage);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadToatalStatsBlock();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mInterstitialAd.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.feedback:
                Intent intentFeed = new Intent(DashboardActivity.this, FeedbackActivity.class);
                startActivity(intentFeed);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void loadToatalStatsBlock() {
        TotalStatisticBlock totalStatisticBlock = new TotalStatisticBlock();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.total_stat_block, totalStatisticBlock)
                .commit();

    }
}
