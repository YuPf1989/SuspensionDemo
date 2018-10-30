package com.rain.suspensiondemo.suspension;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.rain.suspensiondemo.activity_result.ActivityResultManager;
import com.rain.suspensiondemo.activity_result.IResultCallback;

/**
 * Author:rain
 * Date:2018/10/26 15:31
 * Description:
 * 检查是否有悬浮窗权限
 */
public class CheckPermissionUtil {
    public static final int OVERLAY_PERMISSION_REQ_CODE = 01;
    private final ActivityResultManager resultManager;
    private FragmentActivity mActivity;
    public static PermissionCallback mCallback;


    public CheckPermissionUtil(FragmentActivity act) {
        mActivity = act;
        resultManager = new ActivityResultManager(act);
    }

    public void checkSuspensionWinPermission() {
        // 安卓6.0以上才有这个权限开关
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(mActivity)) {
                Toast.makeText(mActivity, "没有权限", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + mActivity.getPackageName()));
                resultManager.startForResult(intent, OVERLAY_PERMISSION_REQ_CODE, new IResultCallback() {
                    @Override
                    public void getResultCallback(int requestCode, int resultCode, Intent data) {
                        if (Settings.canDrawOverlays(mActivity)) {
                            Toast.makeText(mActivity, "权限申请成功", Toast.LENGTH_SHORT).show();
                            mCallback.applyResult(true);
                        } else {
                            Toast.makeText(mActivity, "用户未授予权限", Toast.LENGTH_SHORT).show();
                            mCallback.applyResult(false);

                        }
                    }
                });
            } else {
                mCallback.applyResult(true);
//                openSuspension();
            }
        } else {
            // 需要针对各家机型单独做适配
//            openSuspension();
        }
    }

    public interface PermissionCallback {
        void applyResult(boolean success);
    }

    public void setPermissionCallback(PermissionCallback permissionCallback) {
        mCallback = permissionCallback;
    }


}
