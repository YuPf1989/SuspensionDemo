package com.rain.suspensiondemo.suspension;

import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rain.suspensiondemo.R;
import com.rain.suspensiondemo.model.bean.ArticleBean;
import com.rain.suspensiondemo.ui.ArticleDetailActivity;
import com.rain.suspensiondemo.util.GlideApp;
import com.rain.suspensiondemo.util.SPUtils;

/**
 * Author:rain
 * Date:2018/10/30 10:34
 * Description:
 * 悬浮窗服务
 */
public class WindowShowService extends Service {
    private static final String TAG  = "WindowShowService";
    private static String imgUrl;
    private static String jumpUrl;
    private View suspensionWindow;
    private WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private int statusBarHeight = -1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imgUrl = (String) SPUtils.get(this, "imgUrl", "");
        jumpUrl = (String) SPUtils.get(this, "jumpUrl", "");
        openSuspension();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissWindow();
    }

    private void openSuspension() {
        suspensionWindow = LayoutInflater.from(this).inflate(R.layout.layout_suspension_window, null);
        ImageView img = suspensionWindow.findViewById(R.id.click);
        GlideApp.with(this)
                .load(imgUrl)
                .circleCrop()
                .into(img);
        img.setOnClickListener(v -> {
            ArticleDetailActivity.start(WindowShowService.this, jumpUrl);
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
            private boolean isMove;
            private double downTime;
            private double upTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();
                        isMove = false;
                        downTime = System.currentTimeMillis();
                        // 返回true代表消费当前事件类型，不再向下传递
                        return false;

                    case MotionEvent.ACTION_MOVE:
                        params.x = (int) (event.getRawX() - startX);
                        params.y = (int) (event.getRawY() - startY - statusBarHeight);
                        updateViewLayout();
                        return true;

                    case MotionEvent.ACTION_UP:
                        upTime = System.currentTimeMillis();
                        isMove = upTime - downTime > 100;
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
                        return isMove;
                    default:
                }
                return false;
            }
        });
    }

    private void updateViewLayout() {
        if (null != params && null != suspensionWindow) {
            windowManager.updateViewLayout(suspensionWindow, params);
        }
    }

    public void dismissWindow() {
        if (windowManager != null && suspensionWindow != null) {
            windowManager.removeViewImmediate(suspensionWindow);
            windowManager = null;
            suspensionWindow = null;
        }
    }
}
