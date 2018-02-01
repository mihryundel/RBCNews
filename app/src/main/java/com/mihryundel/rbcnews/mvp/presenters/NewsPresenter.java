package com.mihryundel.rbcnews.mvp.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.mihryundel.rbcnews.MySharedPreferences;
import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.RbcNewsApplication;
import com.mihryundel.rbcnews.ui.activities.WebActivity;
import com.mihryundel.rbcnews.mvp.models.AutoNews;
import com.mihryundel.rbcnews.mvp.models.News;
import com.mihryundel.rbcnews.mvp.models.NewsDao;
import com.mihryundel.rbcnews.mvp.models.RealtyNews;
import com.mihryundel.rbcnews.mvp.models.SportNews;
import com.mihryundel.rbcnews.mvp.views.NewsView;
import com.mihryundel.rbcnews.utils.ParsingNewsAsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.inject.Inject;

@InjectViewState
public class NewsPresenter extends MvpPresenter<NewsView> {

    @Inject
    public NewsDao mNewsDao;
    @Inject
    MySharedPreferences mySharedPreferences;
    @Inject
    public Context mContext;
    public List<News> mNewsList;

    public NewsPresenter() {
        RbcNewsApplication.app().appComponent().inject(this);
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadAllNewsFromDb();
    }

    public void loadAllNewsFromDb() {
        String categoryUrl = mySharedPreferences.getCategoryUrl();
        String subcategory = mySharedPreferences.getSubcategory();
        mNewsList = mNewsDao.loadAll();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mNewsList.stream()
                    .filter(news -> news.getUrl().contains(categoryUrl))
                    .collect(Collectors.toList());
        } else {
            List<News> result = new ArrayList<News>();
            for (News news : mNewsList) {
                if (news.getUrl().contains(categoryUrl)) {
                    result.add(news);
                }
            }
            mNewsList = result;
        }
        if ((subcategory != null) && (!subcategory.equals("Все"))) {
            List<News> result = new ArrayList<News>();
            for (News news : mNewsList) {
                if ((news.getSubcategory() != null) && (news.getSubcategory().equals(subcategory))) {
                    result.add(news);
                }
            }
            mNewsList = result;
        }
        getViewState().onNewsFromDbLoaded(mNewsList);
    }

    public void openNews(Activity activity, int position) {
        if (!isInternetConnected()) {
            showOpenErrorToast();
        } else {
            Intent intent = new Intent(activity, WebActivity.class);
            intent.putExtra("news_url", mNewsList.get(position).getUrl());
            intent.putExtra("news_title", mNewsList.get(position).getTitle());
            activity.startActivity(intent);
            activity.finish();
        }
    }

    public void updateNewsFromInternet() {
        if (!isInternetConnected()) {
            showUpdateErrorToast();
        } else {
            Document htmlDocument = null;
            ParsingNewsAsyncTask parsingDataAsyncTask = new ParsingNewsAsyncTask();
            parsingDataAsyncTask.execute();
            try {
                htmlDocument = parsingDataAsyncTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            List<News> mNewsList = getNewsFromDocumentByCategory(htmlDocument);
            mNewsDao.saveAll(mNewsList.toArray(new News[mNewsList.size()]));
            loadAllNewsFromDb();
        }
    }

    public List<News> getNewsFromDocumentByCategory(Document htmlDocument) {
        List<News> mNewsList = new ArrayList<>();
        Elements newsElements = null;
        switch (mySharedPreferences.getCategoryUrl()) {
            case "https://www.autonews.ru/":
                newsElements = htmlDocument.getElementsByClass("item-medium  js-item-medium js-exclude-block");
                for (org.jsoup.nodes.Element newsElement : newsElements) {
                    News newsItem = new AutoNews(newsElement);
                    mNewsList.add(newsItem);
                }
                break;
            case "https://realty.rbc.ru/":
                newsElements = htmlDocument.getElementsByClass("item-realty_medium js-item-realty ");
                newsElements.addAll(htmlDocument.getElementsByClass("item-realty_big js-item-realty "));
                for (org.jsoup.nodes.Element newsElement : newsElements) {
                    News newsItem = new RealtyNews(newsElement);
                    mNewsList.add(newsItem);
                }
                break;
            case "https://sport.rbc.ru/":
                newsElements = htmlDocument.getElementsByClass("item-sport_medium js-item-sport ");
                newsElements.addAll(htmlDocument.getElementsByClass("item-sport_big js-item-sport "));
                for (org.jsoup.nodes.Element newsElement : newsElements) {
                    News newsItem = new SportNews(newsElement);
                    mNewsList.add(newsItem);
                }
                break;
            default:
                newsElements = htmlDocument.getElementsByClass("item js-center-item");
                for (org.jsoup.nodes.Element newsElement : newsElements) {
                    News newsItem = new News(newsElement);
                    mNewsList.add(newsItem);
                }
        }
        return mNewsList;
    }

    private boolean isInternetConnected() {
        ConnectivityManager internetManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = internetManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting());

    }

    public void showUpdateErrorToast() {
        getViewState().stopRefreshing();
        Toast.makeText(mContext, R.string.update_error_toast,
                Toast.LENGTH_LONG).show();
    }

    public void showOpenErrorToast() {
        Toast.makeText(mContext, R.string.open_error_toast,
                Toast.LENGTH_LONG).show();
    }
}