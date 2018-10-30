package com.rain.suspensiondemo.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:rain
 * Date:2018/10/24 10:02
 * Description:
 */
public class ArticleBean implements Parcelable {
    public String title;
    public String time;
    public String imgUrl;
    public String jumpUrl;

    public ArticleBean( String imgUrl, String title, String time,String jumpUrl) {
        this.title = title;
        this.time = time;
        this.imgUrl = imgUrl;
        this.jumpUrl = jumpUrl;
    }

    protected ArticleBean(Parcel in) {
        title = in.readString();
        time = in.readString();
        imgUrl = in.readString();
        jumpUrl = in.readString();
    }

    public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel in) {
            return new ArticleBean(in);
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(time);
        parcel.writeString(imgUrl);
        parcel.writeString(jumpUrl);
    }
}
