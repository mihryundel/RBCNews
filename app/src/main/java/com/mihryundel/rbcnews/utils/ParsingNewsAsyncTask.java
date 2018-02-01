package com.mihryundel.rbcnews.utils;

import android.os.AsyncTask;

import com.mihryundel.rbcnews.MySharedPreferences;
import com.mihryundel.rbcnews.RbcNewsApplication;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import javax.inject.Inject;

public class ParsingNewsAsyncTask extends AsyncTask<Void, Void, Document> {

    @Inject
    MySharedPreferences mySharedPreferences;

    public ParsingNewsAsyncTask() {
        RbcNewsApplication.app().appComponent().inject(this);
    }


    @Override
    protected Document doInBackground(Void... params) {
        String parsedUrl = mySharedPreferences.getCategoryUrl();
        Document document = null;
        try {
            document = Jsoup.connect(parsedUrl).header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
    }
}
