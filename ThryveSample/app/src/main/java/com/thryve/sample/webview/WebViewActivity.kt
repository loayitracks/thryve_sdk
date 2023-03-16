package com.thryve.sample.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.thryve.connector.sdk.CoreConnector
import com.thryve.sample.MainActivity
import com.thryve.sample.R

open class WebViewActivity : AppCompatActivity() {
    @JvmField
	var webView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        webView = findViewById(R.id.web_view)
        loadWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    open fun loadWebView() {
        CoreConnector(this, MainActivity.APP_ID, MainActivity.partnerUserId).handleDataSourceConnection(webView!!)
    }
}