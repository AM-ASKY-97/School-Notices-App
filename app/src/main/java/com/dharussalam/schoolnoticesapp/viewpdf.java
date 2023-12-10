package com.dharussalam.schoolnoticesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class viewpdf extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpdf);

        webView = findViewById(R.id.viewpdf);

        webView.getSettings().setJavaScriptEnabled(true);

        String name = getIntent().getStringExtra("tittle");
        String fileurl = getIntent().getStringExtra("fileurl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(name);
        pd.setMessage("Opening...!");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String encodedUrl = "";
        try {
            encodedUrl = URLEncoder.encode(fileurl, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String googleDriveUrl = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + encodedUrl;
        webView.loadUrl(googleDriveUrl);
    }
}
