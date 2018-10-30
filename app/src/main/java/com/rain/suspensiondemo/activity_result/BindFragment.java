package com.rain.suspensiondemo.activity_result;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:rain
 * Date:2018/10/29 10:53
 * Description:
 * 用于处理activity onActivityResult结果的fragment
 * 注意默认的result_code 为 RESULT_OK
 */
public class BindFragment extends Fragment {
    private static final String TAG = "BindFragment";
    private Map<Integer, IResultCallback> mCallbacks = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 当其所依附的activity重建时，fragment不重建，填充到新的activity中
        setRetainInstance(true);
    }

    public static BindFragment newInstance() {
        Bundle args = new Bundle();
        BindFragment fragment = new BindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IResultCallback callback = mCallbacks.remove(requestCode);
        if (callback != null) {
            callback.getResultCallback(requestCode, resultCode, data);
        }
    }

    public void startForResult(Intent intent, int requestCode, IResultCallback callback) {
        mCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

}
