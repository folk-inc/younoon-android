package org.folk.younoon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    LinearLayout linearLayout;
    String url = "https://qhkarimeh.ir/";
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);
        linearLayout = findViewById(R.id.pgdata);
        webView.loadUrl(url);
        websetting();
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        load();
    }
    public void load(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                linearLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
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
        webSetting.setSavePassword(false);
        webSetting.setBlockNetworkImage(false);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setAppCacheEnabled(true);
        webSetting.setSupportZoom(false);
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setBuiltInZoomControls(true);
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        webView.setScrollContainer(false);
        webView.addJavascriptInterface(this, "jsinterface");
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
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