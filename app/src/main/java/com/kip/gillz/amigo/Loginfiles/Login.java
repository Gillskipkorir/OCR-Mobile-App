package com.kip.gillz.amigo.Loginfiles;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kip.gillz.amigo.Login_Client;
import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.UserSessionManager;
import com.kip.gillz.amigo.utils.ViewAnimation;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

public class Login extends AppCompatActivity {
    //initializing
    EditText username,password;
    Button btnlogin;
    private View back_drop;
    private boolean rotate = false;
    private View lyt_mic;
    private View lyt_call;
    private View parent_view;

    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //casting
        parent_view = findViewById(android.R.id.content);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.login);
        back_drop = findViewById(R.id.back_drop);

        final FloatingActionButton fab_mic =  findViewById(R.id.fab_mic);
        final FloatingActionButton fab_call =  findViewById(R.id.fab_call);
        final FloatingActionButton fab_add = findViewById(R.id.fab_add);
        lyt_mic = findViewById(R.id.lyt_mic);
        lyt_call = findViewById(R.id.lyt_call);
        ViewAnimation.initShowOut(lyt_mic);
        ViewAnimation.initShowOut(lyt_call);
        back_drop.setVisibility(View.GONE);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(fab_add);
            }
        });

        fab_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Forgot password?", Toast.LENGTH_SHORT).show();
               /* Intent intent = new Intent(Login.this,Login_Client.class);
                startActivity(intent);
                finish();*/
            }
        });

        session = new UserSessionManager(getApplicationContext());

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Username,Password;
                Username= username.getText().toString().trim();
                Password= password.getText().toString().trim();

                if (TextUtils.isEmpty(Username)&&TextUtils.isEmpty(Password)) {

                    username.setError("Username is Required");
                    password.setError("Your Password Required");

                    return;
                }

                else if (TextUtils.isEmpty(Username))
                {
                    username.setError("Please Enter Your Username");
                    return;
                }
                else if (TextUtils.isEmpty(Password))
                {
                    password.setError("Please Enter Your password");
                    return;
                }          else {
                    password.setError(null);
                    username.setError(null);
                    if (internetAvailable())
                    {
                        //saving username on the session
                        session.createUserLoginSession(Username,Password);

                        LoginPageBtask loginPageBtask = new LoginPageBtask(Login.this);
                        loginPageBtask.execute(username.getText().toString(), password.getText().toString());
                    }
                    else
                    {

                        new LovelyStandardDialog(Login.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.colorPrimary)
                                .setButtonsColorRes(R.color.cyan_700)
                                .setIcon(R.drawable.ic_help_outline_black_24dp)
                                .setTitle("No Internet").setTitleGravity(Gravity.CENTER)
                                .setMessage("Please Check Your Internet Connection and again").setMessageGravity(Gravity.CENTER)
                                .setCancelable(false)
                                .setPositiveButton("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();

                    }


                }


                //searchAction();
            }
        });
    }


    @Override
    public void onBackPressed() {
        doExitApp();
    }

    private long exitTime = 0;

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }


    private boolean internetAvailable() {
        boolean haveWiFi = false;
        boolean haveMobile = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo networkInfo : networkInfos) {
            if (networkInfo.getTypeName().equalsIgnoreCase("WiFi")) {
                if (networkInfo.isConnected()) {
                    haveWiFi = true;
                }
            }
            if (networkInfo.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (networkInfo.isConnected()) {
                    haveMobile = true;
                }
            }
        }
        return haveWiFi || haveMobile;
    }
    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_mic);
            ViewAnimation.showIn(lyt_call);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_mic);
            ViewAnimation.showOut(lyt_call);
            back_drop.setVisibility(View.GONE);
        }
    }

}
