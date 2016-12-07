package com.silencedut.knowweather.weather.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by SilenceDut on 16/10/22.
 */

public class GuideHolder extends BaseViewHolder<GuideData> {
    @BindView(R.id.guide_title)
    TextView mGuideTitle;
    @BindView(R.id.guide_icon)
    ImageView mGuideIcon;

    public GuideHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void updateItem(GuideData data, int position) {
        mGuideTitle.setText(data.guide);
        if (data.guideIconId != 0) {
            mGuideIcon.setImageResource(data.guideIconId);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_guide;
    }

}
