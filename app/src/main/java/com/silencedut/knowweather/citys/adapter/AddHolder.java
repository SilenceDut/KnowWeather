package com.silencedut.knowweather.citys.adapter;

import android.view.View;
import android.widget.ImageView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.citys.ui.SearchActivity;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/24 .
 */

public class AddHolder extends BaseViewHolder<AddData> {

    @BindView(R.id.image)
    ImageView mImage;


    public AddHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(AddData data, int position) {

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_add_city;
    }

    @OnClick(R.id.image)
    public void onClick() {
        SearchActivity.navigationActivity(getContext());
    }
}
