package org.folk.younoon;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivity extends AppCompatActivity implements  AdvancedWebView.Listener{
    LinearLayout linearLayout;
    private AdvancedWebView webView;
    String url = "https://younoon.ir/";
    boolean doubleBackToExitPressedOnce = false;
    boolean goToIntro = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        findId();
        websetting();
        url = getIntent().getStringExtra("url");
        goToIntro = getIntent().getBooleanExtra("intro",false);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        load();
    }

    public void findId(){
        linearLayout = findViewById(R.id.pgData);
    }

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

    public void load(){
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (!isNetworkConnected()){
                    showBottomSheetDialogFragment();
                }
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

    @Override
    public void onPageStarted(String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(String url) {

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
