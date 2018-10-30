package com.rain.suspensiondemo.model;

import com.rain.suspensiondemo.model.bean.ArticleBean;

import java.util.ArrayList;

/**
 * Author:rain
 * Date:2018/10/24 9:59
 * Description:
 */
public class DataCreatorUtil {
    /**
     * 创建文章数据
     * @return articles
     */
    public static ArrayList<ArticleBean> getArticles() {
        ArrayList<ArticleBean> articles = new ArrayList<>();
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYYibbSNficfWFKbibMVJ9WeOWIn6feWm9FrZKG9DBqzRDic7LSMbu69sIDXbbsNI6nicsB37BO64PpUAXIw/0?wx_fmt=png"
                , "从源码的角度浅谈Activity，Window，View三者的关系", "2018.09.27",
                "https://mp.weixin.qq.com/s/oPucnbYujfSpwDAbwGI5Mw"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY9sFjRWIyFo6KCxKSnicMaklZQ31B2lo3zBZfa51O4OdD3sv28tPXxPlAYCpxlptMoiav4UXC6oQ20g/0?wx_fmt=png"
                , "如何优雅的使用ThreadLocal", "2018.09.25",
                "https://mp.weixin.qq.com/s/of0au0FwKzVe0spQhkeYgQ"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY8hoA09shUgib8x3FdKGPhPnkicvlGpcaA7S0yTNObmsLV3eZhWEfUYZjx6VwLiaSY24VHSBAVxsHsSw/0?wx_fmt=png"
                , "深入浅出Java中clone方法", "2018.09.23",
                "https://mp.weixin.qq.com/s/W8E6Qt6i5qR-1QQTuKCHNg"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY9Dw1YZNxQYnsjT2Z4f5ak2wn7SS86eL8wy2zDqYBlWTpTFFH6jaLCy0AK7nyicqMpwWdlDD6aQ6QQ/0?wx_fmt=png"
                , "Android不正经布局之ConstraintLayout布局解析", "2018.09.18",
                "https://mp.weixin.qq.com/s/cJJjo7TX6BPCUsGJooD69g"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYYicrkD1ua0KeQUCENQdg157l2VlJDE45gnTlhtGScfoPB2jgRzBWtsKUacOric9S7JCibMPkk4Sbt8Xw/0?wx_fmt=png"
                , "Android开发人员不得不学习Vue.js", "2018.09.13",
                "https://mp.weixin.qq.com/s/0yNnnXzhtQlQa_9Lrx_Mwg"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY98GY0XicKQNvNharaP8JkI3NO80CcdcF8HwR1TibOLaGtKxLNYdM5fZsgQoae6nM1yjJXDNkJF31tw/0?wx_fmt=png"
                , "一个Android程序员的北漂之路", "2018.09.10",
                "https://mp.weixin.qq.com/s/SGdQCZZZz4iOn2dJqytYLQ"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY8dy5Zlpv2WyejEscndrh3T6YRJTxpsMTGhl22dGBiczh9pjgZzRDpP4HOqC0RicU48g6qOUxStF8Og/0?wx_fmt=png"
                , "Android程序员不得不了解的JavaScript基础（一）", "2018.09.02",
                "https://mp.weixin.qq.com/s/4PtAJOF8_B8cUvG59lbemQ"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYY97B4xibmfUUanj6Pj9ypahjdf7pFnfKDIjj4T7qJvCq9PcuRIzpU9UlPdIkJrqAYqlDsol2CYmrrA/0?wx_fmt=png"
                , "Android程序员不得不了解的JavaScript基础（二）", "2018.08.30",
                "https://mp.weixin.qq.com/s/nSXk3sRFqo21xvL93s56EA"));
        articles.add(new ArticleBean("https://mmbiz.qpic.cn/mmbiz_png/ldey36QiaYYib1EicP3ywwlTIiavkNNHxwOENTzPb4SDDUv0PibXiccR16XNZFAjaGxvk1xK5HZxtE8TfrwEChnFia5AA/0?wx_fmt=png"
                , "Kotlin入门教程——快使用Kotlin吧", "2018.08.27",
                "https://mp.weixin.qq.com/s/0-ge5AKCly95K6m-wLyjUg"));
        return articles;
    }
}
