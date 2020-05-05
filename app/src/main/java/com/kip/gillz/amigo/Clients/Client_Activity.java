package com.kip.gillz.amigo.Clients;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kip.gillz.amigo.MainActivity;
import com.kip.gillz.amigo.Myread.Myreadings;
import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.UserSessionManager;

import java.util.HashMap;

public class Client_Activity extends AppCompatActivity {

    MaterialRippleLayout materialRippleLayout,chgpass;
    Button btnchangepass,btnviewreadings;
    TextView userid;
    String Username,Password;
    UserSessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome Client To Tal App");

        session = new UserSessionManager(getApplicationContext());
        /*if (session.checkLogin()) {
            finish();
        }*/
        session = new UserSessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        Username = user.get(UserSessionManager.KEY_ADMIN_USERNAME);
        Password = user.get(UserSessionManager.KEY_ADMIN_PASSWORD);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            Toast.makeText(getApplicationContext(), "Help Coming Soon", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id==R.id.profile){
            showCustomDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showCustomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.login);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        materialRippleLayout= dialog.findViewById(R.id.btlogout);
        userid = dialog.findViewById(R.id.userid);
        btnchangepass= dialog.findViewById(R.id.changepass);
        btnviewreadings = dialog.findViewById(R.id.viewreadings);

        userid.setText("Username: "+ Username);

        btnchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changepsw();

            }
        });


        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Client_Activity.this);
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

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
