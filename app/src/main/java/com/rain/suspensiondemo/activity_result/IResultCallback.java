package com.rain.suspensiondemo.activity_result;

import android.content.Intent;

/**
 * Author:rain
 * Date:2018/10/29 11:10
 * Description:
 * activityResult返回的结果
 */
public interface IResultCallback {
    void getResultCallback(int requestCode, int resultCode, Intent data);
}
