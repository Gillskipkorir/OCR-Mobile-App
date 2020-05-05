package com.kip.gillz.amigo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

public class Splashscreen extends AppCompatActivity {

    UserSessionManager session;
    String Username;
    TextView textView;
    private ProgressBar progress_bar;


    private static int SPLASH_TIME_OUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }
        else {

            session = new UserSessionManager(getApplicationContext());

            HashMap<String, String> user = session.getUserDetails();
            Username = user.get(UserSessionManager.KEY_ADMIN_USERNAME);
            textView = findViewById(R.id.splashtext);
            progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
            textView.setText("Welcome "+ Username+" to Tal");
            searchAction();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent= new Intent(Splashscreen.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },SPLASH_TIME_OUT);
        }

    }

    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
            }
        }, 4000);
    }
}
