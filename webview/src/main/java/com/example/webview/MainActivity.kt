package com.example.webview

import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

// For Javascript and Native component communication
// https://medium.com/@elye.project/making-android-interacting-with-web-app-921be14f99d8

// Just for reference :
// http://droidmentor.com/bind-javascript-to-android/
// https://medium.com/mindorks/webview-love-it-or-hate-it-but-you-cant-ignore-it-d471bc95d81e


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val WIKI_URL = "http://www.wikipedia.com"
    private val GOOGLE_URL = "http://www.google.com"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        webView = web_view

        // Loading Website in Webview

        loadWebSite()

        // Loading Website in Browser i.e showing site through default browser applications
        //val uri = Uri.parse(WIKI_URL)
        //val intent = Intent(Intent.ACTION_VIEW, uri)
        //startActivity(intent)

    }


    fun loadWebSite() {

        btn_ok.visibility = View.VISIBLE
        btn_cancel.visibility = View.VISIBLE
        btn_sendToWebView.visibility = View.GONE

        //webView.loadUrl(WIKI_URL)
        webView.loadUrl(GOOGLE_URL)
        // Loading through the webview
        initWebView()

    }

    private fun initWebView() {

        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progress_bar.let {
                    progress_bar.progress = newProgress
                }
            }
        }


        web_view.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress_bar.visibility = View.VISIBLE
                invalidateOptionsMenu()
            }


            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                //return super.shouldOverrideUrlLoading(view, request)
                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                web_view.loadUrl(request?.url.toString())
                // }
                return true
            }

            @SuppressWarnings("deprecation")
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                // return super.shouldOverrideUrlLoading(view, url)
                web_view.loadUrl(url)
                return true
            }

            /*override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progress_bar.visibility = View.GONE
                btn_ok.isEnabled = true
                btn_cancel.isEnabled = true
                invalidateOptionsMenu()
            }*/

            // If you want to handle the elements of the website/url i.e. hide etc
            // here you may notice some delay in the JavaScript execution because
            // the code is only being executed after the page has finished loading.

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideSearch(url)
                btn_ok.isEnabled = true
                btn_cancel.isEnabled = true

                // Adding delay in showing the webview to overcome the lag by hideSearch() method
                Handler().postDelayed({
                    progress_bar.visibility = View.GONE
                    web_view.visibility = View.VISIBLE
                }, 1000)

                invalidateOptionsMenu()
            }


            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                //super.onReceivedSslError(view, handler, error)
                /* if( Debug version ){

                    handler?.proceed()  // Ignoring the SSL certificates error
                }else
                {*/
                handler?.cancel()
                //  }

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                progress_bar.visibility = View.GONE
                invalidateOptionsMenu()
            }
        }

        web_view.settings.loadWithOverviewMode = true
        web_view.settings.useWideViewPort = true            // true -> page will load its original size , false -> page will contain within the dimension of device
        web_view.clearCache(true)
        web_view.clearHistory()
        web_view.settings.javaScriptEnabled = true
        web_view.settings.domStorageEnabled = true
        web_view.settings.loadWithOverviewMode = true
        web_view.isVerticalScrollBarEnabled = true
        web_view.isHorizontalScrollBarEnabled = true
    }

    fun hideSearch(url: String?) {

        var url_string: String? = "javascript:(function() { " + "var element = document.getElementById('hplogo');" + "element.parentNode.removeChild(element);" + "})()"


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            web_view.evaluateJavascript( url_string)

        }else{*/

        web_view.loadUrl(url_string)
        //}

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        if (!web_view.canGoBack()) {
            menu?.getItem(3)?.isEnabled = false
            menu?.getItem(3)?.icon?.alpha = 120
        } else {

            menu?.getItem(3)?.isEnabled = true
            menu?.getItem(3)?.icon?.alpha = 255
        }


        if (!web_view.canGoForward()) {

            menu?.getItem(4)?.isEnabled = false
            menu?.getItem(4)?.icon?.alpha = 120
        } else {

            menu?.getItem(4)?.isEnabled = true
            menu?.getItem(4)?.icon?.alpha = 255
        }

        return super.onPrepareOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            R.id.action_settings -> true

            R.id.action_load_html_page -> {
                loadHtmlPage()
                return true
            }

            R.id.action_load_url -> {
                loadWebSite()
                return true
            }

            //R.id.action_bookmark -> true
            R.id.action_backward -> goBackward()

            R.id.action_forward -> goForward()

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goBackward(): Boolean {

        if (web_view.canGoBack()) {
            web_view.goBack()
        }
        return true
    }

    private fun goForward(): Boolean {

        if (web_view.canGoForward()) {
            web_view.goForward()
        }
        return true
    }


    /* <!--  All Javascript handling work -->*/


    fun loadHtmlPage() {

        btn_ok.visibility = View.GONE
        btn_cancel.visibility = View.GONE
        btn_sendToWebView.visibility = View.VISIBLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE)) {
                WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        web_view.settings.javaScriptEnabled = true
        web_view.addJavascriptInterface(JavaScriptInterface(), JAVASCRIPT_OBJ)
        web_view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (url == BASE_URL) {
                    injectJavaScriptFunction()
                }
            }
        }


        // Either load the Javascript using loadUrl from webview
        //    or use evaluateJavascript function
        web_view.loadUrl(BASE_URL)


        // From native to Javascript ( calling javascript "updateFromAndroid" function )
        btn_sendToWebView.setOnClickListener {
            web_view.evaluateJavascript("javascript: " +
                    "updateFromAndroid(\"" + edit_text_to_web.text + "\")", null)
        }
    }

    override fun onDestroy() {
        web_view.removeJavascriptInterface(JAVASCRIPT_OBJ)
        super.onDestroy()
    }

    private fun injectJavaScriptFunction() {
        web_view.loadUrl("javascript: " +
                "window.androidObj.textToAndroid = function(message) { " +
                JAVASCRIPT_OBJ + ".textFromWeb(message) }")
    }


    // From WebApp javascript to native i.e. getting text from webApp by interface
    private inner class JavaScriptInterface {
        @JavascriptInterface
        fun textFromWeb(fromWeb: String) {
            txt_from_web.text = fromWeb
        }
    }

    companion object {
        private val JAVASCRIPT_OBJ = "javascript_obj"
        private val BASE_URL = "file:///android_asset/webview.html"
    }

}

