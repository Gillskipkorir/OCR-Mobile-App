package com.kip.gillz.amigo.Allclients.profilee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.Scanner;
import com.kip.gillz.amigo.utils.Tools;

public class Profile extends AppCompatActivity {




    TextView tvname,tvfname,tvlname,tvoname,tvgender,tvphone,
            tvemail,tvidno,tvdate,tvadress,tvpobox,tvkra;
    private static final int REQUEST_PHONE_CALL = 1;
    ImageButton call,text;
    String phoneHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        tvname = findViewById(R.id.profilenames);
        tvphone = findViewById(R.id.ph);
        tvfname = findViewById(R.id.fn);
        tvlname = findViewById(R.id.ln);
        tvoname = findViewById(R.id.on);
        tvidno = findViewById(R.id.idn);
        tvemail = findViewById(R.id.mail);
        tvadress = findViewById(R.id.pa);
        tvpobox = findViewById(R.id.po);
        tvkra = findViewById(R.id.kra);
        tvdate = findViewById(R.id.dat);
        tvgender = findViewById(R.id.gn);

        call= findViewById(R.id.btncall);

// todo: get value from previouas Avtivity

        String meternoHolder = getIntent().getStringExtra("meterno");
        String FnameHolder = getIntent().getStringExtra("fname");
        String LnameHolder = getIntent().getStringExtra("lname");
        String OnameHolder = getIntent().getStringExtra("oname");
        String genderHolder = getIntent().getStringExtra("gender");
        String emailHolder = getIntent().getStringExtra("email");
        phoneHolder = getIntent().getStringExtra("phone");
        String idnoHolder = getIntent().getStringExtra("idno");
        String kraHolder = getIntent().getStringExtra("krapin");
        String adressHolder = getIntent().getStringExtra("adress");
        String postalHolder = getIntent().getStringExtra("postal");
        String dateHolder = getIntent().getStringExtra("reg_date");


        tvname.setText("Tal Clients Profile");
        tvfname.setText(FnameHolder);
        tvlname.setText(LnameHolder);
        tvphone.setText("0"+phoneHolder);
        tvemail.setText(emailHolder);
        tvkra.setText(kraHolder);
        tvidno.setText(idnoHolder);
        tvgender.setText(genderHolder);
        tvpobox.setText(postalHolder);
        tvadress.setText(adressHolder);
        tvdate.setText(dateHolder);

            if (OnameHolder.equals(""))
             {tvoname.setText("N/a");}
            else{ tvoname.setText(OnameHolder);            }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String PhoneNum = "0" + phoneHolder;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(PhoneNum.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }

        });



        initToolbar();
        initComponent();
        profbacktask aaa =new profbacktask(this);
        aaa.execute(meternoHolder);

    }

    private void initToolbar() {
        String FnameHolder = getIntent().getStringExtra("firstname");

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(FnameHolder+"'s Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.colorPrimary);
    }

    private void initComponent() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profilemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void phoneCall(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            String PhoneNum = "0"+phoneHolder;
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+Uri.encode(PhoneNum.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);

        }else{
            Toast.makeText(this, " permission issues.", Toast.LENGTH_SHORT).show();
        }
//todo important
        if(isPermissionGranted()){
            phoneCall();
        }
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Call Permission granted", Toast.LENGTH_SHORT).show();
                    phoneCall();
                } else {
                    Toast.makeText(getApplicationContext(), "Call Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
