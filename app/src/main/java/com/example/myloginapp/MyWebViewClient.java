package com.example.myloginapp;

import android.os.Build;
import android.webkit.SafeBrowsingResponse;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient  extends WebViewClient {
    // Automatically go "back to safety" when attempting to load a website that
    // Google has identified as a known threat. An instance of WebView calls
    // this method only after Safe Browsing is initialized, so there's no
    // conditional logic needed here.
    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request,
                                  int threatType, SafeBrowsingResponse callback) {
        // The "true" argument indicates that your app reports incidents like
        // this one to Safe Browsing.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            callback.backToSafety(true);
        }
        Toast.makeText(view.getContext(), "Unsafe web page blocked.",
                Toast.LENGTH_LONG).show();
    }
}