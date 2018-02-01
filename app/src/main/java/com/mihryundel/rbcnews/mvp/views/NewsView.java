package com.mihryundel.rbcnews.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.mihryundel.rbcnews.mvp.models.News;

import java.util.List;


@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface NewsView extends MvpView {
    void onNewsFromDbLoaded(List<News> news);
    void updateView();
    void onNewsFromInternetUpdated(List<News> mNewsList);
    void stopRefreshing();
}
