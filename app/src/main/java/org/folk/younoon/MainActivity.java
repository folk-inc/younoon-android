package org.folk.younoon;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;
import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity {
    AdvancedWebView webView;
    LinearLayout linearLayout;
    String url = "https://younoon.ir/";
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findID();
        websetting();
        webView.loadUrl(url);
        load();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void findID(){
        webView = findViewById(R.id.webview);
        linearLayout = findViewById(R.id.pgData);
    }

    public void load(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap facIcon) {
                linearLayout.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                linearLayout.setVisibility(View.GONE);
            }
        });
    };
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    public void websetting(){
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setSupportZoom(false);
        webSetting.setSavePassword(true);
        webSetting.setBlockNetworkImage(false);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setSupportZoom(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setScrollContainer(false);
        webView.addJavascriptInterface(this, "jsinterface");
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.backMsg, Toast.LENGTH_SHORT).show();
        }
    }
}