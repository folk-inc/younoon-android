package org.folk.younoon;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.farsitel.bazaar.IUpdateCheckService;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity {
    private AdvancedWebView webView;
    LinearLayout linearLayout;
    String url = "https://younoon.ir/";
    boolean doubleBackToExitPressedOnce = false;
    IUpdateCheckService service;
    UpdateServiceConnection connection;
    private static final String TAG = "UpdateCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ((YounoonApplication) getApplication()).refreshLocale(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initService();
    	Intent intent;
    	intent = new Intent(MainActivity.this, WebViewActivity.class);

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

    class UpdateServiceConnection implements ServiceConnection {
		public void onServiceConnected(ComponentName name, IBinder boundService) {
			service = IUpdateCheckService.Stub
					.asInterface((IBinder) boundService);
			try {
				long vCode = service.getVersionCode("org.folk.younoon");
				Toast.makeText(MainActivity.this, "Version Code:" + vCode,
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d(TAG, "onServiceConnected(): Connected");
		}

		public void onServiceDisconnected(ComponentName name) {
			service = null;
			Log.d(TAG, "onServiceDisconnected(): Disconnected");
		}
	}
	private void initService() {
		Log.i(TAG, "initService()");
		connection = new UpdateServiceConnection();
		Intent i = new Intent(
				"com.farsitel.bazaar.service.UpdateCheckService.BIND");
		i.setPackage("com.farsitel.bazaar");
		boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
		Log.d(TAG, "initService() bound value: " + ret);
	}

	/** This is our function to un-binds this activity from our service. */
	private void releaseService() {
		unbindService(connection);
		connection = null;
		Log.d(TAG, "releaseService(): unbound.");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseService();
	}
}