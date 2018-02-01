package com.mihryundel.rbcnews.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.ui.activities.NewsActivity;
import com.mihryundel.rbcnews.ui.activities.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    @BindView(R.id.btn_settings) ImageButton mSettingsImageButton;
    @BindView(R.id.btn_news) ImageButton mNewsImageButton;
    private Unbinder unbinder;

    @OnClick(R.id.btn_news)
    public void openNewsActivity() {
        Intent intentNewsList = new Intent(getActivity(), NewsActivity.class);
        startActivity(intentNewsList);
    }

    @OnClick(R.id.btn_settings)
    public void openSettingsActivity() {
        Intent intentSettings = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intentSettings);
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
