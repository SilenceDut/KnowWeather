package com.silencedut.knowweather.common.adapter;

import android.view.View;

/**
 * Created by SilenceDut on 16/10/19.
 */

public class NoDataViewHolder extends BaseViewHolder {
    public NoDataViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(BaseAdapterData Data, int position) {

    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public void initViews() {

    }
}
