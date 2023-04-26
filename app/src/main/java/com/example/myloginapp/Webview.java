package com.example.myloginapp;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Webview extends AppCompatActivity {

    private WebView mSuperSafeWebView;

    private boolean mSafeBrowsingIsInitialized;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webiew);

        mSuperSafeWebView = findViewById(R.id.mWebview);
        mSuperSafeWebView.setWebViewClient(new MyWebViewClient());
        mSafeBrowsingIsInitialized = false;
        mSuperSafeWebView.loadUrl("https://www.google.com/");
        getBackForwardList();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            mSuperSafeWebView.startSafeBrowsing(this, new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean success) {

                    mSafeBrowsingIsInitialized = true;
                    if (!success) {
                        Log.e("MY_APP_TAG", "Unable to initialize Safe Browsing!");
                    }
                }
            });
        }
    }

    public void getBackForwardList(){
        WebBackForwardList currentList = mSuperSafeWebView.copyBackForwardList();
        int currentSize = currentList.getSize();
        for(int i = 0; i < currentSize; ++i)
        {
            WebHistoryItem item = currentList.getItemAtIndex(i);
            String url = item.getUrl();
            System.out.printf("The URL at index: " + Integer.toString(i) + " is " + url );
        }
    }
}
