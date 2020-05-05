package com.kip.gillz.amigo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kip.gillz.amigo.AdminCheck.Webpage;
import com.kip.gillz.amigo.Agentsss.Agents;
import com.kip.gillz.amigo.Allclients.All_clients;
import com.kip.gillz.amigo.Newclient.AddnewClient;
import com.kip.gillz.amigo.utils.Tools;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adminpage extends AppCompatActivity {

    MaterialRippleLayout add,viewcli,viewage,gotoweb;
    UserSessionManager session;
    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        Username = user.get(UserSessionManager.KEY_ADMIN_USERNAME);
        initToolbar();

        final FloatingActionButton fab_call = findViewById(R.id.fab_call);
        add = findViewById(R.id.neww);
        viewage = findViewById(R.id.va);
        viewcli = findViewById(R.id.vc);
        gotoweb = findViewById(R.id.web);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Adminpage.this,AddnewClient.class);
                startActivity(intent);

            }
        });

        viewage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Adminpage.this,Agents.class);
                startActivity(intent);

            }
        });

        viewcli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Adminpage.this,All_clients.class);
                startActivity(intent);

            }
        });

        gotoweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Adminpage.this,Webpage.class);
                startActivity(intent);
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new SweetAlertDialog(Adminpage.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout of the App?")
                        .setContentText(Username+ ",  are you Sure you want to Logout?\"")
                        .setConfirmText("Logout")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                //end user Session
                                session.logoutUser();
                                finish();
                                Toast.makeText(getApplicationContext(), "You have logged out successfully", Toast.LENGTH_LONG).show();
                                sDialog.dismissWithAnimation();


                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(Adminpage.this);
                builder.setTitle(Username+",  are you Sure you want to Logout?");
                builder.setIcon(R.drawable.ic_lock_outline_black_24dp);
                builder.setPositiveButton("Yes Log Me Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "You have logged out successfully", Toast.LENGTH_LONG).show();
                        //end user Session
                        session.logoutUser();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();


            }
        });


    }


    private void initToolbar() {
        String FnameHolder = getIntent().getStringExtra("firstname");

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome Admin "+Username +" .");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

}
