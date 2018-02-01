package com.mihryundel.rbcnews.ui.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpActivity;
import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.mvp.models.News;
import com.mihryundel.rbcnews.mvp.presenters.NewsPresenter;
import com.mihryundel.rbcnews.mvp.views.NewsView;
import com.mihryundel.rbcnews.ui.adapters.NewsRecyclerAdapter;
import com.mihryundel.rbcnews.ui.commons.ItemClickSupport;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends MvpFragment implements NewsView {
    @InjectPresenter
    public NewsPresenter mPresenter;
    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mNewsRecyclerView = (RecyclerView) getView().findViewById(R.id.rv_news_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                mPresenter.updateNewsFromInternet();
            }
        });
    }

    @Override
    public void onNewsFromDbLoaded(List<News> news) {
        mSwipeRefreshLayout.setRefreshing(false);
        mNewsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getView().getContext());
        mNewsRecyclerView.setLayoutManager(mLayoutManager);

        mNewsRecyclerView.setAdapter(new NewsRecyclerAdapter(getView().getContext(), news));
        updateView();
        ItemClickSupport.addTo(mNewsRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        mPresenter.openNews(getActivity(), position);
                    }
                });
    }

    @Override
    public void updateView() {
        mNewsRecyclerView.getAdapter().notifyDataSetChanged();
        if (mNewsRecyclerView.getAdapter().getItemCount() == 0) {
            //mNewsRecyclerView.setVisibility(View.GONE);
            getActivity().findViewById(R.id.tv_unable_to_get_news).setVisibility(View.VISIBLE);
        } else {
            mNewsRecyclerView.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.tv_unable_to_get_news).setVisibility(View.GONE);
        }
    }

    @Override
    public void onNewsFromInternetUpdated(List<News> mNewsList) {
        mPresenter.loadAllNewsFromDb();
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
