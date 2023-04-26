package com.example.myloginapp;

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.myloginapp.databinding.ActivityMain2Binding


class MainActivityWebview : AppCompatActivity()  , VirusTotalChecker.VirusTotalCheckListener {

    private lateinit var binder: ActivityMain2Binding
     var userDao: WebHistoryDao? = null
    var  title  : String = "";
    var url :String ="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binder.root)


        //val userDao = WebHistoryDatabase.getInstance(this)?.webHistoryDao()
        var mWebViewClient: WebViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                userDao = WebHistoryDatabase.getInstance(applicationContext)?.webHistoryDao()
                title= view.title.toString();
                this@MainActivityWebview.url = url;
                VirusTotalChecker.checkUrl(url, this@MainActivityWebview)
            }
        }
        binder.wvGoogleSearch.webViewClient = mWebViewClient
        binder.wvGoogleSearch.settings.javaScriptEnabled = true

    }

    override fun onResume() {
        super.onResume()
        binder.wvGoogleSearch.loadUrl("https://www.google.com/")
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action === KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK -> {
                    if (binder.wvGoogleSearch.canGoBack()) {
                        binder.wvGoogleSearch.goBack()
                    } else {
                        finish()
                    }
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCheckComplete(isMalicious: Boolean) {
        val result = if (isMalicious) "Malicious" else "Safe"
        if(title.equals("Google")){
          return
        }

        userDao?.insert(WebHistory(null, title!!, url , result))
        for (i in 0 until userDao?.getAll()!!.size) {
            Log.i(
                "HistoryTabsRoom",
                "history No ${i}: \n Title : ${
                    userDao!!.getAll()?.get(i)?.title
                } \n URL : ${
                    userDao!!.getAll().get(i).url
                } \n _______________________________________________________________________________"
            )
        }
    }

}

