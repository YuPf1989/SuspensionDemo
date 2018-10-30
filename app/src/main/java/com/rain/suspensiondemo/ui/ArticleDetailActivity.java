package com.rain.suspensiondemo.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rain.suspensiondemo.R;
import com.rain.suspensiondemo.base.BaseActivity;
import com.rain.suspensiondemo.model.bean.ArticleBean;
import com.rain.suspensiondemo.suspension.SuspensionWindowManager;

/**
 * Author:rain
 * Date:2018/10/24 11:23
 * Description:
 */
public class ArticleDetailActivity extends BaseActivity {

    private Toolbar mToolbar;
    private WebView mWebview;
    private String jumpUrl;
    private ProgressBar mPb;
    private SuspensionWindowManager suspensionWindowManager;
    private ArticleBean data;

    @Override
    protected void initView() {
        getIntentData();
        mToolbar = findViewById(R.id.toolbar);
        initToolbar(mToolbar, true, "标题");
        mPb = findViewById(R.id.pb);
        mWebview = findViewById(R.id.webview);
        initWebviewSetting();
        suspensionWindowManager = new SuspensionWindowManager(this);
    }

    private void initWebviewSetting() {
        mWebview.setVerticalScrollBarEnabled(false);
        mWebview.setHorizontalScrollBarEnabled(false);
        WebSettings webSetting = mWebview.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getDir("appCache", Context.MODE_PRIVATE).getPath());
        webSetting.setDatabasePath(getDir("databases", Context.MODE_PRIVATE).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", Context.MODE_PRIVATE).getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setTextSize(WebSettings.TextSize.NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPb.setVisibility(View.GONE);
            }
        });

        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPb.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                mToolbar.setTitle(title);
            }
        });

        mWebview.loadUrl(jumpUrl);
    }

    private void getIntentData() {
        if (getIntent().hasExtra("data")) {
            data = ((ArticleBean) getIntent().getParcelableExtra("data"));
            jumpUrl = data.jumpUrl;
        }
        if (getIntent().hasExtra("jumpUrl")) {
            jumpUrl = getIntent().getStringExtra("jumpUrl");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_article_detail;
    }

    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_article_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open:
                if (suspensionWindowManager.isWindowOpen()) {
                    Toast.makeText(this, "当前服务已经开启！", Toast.LENGTH_SHORT).show();
                    return true;
                }
                if (data == null) {
                    Toast.makeText(this, "data为null!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                suspensionWindowManager.openWindow(data);
                break;

            case R.id.action_close:
                suspensionWindowManager.closeWindow();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public static void start(Context context, ArticleBean data) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    public static void start(Context context, String jumpUrl) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("jumpUrl", jumpUrl);
        context.startActivity(intent);
    }
}
