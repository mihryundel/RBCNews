package com.mihryundel.rbcnews.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.mvp.models.News;
import com.mihryundel.rbcnews.mvp.presenters.NewsPresenter;
import com.mihryundel.rbcnews.mvp.views.NewsView;
import com.mihryundel.rbcnews.ui.adapters.NewsRecyclerAdapter;
import com.mihryundel.rbcnews.ui.commons.ItemClickSupport;

import java.util.List;

public class NewsActivity extends AppCompatActivity {


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //UpdateEventReceiver.setupAlarm(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void showUpdateErrorToast() {
//        Toast.makeText(this, R.string.update_error_toast,
//                Toast.LENGTH_LONG).show();
//    }

}
