package com.rain.suspensiondemo;

import android.app.Application;

/**
 * Author:rain
 * Date:2018/10/26 15:01
 * Description:
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyActivityLifecycleCallBack lifecycleCallBack = new MyActivityLifecycleCallBack();
        registerActivityLifecycleCallbacks(lifecycleCallBack);
        registerComponentCallbacks(lifecycleCallBack);
    }
}
