package com.silencedut.knowweather.weather.ui.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silencedut.expandablelayout.ExpandableLayout;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.utils.UIUtil;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.common.customview.LevelView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/19.
 */

public class AqiViewHolder extends BaseViewHolder<AqiData> {

    private static final int EXPAND_DURATION = 300;

    private static final int[] COlORS_ID = {R.color.green500, R.color.yellow500, R.color.orange500, R.color.red400, R.color.purple500, R.color.red900};
    private static final int[] AQI_LEVELS = {50, 100, 150, 200, 300, 500};
    private static final int[] PM2_5_LEVELS = {35, 75, 115, 150, 250, 500};
    private static final int[] PM10_LEVELS = {50, 150, 250, 350, 420, 600};

    @BindView(R.id.aqi_view)
    LevelView aqiView;
    @BindView(R.id.aqi_value)
    TextView aqiValue;
    @BindView(R.id.aqi_quality)
    TextView aqiQuality;
    @BindView(R.id.date_case)
    LinearLayout dateCase;
    @BindView(R.id.expand_icon)
    ImageView expandIcon;
    @BindView(R.id.base_info)
    LinearLayout baseInfo;
    @BindView(R.id.pm2_5_view)
    LevelView pm2_5View;
    @BindView(R.id.pm2_5_value)
    TextView pm2_5Value;
    @BindView(R.id.pm10_view)
    LevelView pm10View;
    @BindView(R.id.pm10_value)
    TextView pm10Value;
    @BindView(R.id.aqi_advice)
    TextView aqiAdvice;
    @BindView(R.id.expandable_layout)
    ExpandableLayout expandableLayout;
    @BindView(R.id.rank)
    TextView rank;

    private boolean isExpanded;
    private Animator iconDownAnimator;
    private Animator iconUpAnimator;

    public AqiViewHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }

    @Override
    public void initViews() {

        iconDownAnimator = generateAnimator(expandIcon, 0, 180);
        iconUpAnimator = generateAnimator(expandIcon, 180, 0);

        expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean expanded) {
                isExpanded = expanded;
            }
        });

        aqiView.setColorLever(COlORS_ID, AQI_LEVELS);
        pm2_5View.setColorLever(COlORS_ID, PM2_5_LEVELS);
        pm10View.setColorLever(COlORS_ID, PM10_LEVELS);

    }

    @OnClick(R.id.expandable_layout)
    void animateIcon() {
        if (isExpanded) {
            iconUpAnimator.start();
        } else {
            iconDownAnimator.start();
        }
    }

    private Animator generateAnimator(View target, float start, float end) {
        return ObjectAnimator.ofFloat(target, "rotation", start, end).setDuration(EXPAND_DURATION);
    }

    @Override
    public void updateItem(AqiData data, int position) {
        WeatherEntity.AqiEntity aqiEntity = data.aqiData;
        if (Check.isNull(aqiEntity)) {
            return;
        }

        upDateLevel(aqiView, aqiValue, Integer.parseInt(aqiEntity.getAqi()));
        upDateLevel(pm2_5View, pm2_5Value, Integer.parseInt(aqiEntity.getPm25()));
        upDateLevel(pm10View, pm10Value, Integer.parseInt(aqiEntity.getPm10()));
        aqiQuality.setText(aqiEntity.getQuality());
        aqiAdvice.setText(aqiEntity.getAdvice());

        SpannableString rankSpannable = new SpannableString(aqiEntity.getCityRank());
        rankSpannable.setSpan(new ForegroundColorSpan(UIUtil.getColor(getContext(), R.color.colorAccent)), 8, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        rankSpannable.setSpan(new RelativeSizeSpan(1.3f), 8, 11, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        rank.setText(rankSpannable);


    }

    private void upDateLevel(LevelView levelView, TextView valueText, int value) {
        levelView.setCurrentValue(value);
        valueText.setText(String.valueOf(value));
        valueText.setTextColor(levelView.getSectionColor());
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_aqi;
    }

}
