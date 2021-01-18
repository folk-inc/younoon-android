package org.folk.younoon;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class YounoonApplication extends Application {

    public static final String TAG = "amingoli";
    private RequestQueue mRequestQueue;
    private static YounoonApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        setFont();
    }

    public static synchronized YounoonApplication getInstance() {
        return mInstance;
    }

    public  RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

//    public void refreshLocale(@NonNull Context context) {
//        final String language = AppManager.getAppLanguage(this);
//
//        final Locale locale;
//        if (language != null) {
//            locale = new Locale(language);
////      locale = new Locale("ar");
//        } else {
//            // nothing to do...
//            return;
//        }
//
//        updateLocale(context, locale);
//        final Context appContext = context.getApplicationContext();
//        if (context != appContext) {
//            updateLocale(appContext, locale);
//        }
//
//        Api.endPoint(getApplicationContext(), status
//                -> Api.android(getApplicationContext(), status1
//                -> {
//            Log.d(TAG, "refreshLocale");
//        }));
//    }
//
//    private void updateLocale(@NonNull Context context, @NonNull Locale locale) {
//        final Resources resources = context.getResources();
//        Configuration config = resources.getConfiguration();
//        config.locale = locale;
//        if (Build.VERSION.SDK_INT >= 21) {
//            config.setLayoutDirection(config.locale);
//        }
//        resources.updateConfiguration(config, resources.getDisplayMetrics());
//        setFont();
//    }
//
//    private void setFont(){
//        String appLanguage = AppManager.getAppLanguage(getApplicationContext());
//        if (appLanguage!=null && appLanguage.equals("fa")){
//            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "iranyekan_regular.ttf");
//        }else {
//            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "iranyekan_regular.ttf");
//        }
//    }
}