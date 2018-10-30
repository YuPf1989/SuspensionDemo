package com.rain.suspensiondemo.activity_result;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Author:rain
 * Date:2018/10/29 10:45
 * Description:
 * 专门用于处理startActivityForResult的工具类
 * 参见：https://github.com/AnotherJack/AvoidOnResult
 */
public class ActivityResultManager {
    private  BindFragment bindFragment;
    private static final String BINDFRAGMENT = "BINDFRAGMENT";

    public ActivityResultManager(FragmentActivity activity) {
        bindFragment = bindFragment(activity);
    }

    public ActivityResultManager(Fragment fragment) {
        this(fragment.getActivity());
    }

    public void startForResult(Intent intent, int requestCode, IResultCallback callback) {
        bindFragment.startForResult(intent, requestCode, callback);
    }

    public void startForResult(Class<?> clazz, int requestCode, IResultCallback callback) {
        Intent intent = new Intent(bindFragment.getActivity(), clazz);
        startForResult(intent, requestCode, callback);
    }

    private BindFragment bindFragment(FragmentActivity activity) {
        BindFragment bindFragment = getBindFragment(activity);
        if (bindFragment == null) {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            bindFragment = BindFragment.newInstance();
            fragmentManager.beginTransaction().add(bindFragment, BINDFRAGMENT).commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        }
        return bindFragment;
    }

    private BindFragment getBindFragment(FragmentActivity activity) {
        return (BindFragment) activity.getSupportFragmentManager().findFragmentByTag(BINDFRAGMENT);
    }

}
