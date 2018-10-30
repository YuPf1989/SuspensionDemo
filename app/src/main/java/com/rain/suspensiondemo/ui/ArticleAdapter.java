package com.rain.suspensiondemo.ui;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rain.suspensiondemo.R;
import com.rain.suspensiondemo.model.bean.ArticleBean;
import com.rain.suspensiondemo.util.GlideApp;

import java.util.List;

/**
 * Author:rain
 * Date:2018/10/24 9:58
 * Description:
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {
    public ArticleAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleBean item) {
        helper.setText(R.id.tv_time, item.time);
        helper.setText(R.id.tv_title, item.title);
        GlideApp.with(mContext)
                .load(item.imgUrl)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.iv_article));
    }
}
