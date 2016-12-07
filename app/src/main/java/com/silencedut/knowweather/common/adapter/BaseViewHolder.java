package com.silencedut.knowweather.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.silencedut.knowweather.common.UIInit;

import butterknife.ButterKnife;

/**
 * Created by SilenceDut on 16/10/19.
 */

/**
 * implement UIInit interface is convenient to generate views on ViewHolder
 */
public abstract class BaseViewHolder<T extends BaseAdapterData> extends RecyclerView.ViewHolder implements UIInit {

    protected BaseRecyclerAdapter mBaseAdapter;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private void setUIContext(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    protected Context getContext() {
        return mContext;
    }


    public BaseViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView);
        mBaseAdapter = baseRecyclerAdapter;
        ButterKnife.bind(this, itemView);
        setUIContext(itemView.getContext());
        initBeforeView();
        initViews();
    }

    @Override
    public void initBeforeView() {

    }

    @Override
    public void initViews() {

    }

    public abstract void updateItem(T data, int position);
}
