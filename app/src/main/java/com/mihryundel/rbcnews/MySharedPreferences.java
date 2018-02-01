package com.mihryundel.rbcnews;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;
    @Inject
    public Context mContext;
    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        RbcNewsApplication.app().appComponent().inject(this);
        this.mSharedPreferences = mSharedPreferences;
    }

    public void isImageLoading(boolean imageLoading) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext).edit().putBoolean(mContext.getResources().getString(R.string.imageLoading),
                imageLoading).apply();
    }

    public boolean isImageLoading() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getBoolean(mContext.getResources().getString(R.string.imageLoading), false);
    }

    public void setUpdateInterval(int updateIntervalInQuaterOfAnHour) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext).edit().putInt(mContext.getResources().getString(R.string.intervalList), updateIntervalInQuaterOfAnHour).apply();
    }

    public int getUpdateInterval() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getInt(mContext.getResources().getString(R.string.intervalList), 1);
    }

    public void setCategoryUrl(String categoryUrl) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext).edit().putString(mContext.getResources().getString(R.string.categoryUrlList),
                categoryUrl).apply();
    }

    public String getCategoryUrl() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getString(mContext.getResources().getString(R.string.categoryUrlList), mContext.getResources().getStringArray(R.array.category_url_values)[0]);
    }

    public void setSubcategory(String subcategory) {
        PreferenceManager
                .getDefaultSharedPreferences(mContext).edit().putString(mContext.getResources().getString(R.string.subcategoryList),
                subcategory).apply();
    }

    public String getSubcategory() {
        return PreferenceManager
                .getDefaultSharedPreferences(mContext)
                .getString(mContext.getResources().getString(R.string.subcategoryList), mContext.getResources().getStringArray(R.array.auto_subcategory_values)[0]);
    }
}
