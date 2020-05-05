package com.kip.gillz.amigo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.kip.gillz.amigo.AdminCheck.WebBtask;
import com.kip.gillz.amigo.Myread.Myreadings;
import com.kip.gillz.amigo.utils.Tools;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import java.util.Calendar;
import java.util.HashMap;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Dashboard.OnFragmentInteractionListener,Myreadings.OnFragmentInteractionListener{
    UserSessionManager session;
    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    TextView textView1,textView2;
    String Username,Password;
    TextView userid;
    MaterialRippleLayout materialRippleLayout,chgpass;
    Button btnchangepass,btnviewreadings;
    EditText et1,et2,et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Welcome To Tal App");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
        Toast.makeText(getApplicationContext(), "Some Fuctionalities are Still Under development. Thank you", Toast.LENGTH_LONG).show();

        progress_bar = findViewById(R.id.progress_bar);
        session = new UserSessionManager(getApplicationContext());
        if (session.checkLogin()) {
            finish();
        }
            session = new UserSessionManager(getApplicationContext());
            HashMap<String, String> user = session.getUserDetails();
            Username = user.get(UserSessionManager.KEY_ADMIN_USERNAME);
            Password = user.get(UserSessionManager.KEY_ADMIN_PASSWORD);

        // Auto refresh testing code
        //==============================================================================
       /* final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                searchAction();
                handler.postDelayed(this,  5000);
            }
        };
        handler.postDelayed(refresh, 5000);*/
        //=============================================================================


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textnavhead);
        txtProfileName.setText("You Are Logged In As "+Username);


// on page opened it displays scan meter readings on the content main
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Dashboard dashboard = new Dashboard();
            ft.replace(R.id.frame_container,dashboard);
            ft.addToBackStack("Main_Dashboard");
            ft.commit();



    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            doExitApp();
        }

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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.dash) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Dashboard dashboard = new Dashboard();
            ft.replace(R.id.frame_container,dashboard);
            ft.addToBackStack("Main_Dashboard");
            ft.commit();

        }
        else if (id == R.id.webpage) {
            if (internetAvailable())
                 {
                WebBtask webBtask = new WebBtask(MainActivity.this);
                webBtask.execute(Username,Password);
                 }
            else
                 {
               nointernet();
                 }

        }

        else if (id == R.id.feedback)
        {
            feedback();
        }
        else if (id == R.id.developers) {
            if (internetAvailable()) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.tal.co.ke/"));
                startActivity(i);
            } else {
                nointernet();

            }
        }

        else if (id == R.id.nav_about) {
            showAbout();

        }
        else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "Share Coming Soon", Toast.LENGTH_LONG).show();

        }
        else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

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
                changepsw();

            }
        });

        btnviewreadings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //viewread();

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Myreadings dashboard = new Myreadings();
                ft.replace(R.id.frame_container,dashboard);
                ft.addToBackStack("Main_Dashboard");
                ft.commit();

                dialog.dismiss();

            }
        });
        materialRippleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    public void feedback() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.feedback);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity= Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        Button bt1,bt2;
        bt1=dialog.findViewById(R.id.bt_decline);
        bt2=dialog.findViewById(R.id.sendfeedback);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetAvailable())
                {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Thanks "+ Username+"  For Your Feedback", Toast.LENGTH_LONG).show();
                    searchAction();
                }
                else
                {
                    nointernet();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        //fab.setAlpha(0f);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                //fab.setAlpha(1f);
                Toast.makeText(getApplicationContext(), "Your Feedback Has Been Sent", Toast.LENGTH_LONG).show();

            }
        }, 3000);
    }
    public boolean internetAvailable() {
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
    private void nointernet(){
            new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                    .setTopColorRes(R.color.colorPrimary)
                    .setButtonsColorRes(R.color.cyan_700)
                    .setIcon(R.drawable.ic_help_outline_black_24dp)
                    .setTitle("No Internet").setTitleGravity(Gravity.CENTER)
                    .setMessage("Please Check Your Internet Connection and try again").setMessageGravity(Gravity.CENTER)
                    .setCancelable(false)
                    .setPositiveButton("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
    }
    public void changepsw() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.changepass);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity= Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        et1= dialog.findViewById(R.id.oldpass);
        et2= dialog.findViewById(R.id.newpass);
        et3= dialog.findViewById(R.id.cnewpass);
        chgpass= dialog.findViewById(R.id.btnchg);

        chgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Oldp,Newp,Cnewp;
                Oldp= et1.getText().toString().trim();
                Newp= et2.getText().toString().trim();
                Cnewp= et3.getText().toString().trim();

                if (TextUtils.isEmpty(Oldp)&&TextUtils.isEmpty(Newp)&&TextUtils.isEmpty(Cnewp)) {

                    et1.setError("Old Password is Required");
                    et2.setError("New Password is Required");
                    et3.setError("Confirm New Password Required");

                }

                else if (TextUtils.isEmpty(Oldp))
                {
                    et1.setError("Please Enter Your Old Password");

                }
                else if (TextUtils.isEmpty(Newp))
                {
                    et2.setError("Please Enter Your New password");

                }
                else if (TextUtils.isEmpty(Cnewp))
                {
                    et3.setError("Please Confirm the password");

                }
                else {
                    et1.setError(null);
                    et2.setError(null);
                    et3.setError(null);

                    if (!(Newp.equals(Cnewp)))
                    {

                        new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.red_400)
                                .setButtonsColorRes(R.color.colorPrimary)
                                .setIcon(R.drawable.ic_close)
                                .setTitle("Your New Passwords Does Not Match").setTitleGravity(Gravity.CENTER)
                                .setTopTitle("NEW PASSWORD ERROR")
                                .setTopTitleColor(R.color.white)
                                .setMessage("Please check your new password and confirm new password fields and try again").setMessageGravity(Gravity.CENTER)
                                .setCancelable(false)
                                .setPositiveButton("Close dialog", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();
                    }
                    else if (!(Password.equals(Oldp)))
                    {
                        new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                .setTopColorRes(R.color.red_400)
                                .setButtonsColorRes(R.color.colorPrimary)
                                .setIcon(R.drawable.ic_close)
                                .setTitle("You Entered a Wrong Old Password").setTitleGravity(Gravity.CENTER)
                                .setTopTitle("OLD PASSWORD ERROR")
                                .setTopTitleColor(R.color.white)
                                .setMessageGravity(Gravity.CENTER)
                                .setMessage("Please Check your old password and try again").setMessageGravity(Gravity.CENTER)
                                .setCancelable(false)
                                .setPositiveButton("Close this dialog", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                })
                                .show();

                    }

                    else
                        {
                            new LovelyStandardDialog(MainActivity.this, LovelyStandardDialog.ButtonLayout.VERTICAL)
                                    .setTopColorRes(R.color.green_500)
                                    .setTopTitle("PASSWORD SUCCESSFULLY CHANGED")
                                    .setTitle("YOUR NEW PASSWORD IS "+ Newp).setTitleGravity(Gravity.CENTER).setTopTitleColor(R.color.mdtp_white)
                                    .setButtonsColorRes(R.color.green_500)
                                    .setIcon(R.drawable.ic_done_black_24dp)
                                    .setMessage("Your Password has been successfully Changed").setMessageGravity(Gravity.CENTER)
                                    .setCancelable(false)
                                    .setPositiveButton("Close", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    })
                                    .show();                        }


                }


            }
        });



        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public void viewread() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.viewdialog);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.gravity= Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((Button) dialog.findViewById(R.id.datefrom)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cur_calender = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long date_ship_millis = calendar.getTimeInMillis();
                                ((TextView) dialog.findViewById(R.id.from)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                            }
                        },
                        cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH)


                );
                //set dark light
                datePicker.setThemeDark(true);
                datePicker.setTitle("Date From");
                datePicker.setCancelColor(getResources().getColor(R.color.red_400));
                datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setMaxDate(cur_calender);
                cur_calender.add(Calendar.DAY_OF_MONTH,-30);
                datePicker.setMinDate(cur_calender);

                datePicker.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        ((Button) dialog.findViewById(R.id.dateto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cur_calender = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long date_ship_millis = calendar.getTimeInMillis();
                                ((TextView) dialog.findViewById(R.id.to)).setText(Tools.getFormattedDateSimple(date_ship_millis));
                            }
                        },
                        cur_calender.get(Calendar.YEAR),
                        cur_calender.get(Calendar.MONTH),
                        cur_calender.get(Calendar.DAY_OF_MONTH)

                );

                //set dark light
                datePicker.setThemeDark(true);
                datePicker.setTitle("Date To");
                datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setMaxDate(cur_calender);
                cur_calender.add(Calendar.DAY_OF_MONTH,-30);
                datePicker.setMinDate(cur_calender);
                datePicker.show(getFragmentManager(), "Datepickerdialog");            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void showAbout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.tv_version)).setText(BuildConfig.VERSION_NAME);

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        (dialog.findViewById(R.id.terms)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Terms of services coming soon", Toast.LENGTH_LONG).show();

            }
        });

        (dialog.findViewById(R.id.privacy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacypolicyDialog();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void PrivacypolicyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.privacypolicy);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
