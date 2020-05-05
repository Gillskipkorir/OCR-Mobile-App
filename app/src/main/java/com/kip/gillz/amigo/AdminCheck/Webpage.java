package com.kip.gillz.amigo.AdminCheck;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.kip.gillz.amigo.R;

public class Webpage extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progress_bar;
    private LinearLayout lyt_no_connection;
    private AppCompatButton bt_retry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webpage);
        if (internetAvailable())
        {
            webView = findViewById(R.id.web);
            WebSettings webSettings =webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("https://tal.co.ke");
            webView.setWebViewClient(new WebViewClient()
             {

                 ProgressDialog prDialog;
                 @Override
                 public void onPageStarted(WebView view, String url, Bitmap favicon)
                 {
                     prDialog = ProgressDialog.show(Webpage.this, null, "Loading Tal page, Please wait...");
                     super.onPageStarted(view, url, favicon);
                 }

                 @Override
                 public void onPageFinished(WebView view, String url)
                 {
                     prDialog.dismiss();
                     super.onPageFinished(view, url);
                 }
                                     }

            );
        }
        else  {
            initComponent();

        }
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
        {
            webView.goBack();
        }
        else
        {
            super.onBackPressed();
        }
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
    private void initComponent() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.activity_no_item_internet_image);
        dialog.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        progress_bar = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        lyt_no_connection = (LinearLayout) dialog.findViewById(R.id.lyt_no_connection);
        bt_retry = (AppCompatButton) dialog.findViewById(R.id.bt_retry);
        progress_bar.setVisibility(View.GONE);
        lyt_no_connection.setVisibility(View.VISIBLE);
        bt_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress_bar.setVisibility(View.VISIBLE);
                lyt_no_connection.setVisibility(View.GONE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_bar.setVisibility(View.GONE);
                        lyt_no_connection.setVisibility(View.VISIBLE);
                    }
                }, 1000);
                maintaskk();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    private void maintaskk(){
        if (internetAvailable())
        {
            webView = findViewById(R.id.web);
            WebSettings webSettings =webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("https://tal.co.ke");
            webView.setWebViewClient(new WebViewClient()
             {

                 ProgressDialog prDialog;
                 @Override
                 public void onPageStarted(WebView view, String url, Bitmap favicon)
                 {
                     prDialog = ProgressDialog.show(Webpage.this, null, "Loading Tal page, Please wait...");
                     super.onPageStarted(view, url, favicon);
                 }

                 @Override
                 public void onPageFinished(WebView view, String url)
                 {
                     prDialog.dismiss();
                     super.onPageFinished(view, url);
                 }
             }

            );
        }
        else  {
            initComponent();

        }

    }

}
