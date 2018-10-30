package com.rain.suspensiondemo;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.rain.suspensiondemo.suspension.WindowShowService;
import com.rain.suspensiondemo.util.SPUtils;

/**
 * Author:rain
 * Date:2018/10/26 15:03
 * Description:
 * ActivityLifecycleCallbacks 管理所有activity的生命周期
 * ComponentCallbacks2 内存管理
 * 观察app进入前台后台的两种方式：
 */
public class MyActivityLifecycleCallBack implements Application.ActivityLifecycleCallbacks,ComponentCallbacks2 {
    private static final String TAG  = "LifecycleCallBack";
    private boolean isBackground = false;
    private int startCount;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        // 方式一
        startCount++;
        if (startCount == 1) {
            Log.e(TAG, "onActivityStarted: 应用进入到前台");
            String jumpUrl = (String) SPUtils.get(activity, "jumpUrl", "");
            if (!TextUtils.isEmpty(jumpUrl)) {
                activity.startService(new Intent(activity, WindowShowService.class));
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        if (!isBackground) {
//            isBackground = true;
//            Log.e(TAG, "onActivityResumed: 应用进入到前台");
//        }


    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        startCount--;
        if (startCount == 0) {
            Log.e(TAG, "应用进入后台: ");
            activity.stopService(new Intent(activity, WindowShowService.class));
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onTrimMemory(int level) {
//        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
//            Log.e(TAG, "应用进入后台: ");
//            isBackground = false;
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
