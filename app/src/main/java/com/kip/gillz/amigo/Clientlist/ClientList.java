package com.kip.gillz.amigo.Clientlist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kip.gillz.amigo.R;
import com.kip.gillz.amigo.adapter.AdapterListAnimation;
import com.kip.gillz.amigo.model.People;
import com.kip.gillz.amigo.utils.ItemAnimation;
import com.kip.gillz.amigo.utils.Tools;

import java.util.ArrayList;
import java.util.List;


public class ClientList extends AppCompatActivity {
    private View parent_view;
    //UserSessionManager session;
    private RecyclerView recyclerView;
    private AdapterListAnimation mAdapter;
    private List<People> items = new ArrayList<>();

    CAdapter cAdapter;
    ArrayList<String> meter_no;

    private int animation_type = ItemAnimation.BOTTOM_UP;
    private View lyt_mic;
    private View lyt_call;
    private boolean rotate = false;
    private View back_drop;
    private EditText editTextfName;
    private EditText editTextlName;    ;
    private EditText editTextphone;
    private EditText editTextmeterno;
    private EditText editTextEmail;
    EditText search;
    String Username;
    private ImageButton btdismis;
    private Button reg;
    public static final String ROOT_URL = "https://tal.co.ke/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);
        parent_view = findViewById(android.R.id.content);
        //back_drop = findViewById(R.id.back_drop);

        search = findViewById(R.id.search);
        //new
        meter_no = new ArrayList<>();
        initToolbar();
        setAdapter();

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registered Clients");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }
    private void initComponent() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
       // items = DataGenerator.getPeopleData(this);

        animation_type = ItemAnimation.FADE_IN;
//==============================================================================
        final Handler handler = new Handler();
        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                setAdapter();
                handler.postDelayed(this,  5000);
            }
        };
        handler.postDelayed(refresh, 5000);
//=============================================================================

        setAdapter();
    }
    private void setAdapter() {
        animation_type = ItemAnimation.FADE_IN;
        Clientlistbacktask aaa =new Clientlistbacktask(this);
        aaa.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_animation, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //finish();
                break;
            case R.id.action_refresh:
                setAdapter();

                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private static final String[] ANIMATION_TYPE = new String[]{
            "Bottom Up", "Fade In", "Left to Right", "Right to Left"
    };
    String fname,lname,mail,phone,meter;
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
