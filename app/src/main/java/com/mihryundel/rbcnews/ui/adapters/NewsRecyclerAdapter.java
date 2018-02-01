package com.mihryundel.rbcnews.ui.adapters;

import android.content.Context;
import android.opengl.Visibility;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihryundel.rbcnews.MySharedPreferences;
import com.mihryundel.rbcnews.R;
import com.mihryundel.rbcnews.RbcNewsApplication;
import com.mihryundel.rbcnews.mvp.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private List<News> mNewsArrayList;
    private Context mContext;
    @Inject
    MySharedPreferences mySharedPreferences;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_news_title) TextView mTitleTv;
        @BindView(R.id.tv_news_time) TextView mTimeTv;
        @BindView(R.id.iv_news_image) ImageView mImageIv;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public NewsRecyclerAdapter(Context context, List<News> newsArrayList) {
        RbcNewsApplication.app().appComponent().inject(this);
        mContext = context;
        mNewsArrayList = newsArrayList;
    }

    @Override
    public NewsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitleTv.setText(mNewsArrayList.get(position).getTitle());
        holder.mTimeTv.setText(mNewsArrayList.get(position).getTime());
        holder.mImageIv.setVisibility(View.VISIBLE); //this fix disappearing images
        if (!(mNewsArrayList.get(position).getImageUrl() == null) && mySharedPreferences.isImageLoading()) {
            Picasso.with(mContext).load(mNewsArrayList.get(position).getImageUrl()).into(holder.mImageIv);
        } else {
            holder.mImageIv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mNewsArrayList.size();
    }
}
