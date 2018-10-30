package com.rain.suspensiondemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.rain.suspensiondemo.base.BaseActivity;
import com.rain.suspensiondemo.suspension.CheckPermissionUtil;
import com.rain.suspensiondemo.suspension.SuspensionWindowManager;
import com.rain.suspensiondemo.ui.ArticleDetailActivity;
import com.rain.suspensiondemo.ui.ArticlesActivity;

/**
 * 安卓悬浮窗项目
 * 1.不通过权限开启悬浮窗
 * 2.通过正规权限开启
 * 3.6.0,7.0,8.0系统适配问题
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private static final int OVERLAY_PERMISSION_REQ_CODE = 01;

    private View suspensionWindow;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private int statusBarHeight = -1;
    private SuspensionWindowManager manager;
    private Toolbar mToolbar;

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        initToolbar(mToolbar,false,null);
        manager = new SuspensionWindowManager(MainActivity.this);

        findViewById(R.id.btn_open_articles).setOnClickListener(view -> {
            startActivity(new Intent(this, ArticlesActivity.class));
        });

        findViewById(R.id.btn_nopermission_open).setOnClickListener(view -> {
            checkSuspensionWinPermission();
        });

        // 通过工具类开启权限
        findViewById(R.id.btn_permission_open).setOnClickListener(view -> {
//            manager.openWindow(new CheckPermissionUtil.PermissionCallback() {
//                @Override
//                public void applyResult(boolean success) {
//                    Toast.makeText(MainActivity.this, "grand:" + success, Toast.LENGTH_SHORT).show();
//                }
//            });
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void checkSuspensionWinPermission() {
        // 安卓6.0以上才有这个权限开关
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            } else {
                openSuspension();
            }
        } else {
            // 需要针对各家机型单独做适配
            openSuspension();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // todo 必须用此方法判断是否开启结果，针对6.0及其以上，6.0以下默认开启，但需要对某些厂家做适配
        if (Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
            openSuspension();
        } else {
            Toast.makeText(this, "用户未授予权限", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 关于弹窗类型：
     * Phone/Toast:不需要申请权限，7.0及其以下对于部分机型起作用 7.1及以上会导致崩溃
     * Dialog：不需要申请权限，可以适配到8.0，但需要设置token，只能在当前activity内使用
     * Alert:需要在清单文件中申请权限，并且需要动态申请权限，<8.0
     * Overlay:需要申请权限 可以适配>=8.0的系统
     */
    private void openSuspension() {
        suspensionWindow = LayoutInflater.from(this).inflate(R.layout.layout_suspension_window, null);
        suspensionWindow.findViewById(R.id.click).setOnClickListener(v -> {
//            startActivity(new Intent(getApplication(), MainActivity.class));
            Toast.makeText(getApplication(), "悬浮窗", Toast.LENGTH_SHORT).show();
        });
        params = new WindowManager.LayoutParams();
        // 如果>=8.0,type值是不一样的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        // 参数分别是：type:弹窗类型，flag:弹窗不获取焦点，不影响其他部件的点击，format:弹窗背景 透明
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.width = 200;
        // 如果不设置params.height,那么默认值为-1，match_parent
        params.height = 200;
        // 如果获取的是application的windowManager 则可以在应用外显示，随application的生命周期
        // 如果是activity的manager，窗口生命周期随当前的activity，且只能在当前的activity窗口操作
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
//        windowManager = getWindowManager();
        windowManager.addView(suspensionWindow, params);

        // 获取系统状态栏的高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = this.getResources().getDimensionPixelSize(resourceId);
        }
        suspensionWindow.setOnTouchListener(new View.OnTouchListener() {

            private float startX;
            private float startY;
            private float finalMoveX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        params.x = (int) (event.getRawX() - startX);
                        params.y = (int) (event.getRawY() - startY - statusBarHeight);
                        updateViewLayout();
                        break;

                    case MotionEvent.ACTION_UP:
                        // 如果移动的距离大于屏幕的一半
                        if ((params.x + suspensionWindow.getMeasuredWidth() / 2) >= windowManager.getDefaultDisplay().getWidth() / 2) {
                            finalMoveX = windowManager.getDefaultDisplay().getWidth() - suspensionWindow.getMeasuredWidth();
                        } else {
                            finalMoveX = 0;
                        }

                        ValueAnimator anim = ValueAnimator.ofInt(params.x, (int) finalMoveX).setDuration((long) Math.abs(finalMoveX - params.x));
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                params.x = (int) animation.getAnimatedValue();
                                updateViewLayout();
                            }
                        });
                        anim.start();
                        break;
                    default:
                }
                return false;
            }
        });


    }

    /**
     * 更新view的位置
     */
    private void updateViewLayout() {
        if (null != params && null != suspensionWindow) {
            windowManager.updateViewLayout(suspensionWindow, params);
        }
    }
}
