package com.kip.gillz.amigo.Newclient;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.RegisterAPI;
import com.kip.gillz.amigo.UserSessionManager;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddnewClient extends AppCompatActivity {
    String fname,lname,phone,meter,inst_date,plot,remarkk;

    public static final String ROOT_URL = "https://tal.co.ke/";
    UserSessionManager session;

    private EditText editTextfName;
    private EditText editTextlName;
    private EditText editTextphone;
    private EditText editTextmeterno;
    private EditText editTextremark;
    private TextView textviewinst;
    private EditText editTextplot;    ;
    ArrayAdapter<CharSequence> arrayAdapter;

    String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_client);

        editTextfName=findViewById(R.id.names);
        editTextlName=findViewById(R.id.lname);
        editTextphone=findViewById(R.id.phone);
        editTextmeterno=findViewById(R.id.meterno);
        textviewinst=findViewById(R.id.isnt);
        editTextplot=findViewById(R.id.plotno);
        editTextremark=findViewById(R.id.remarks);

                (findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=  editTextfName.getText().toString().trim();
                lname=  editTextlName.getText().toString().trim();
                phone=editTextphone.getText().toString().trim();
                meter= editTextmeterno.getText().toString().trim();
                inst_date= textviewinst.getText().toString().trim();
                plot= editTextplot.getText().toString().trim();
                remarkk= editTextremark.getText().toString().trim();

                if (TextUtils.isEmpty(fname)&&TextUtils.isEmpty(lname)&&TextUtils.isEmpty(meter)) {
                    editTextfName.setError("First Name is Required");
                    editTextlName.setError("Last Name is Required");
                    editTextmeterno.setError("Meter Number is Required");
                    return;
                }

                else if (TextUtils.isEmpty(fname))
                {
                    editTextfName.setError("Please Enter Client's First Name");
                    return;
                }

                else if (TextUtils.isEmpty(lname))
                {
                    editTextlName.setError("Please Enter Client's Last Name");
                    return;
                }

                else if (TextUtils.isEmpty(meter))
                {
                    editTextmeterno.setError("Please Enter Meter Number");
                    return;
                }
                else if (!(meter.length() ==12))
                {
                    editTextmeterno.setError("Meter Number Must be Exactly 12 didits");
                    errortoast("Meter Number Must be Exactly 12 didits");

                    return;
                }

                else if (inst_date.equals("Installation Date"))
                {
                    errortoast("Please Select Installation date");
                    return;
                }

                else if (!fname.matches("[a-zA-Z]+"))
                {
                    editTextfName.setError("Enter a Valid Client's First Name");
                    return;
                }

                else if (!lname.matches("[a-zA-Z]+"))
                {
                    editTextlName.setError("Enter a Valid Client's First Name");
                    return;
                }

                else
                {
                    editTextfName.setError(null);
                    editTextmeterno.setError(null);
                    ConfirmDialog();
                }

            }
        });


        }
public void errortoast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.errortoast,
        (ViewGroup) findViewById(R.id.toast_layout_root));
        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_close);
        TextView text =  layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        }
private void insertUser(){
    session = new UserSessionManager(getApplicationContext());

    //Here we will handle the http request to insert user to mysql db
    //Creating a RestAdapter
    RestAdapter adapter = new RestAdapter.Builder()
            .setEndpoint(ROOT_URL) //Setting the Root URL
            .build(); //Finally building the adapter

    // Set up progress before call
    final ProgressDialog progressDoalog;
    progressDoalog = new ProgressDialog(AddnewClient.this);
    progressDoalog.setMessage("Please Wait....");
    progressDoalog.setTitle("Registering new Client");
    progressDoalog.setCancelable(false);
    progressDoalog.setIndeterminate(true);
    progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    // show it
    progressDoalog.show();


    //Creating object for our interface
    RegisterAPI api = adapter.create(RegisterAPI.class);
    //Defining the method insertuser of our interface


    String full= fname+" "+lname;
    api.insertUser(
            //Passing the values by getting it from editTexts
            full,
            editTextphone.getText().toString().trim(),
            editTextmeterno.getText().toString().trim(),
            inst_date,
            editTextplot.getText().toString().trim(),
            editTextremark.getText().toString().trim(),
            //Creating an anonymous callback
            new Callback<Response>() {
                @Override
                public void success(Response result, Response response) {

                    progressDoalog.dismiss();

                    //On success we will read the server's output using bufferedreader
                    //Creating a bufferedreader object
                    BufferedReader reader = null;
                    //An string to store output from the server
                    String output = "";
                    try {
                        //Initializing buffered reader
                        reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                        //Reading the output in the string
                        output = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (output.equals("New Client Successfully Registered"))
                    {
                        //Displaying the output as a toast
                        new LovelyStandardDialog(AddnewClient.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.green_500)
                                .setTopTitle("Server Response")
                                .setTitle("SUCCESS!").setTitleGravity(Gravity.CENTER).setTopTitleColor(R.color.mdtp_white)
                                .setButtonsColorRes(R.color.green_500)
                                .setIcon(R.drawable.ic_done_black_24dp)
                                .setMessage(output).setMessageGravity(Gravity.CENTER)
                                .setCancelable(false)
                                .setPositiveButton("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }
                    else {
                        new LovelyStandardDialog(AddnewClient.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.red_400)
                                .setButtonsColorRes(R.color.red_400)
                                .setIcon(R.drawable.ic_close_black_24dp)
                                .setTopTitle("Server Response")
                                .setTitle("FAILED!").setTitleGravity(Gravity.CENTER).setTopTitleColor(R.color.mdtp_white)
                                .setMessage(output).setMessageGravity(Gravity.CENTER)
                                .setCancelable(false)
                                .setPositiveButton("Try Again", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDoalog.dismiss();

                    //If any error occured displaying the error as toast
                    //Toast.makeText(Home.this, error.toString(),Toast.LENGTH_LONG).show();
                    errortoast(error.toString());

                }
            }
    );
}
    private void ConfirmDialog() {
        final Dialog dialog = new Dialog(AddnewClient.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.confirmdetails);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.firstname)).setText(fname+" "+lname);
        ((TextView) dialog.findViewById(R.id.phone)).setText(phone);
        ((TextView) dialog.findViewById(R.id.meterno)).setText(meter);
        ((TextView) dialog.findViewById(R.id.ins)).setText(inst_date);
        ((TextView) dialog.findViewById(R.id.plott)).setText(plot);
        ((TextView) dialog.findViewById(R.id.rem)).setText(remarkk);

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (internetAvailable())
                {
                    insertUser();
                    dialog.dismiss();



                }
                else
                {
                    new LovelyStandardDialog(AddnewClient.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                            .setTopColorRes(R.color.colorPrimary)
                            .setButtonsColorRes(R.color.cyan_700)
                            .setIcon(R.drawable.ic_help_outline_black_24dp)
                            .setTitle("No Internet").setTitleGravity(Gravity.CENTER)
                            .setMessage("Please Check Your Internet Connection and try again").setMessageGravity(Gravity.CENTER)
                            .setCancelable(false)
                            .setPositiveButton("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            })
                            .show();

                }



            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Client "+fname+" details was Dismissed", Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);

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
}

