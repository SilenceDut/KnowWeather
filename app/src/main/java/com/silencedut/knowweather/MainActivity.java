package com.silencedut.knowweather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.knowweather.citys.ui.CityFragment;
import com.silencedut.knowweather.common.BaseActivity;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.customview.ShareDialog;
import com.silencedut.knowweather.user.AboutActivity;
import com.silencedut.knowweather.user.UserFragment;
import com.silencedut.knowweather.utils.TimeUtil;
import com.silencedut.knowweather.utils.UIUtil;
import com.silencedut.knowweather.weather.entity.WeatherEntity;
import com.silencedut.knowweather.weather.presenter.MainView;
import com.silencedut.knowweather.weather.presenter.WeatherPresenter;
import com.silencedut.knowweather.weather.ui.WeatherFragment;
import com.silencedut.knowweather.weather.ui.adapter.AqiData;
import com.silencedut.knowweather.weather.ui.adapter.DailyWeatherData;
import com.silencedut.knowweather.weather.ui.adapter.HourWeatherHolder;
import com.silencedut.knowweather.weather.ui.adapter.HoursForecastData;
import com.silencedut.knowweather.weather.ui.adapter.LifeIndexData;
import com.silencedut.knowweather.weather.ui.adapter.MainPageAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * Created by SilenceDut on 16/10/15.
 */

public class MainActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, MainView {

    private static final int ROTATION_DURATION = 1000;
    private static final int POSTTIME_DURATION = 500;
    private static final float DEFAULT_PERCENTAGE = 0.8f;

    private float percentageOfShowTitle = DEFAULT_PERCENTAGE;
    private float mWeatherInfoContainerLeft;
    private BaseRecyclerAdapter mHoursForecastAdapter;
    private String mTemperature;
    private String mWeatherStatus;
    protected float mTitlePercentage;
    private ObjectAnimator mActionRotate;
    private Drawable mDrawableLocation;
    private ValueAnimator mSucceedAnimator;

    @BindView(R.id.main_layout)
    CoordinatorLayout mMainLayout;
    @BindView(R.id.title_icon)
    ImageView mTitleIcon;
    @BindView(R.id.title_temp)
    TextView mTitleTemp;
    @BindView(R.id.float_action)
    FloatingActionButton mFloatAction;
    @BindView(R.id.refresh_status)
    ImageView mRefreshStatus;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_temp)
    TextView mMainTemp;
    @BindView(R.id.container_layout)
    View mTitleContainer;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.main_hours_forecast_recyclerView)
    RecyclerView mHoursForecastRecyclerView;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.main_bg)
    ImageView mMainBgIv;
    @BindView(R.id.main_info)
    TextView mMainInfoTv;
    @BindView(R.id.main_location)
    TextView mLocationTv;
    @BindView(R.id.main_post_time)
    TextView mPostTimeTv;

    private WeatherPresenter mWeatherPresenter;
    private WeatherFragment mWeatherFragment;

    private ShareDialog mShareDialog;

    private boolean mIsInit = true;

    @Override
    public void initBeforeView() {
        mWeatherPresenter = new WeatherPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        if (TimeUtil.isNight()) {
            mMainBgIv.setImageResource(R.mipmap.bg_night);
        }

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(null);
        getSupportActionBar().setTitle("");
        mAppBarLayout.addOnOffsetChangedListener(this);
        setupViewPager();
        setupHoursForecast();
        mTitleContainer.post(new Runnable() {
            @Override
            public void run() {
                mWeatherInfoContainerLeft = mTitleContainer.getX();
                percentageOfShowTitle = (mTitleContainer.getBottom()) * 1.0f / mAppBarLayout.getTotalScrollRange();
                if (percentageOfShowTitle == 0) {
                    mWeatherInfoContainerLeft = DEFAULT_PERCENTAGE;
                }
            }
        });
        mActionRotate = ObjectAnimator.ofFloat(mRefreshStatus, "rotation", 0, 360);
        mActionRotate.setDuration(ROTATION_DURATION);
        mActionRotate.setRepeatCount(-1);

        mDrawableLocation = UIUtil.getDrawable(this, R.mipmap.location);
        mDrawableLocation.setBounds(0, 0, UIUtil.dipToPx(this, R.dimen.common_location_size), UIUtil.dipToPx(this, R.dimen.common_location_size));

        mSucceedAnimator = ofFloat(mPostTimeTv, "scaleX", 1, 0, 1).setDuration(POSTTIME_DURATION);
        mSucceedAnimator.setStartDelay(ROTATION_DURATION);

        mShareDialog = new ShareDialog(this);

    }

    private void setupViewPager() {
        MainPageAdapter adapter = new MainPageAdapter(this, getSupportFragmentManager());

        Fragment cityFragment = CityFragment.newInstance();
        adapter.addFrag(cityFragment);

        mWeatherFragment = WeatherFragment.newInstance();
        adapter.addFrag(mWeatherFragment);

        Fragment userFragment = UserFragment.newInstance();
        adapter.addFrag(userFragment);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setCustomView(adapter.getTabView(0, mTabLayout));
        mTabLayout.getTabAt(1).setCustomView(adapter.getTabView(1, mTabLayout));
        mTabLayout.getTabAt(2).setCustomView(adapter.getTabView(2, mTabLayout));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setCurrentItem(1);

    }

    void setupHoursForecast() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHoursForecastRecyclerView.setLayoutManager(linearLayoutManager);
        mHoursForecastAdapter = new BaseRecyclerAdapter(this);
        mHoursForecastAdapter.registerHolder(HourWeatherHolder.class, R.layout.item_hour_forecast);
        mHoursForecastRecyclerView.setAdapter(mHoursForecastAdapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        mTitlePercentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleInfoAnimate(mTitlePercentage);

    }

    private void handleInfoAnimate(float percentage) {
        mToolbar.getBackground().mutate().setAlpha((int) (255 * percentage));
        mTitleContainer.setAlpha(1 - percentage);
        mTitleContainer.setScaleX(1 - percentage);
        mTitleContainer.setScaleY(1 - percentage);
        mHoursForecastRecyclerView.setAlpha(1 - percentage);

        if (mWeatherInfoContainerLeft > 0) {
            mTitleContainer.setX(mWeatherInfoContainerLeft * (1 - percentage));
        }

        if (!(percentage < percentageOfShowTitle)) {
            mTitleIcon.setImageResource(Constants.getIconId(mWeatherStatus));
            mTitleTemp.setText(mTemperature);
            if (mFloatAction.isShown()) {
                mFloatAction.hide();
            }
        } else {
            if (!mFloatAction.isShown() && !mActionRotate.isRunning()) {
                mFloatAction.show();
            }
            mTitleIcon.setImageDrawable(null);
            mTitleTemp.setText("");
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && mIsInit) {
            mIsInit = false;
            mWeatherPresenter.getDefaultWeather();
        }
    }

    @OnClick(R.id.float_action)
    public void onClick() {
        mWeatherPresenter.updateDefaultWeather();
    }

    @Override
    public void onRefreshing(boolean refreshing) {
        if (refreshing) {
            mPostTimeTv.setText(R.string.refreshing);
            mRefreshStatus.setVisibility(View.VISIBLE);
            mActionRotate.start();
            mFloatAction.hide();
        } else {
            mPostTimeTv.setText(R.string.refresh_fail);
            stopRefreshing();
        }
    }

    @Override
    public void onBasicInfo(WeatherEntity.BasicEntity basicData, List<HoursForecastData> hoursForecastDatas, boolean isLocationCity) {

        mLocationTv.setCompoundDrawables(isLocationCity ? mDrawableLocation : null, null, null, null);
        mLocationTv.setText(basicData.getCity());

        updateSucceed(String.format(getString(R.string.post), TimeUtil.getTimeTips(basicData.getTime())));

        mTemperature = basicData.getTemp();
        mWeatherStatus = basicData.getWeather();
        mMainTemp.setText(mTemperature);
        mMainInfoTv.setText(mWeatherStatus);

        if (TimeUtil.isNight()) {
            if (Constants.sunny(mWeatherStatus)) {
                mMainBgIv.setImageResource(R.mipmap.bg_night);
            } else {
                mMainBgIv.setImageResource(R.mipmap.bg_night_dark);
            }
        } else {
            mMainBgIv.setImageResource(R.mipmap.bg_day);
        }

        mHoursForecastAdapter.setData(hoursForecastDatas);
    }


    @Override
    public void onMoreInfo(AqiData aqiData, List<DailyWeatherData> dailyForecastDatas, LifeIndexData lifeIndexData) {
        mWeatherFragment.onMoreInfo(aqiData, dailyForecastDatas, lifeIndexData);
    }

    private void updateSucceed(final String postTime) {

        mSucceedAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                stopRefreshing();
                mPostTimeTv.setText(R.string.refresh_succeed);
            }
        });

        mSucceedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                if (fraction >= 0.5f) {
                    mPostTimeTv.setText(postTime);
                }
            }
        });
        mSucceedAnimator.start();
    }

    private void stopRefreshing() {
        mActionRotate.end();
        mRefreshStatus.setVisibility(View.GONE);
        mFloatAction.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share_weather:
                mShareDialog.show(true);
                break;
            case R.id.share_app:
                mShareDialog.show(false);
                break;
            case R.id.about:
                AboutActivity.navigationActivity(this);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSucceedAnimator.removeAllListeners();
        mActionRotate.removeAllListeners();
        mSucceedAnimator.removeAllUpdateListeners();
        mActionRotate.removeAllUpdateListeners();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
