package com.rain.suspensiondemo.suspension;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.rain.suspensiondemo.model.bean.ArticleBean;
import com.rain.suspensiondemo.util.SPUtils;

import java.util.List;

/**
 * Author:rain
 * Date:2018/10/26 15:28
 * Description:
 */
public class SuspensionWindowManager {
    private static final String TAG = "SuspensionWindowManager";
    private final CheckPermissionUtil permissionUtil;
    private FragmentActivity mActivity;
    private static final String SERVICE_NAME  = "com.rain.suspensiondemo.suspension.WindowShowService";

    public SuspensionWindowManager(FragmentActivity activity) {
        mActivity = activity;
        permissionUtil = new CheckPermissionUtil(activity);
    }


    public void openWindow(ArticleBean data) {
        permissionUtil.setPermissionCallback(new CheckPermissionUtil.PermissionCallback() {
            @Override
            public void applyResult(boolean success) {
                if (success) {
                    // 用户已经开启了悬浮窗权限
                    Intent intent = new Intent(mActivity, WindowShowService.class);
                    SPUtils.put(mActivity,"imgUrl",data.imgUrl);
                    SPUtils.put(mActivity,"jumpUrl",data.jumpUrl);
                    mActivity.startService(intent);
                }
            }
        });
        permissionUtil.checkSuspensionWinPermission();
    }

    public void closeWindow() {
        if (isWindowOpen()) {
            mActivity.stopService(new Intent(mActivity, WindowShowService.class));
        }
    }

    public boolean isWindowOpen() {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mActivity
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(SERVICE_NAME)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
