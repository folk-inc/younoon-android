package org.folk.younoon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivity extends AppCompatActivity implements  AdvancedWebView.Listener{
    LinearLayout linearLayout;
    private AdvancedWebView webView;
    String TAG = "folk-inc";
    String url = "https://younoon.ir/";
    boolean doubleBackToExitPressedOnce = false;
    boolean goToIntro = false;
    private ProgressBar progress;
    boolean bottomSheetIsShow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView.loadUrl(url);
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

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    public void showBottomSheetDialogFragment() {
        if (!bottomSheetIsShow){
            progress.setVisibility(View.GONE);
            bottomSheetIsShow = true;
            webView.stopLoading();
            webView.setVisibility(View.GONE);
            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.setCancelable(false);
            bottomSheetFragment.setListener(() -> {
                bottomSheetIsShow = false;
                webView.reload();
                bottomSheetFragment.dismiss();
            });
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        }
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        progress.setVisibility(View.VISIBLE);
        if (!isNetworkConnected()) showBottomSheetDialogFragment();
        Log.d(TAG, "onPageStarted: "+url);
    }

    @Override
    public void onPageFinished(String url) {
        Log.d(TAG, "onPageFinished: "+url);
        progress.setVisibility(View.GONE);
        if (isNetworkConnected())
            if (webView.getVisibility() != View.VISIBLE)
                webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        if (description.equals("net::ERR_INTERNET_DISCONNECTED")){
            showBottomSheetDialogFragment();
        }
        if (errorCode == -10 || description.equals("net::ERR_UNKNOWN_URL_SCHEME")){
            webView.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onPageError: "+errorCode+"\n"+description+"\n"+failingUrl);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }
}
