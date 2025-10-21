
package com.aasl.padashboard

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.webkit.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var webView: WebView
    private val dashboardUrl = "http://10.64.10.178:5000"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        webView = WebView(this)
        setContentView(webView)

        val s = webView.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.databaseEnabled = true
        s.cacheMode = WebSettings.LOAD_NO_CACHE
        s.setSupportZoom(true)
        s.builtInZoomControls = true
        s.displayZoomControls = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            s.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        WebView.setWebContentsDebuggingEnabled(true)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) = false
        }
        webView.webChromeClient = WebChromeClient()

        if (savedInstanceState == null) webView.loadUrl(dashboardUrl) else webView.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }
}
