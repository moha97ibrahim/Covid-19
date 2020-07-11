package com.mohammedibrahim.covid_19.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mohammedibrahim.covid_19.R;


public class SplashScreenActivity extends AppCompatActivity {

//024cd2e9ed73490ea47ab20a25ece2a4
    private ImageView applogo,devlogo;
    private TextView Go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);
        applogo = findViewById(R.id.logo);
        applogo.startAnimation(animation);
        devlogo = findViewById(R.id.devLogo);
        devlogo.startAnimation(animation);
        Go = findViewById(R.id.go);

        intentToDashboard(3000);

    }

    private void intentToDashboard(int time) {
        if(isConnected()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this,DashboardActivity.class);
                    startActivity(i);
                    finish();
                }
            },time);
        }
        else{

            Toast.makeText(SplashScreenActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
            devlogo.setVisibility(View.GONE);
            Go.setVisibility(View.VISIBLE);
            Go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intentToDashboard(0);
                }
            });



        }
    }


    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

}
