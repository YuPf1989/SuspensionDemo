package com.rain.suspensiondemo.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rain.suspensiondemo.R;
import com.rain.suspensiondemo.base.BaseActivity;
import com.rain.suspensiondemo.model.DataCreatorUtil;
import com.rain.suspensiondemo.model.bean.ArticleBean;

/**
 * Author:rain
 * Date:2018/10/24 9:35
 * Description:
 */
public class ArticlesActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void initView() {
        initToolbar(findViewById(R.id.toolbar),true,"文章列表");
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.layout_item_article, DataCreatorUtil.getArticles());
        mRecyclerView.setAdapter(articleAdapter);
        articleAdapter.setOnItemClickListener((adapter, view, position) -> {
            ArticleBean data = (ArticleBean) adapter.getData().get(position);
            Intent intent = new Intent(ArticlesActivity.this, ArticleDetailActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_articles;
    }
}
