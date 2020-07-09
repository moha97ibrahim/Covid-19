package com.mohammedibrahim.covid_19.view.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.WifiAwareManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mohammedibrahim.covid_19.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private String ipAddress, nameString, emailString, messageString;
    private EditText name, email, message;
    private Button sendBtn;
    private FirebaseFirestore firebaseFirestore;
    private Map<String, String> data = new HashMap<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //Get Feedback

        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        message = findViewById(R.id.userMessage);
        sendBtn = findViewById(R.id.sendButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getApplicationContext());
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Sending...");
                progressDialog.show();
                sendData();
            }
        });

        //Get IP address of the user
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = Formatter.formatIpAddress(wifiInfo.getIpAddress());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feedback");
    }

    private void sendData() {
        nameString = name.getText().toString().trim();
        emailString = email.getText().toString().trim();
        messageString = message.getText().toString().trim();
        data.put("Name",nameString);
        data.put("Email",emailString);
        data.put("ip",ipAddress);
        data.put("message",messageString);
        data.put("time",getTime());

        if (TextUtils.isEmpty(nameString) && TextUtils.isEmpty(messageString)) {
            firebaseFirestore.collection("FeedBack")
                    .document()
                    .set(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FeedbackActivity.this, "Done", Toast.LENGTH_LONG).show();
                            progressDialog.cancel();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FeedbackActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(FeedbackActivity.this, "Field is empty", Toast.LENGTH_LONG).show();

        }


    }

    private String getTime() {
        Calendar calendar;
        SimpleDateFormat dateFormat;
        String date;
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        date = dateFormat.format(calendar.getTime());
        return date;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
