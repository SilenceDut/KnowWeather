package com.silencedut.knowweather.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.Observer;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.utils.TimeUtil;
import com.silencedut.baselib.commonhelper.utils.UIUtil;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.ui.adapter.HourWeatherHolder;
import com.silencedut.knowweather.ui.adapter.MainPageAdapter;
import com.silencedut.taskscheduler.TaskScheduler;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.IActivityRouter;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.api.coreprovider.ICoreProvider;
import com.silencedut.weather_core.api.settingprovider.ISettingProvider;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;
import com.silencedut.weather_core.corebase.BaseActivity;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.corebase.ResourceProvider;
import com.silencedut.weather_core.corebase.StatusDataResource;
import com.silencedut.weather_core.permission.IPermissionApi;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import butterknife.BindView;
import butterknife.OnClick;

import static android.animation.ObjectAnimator.ofFloat;

/**
 * Created by SilenceDut on 16/10/15.
 */

public class MainActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

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
    View mMainBgIv;
    @BindView(R.id.main_info)
    TextView mMainInfoTv;
    @BindView(R.id.main_location)
    TextView mLocationTv;
    @BindView(R.id.main_post_time)
    TextView mPostTimeTv;

    private WeatherModel mWeatherModel;
    private long mStartRefresh;
    private String mUpdateTime ="";

    private static final int MIN_REFRESH_MILLS = 2000;


    @Override
    public void initBeforeView() {
        super.initBeforeView();
        CoreManager.getImpl(IPermissionApi.class).initUrgentPermission(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_activity_main;
    }

    @Override
    public void initViews() {

        if (TimeUtil.isNight()) {
            mMainBgIv.setBackgroundResource(R.mipmap.weather_bg_night);
        }else {
            mMainBgIv.setBackgroundResource(R.mipmap.weather_bg_day);
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

        mDrawableLocation = UIUtil.getDrawable(this, R.mipmap.core_location);
        mDrawableLocation.setBounds(0, 0, UIUtil.dipToPx(this, R.dimen.common_location_size), UIUtil.dipToPx(this, R.dimen.common_location_size));

        mSucceedAnimator = ofFloat(mPostTimeTv, "scaleX", 1, 0, 1).setDuration(POSTTIME_DURATION);
        mSucceedAnimator.setStartDelay(ROTATION_DURATION);


    }

    @Override
    protected void initDataObserver() {

        mWeatherModel = ModelProvider.getModel(this,WeatherModel.class);

        mWeatherModel.getGetWeatherStatus().observe(this, new Observer<StatusDataResource.Status>() {
            @Override
            public void onChanged(@Nullable final StatusDataResource.Status status) {
                if(StatusDataResource.Status.LOADING.equals(status)) {
                    startRefresh();
                    updateBaseWeatherInfo(mWeatherModel.getWeatherBaseData());
                    mHoursForecastAdapter.setData(mWeatherModel.getHoursDatas());
                }else {
                    if(SystemClock.currentThreadTimeMillis() - mStartRefresh > MIN_REFRESH_MILLS) {
                        onWeatherUpdate(StatusDataResource.Status.SUCCESS.equals(status));
                    }else {
                        TaskScheduler.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                onWeatherUpdate(StatusDataResource.Status.SUCCESS.equals(status));
                            }
                        },MIN_REFRESH_MILLS+SystemClock.currentThreadTimeMillis() - mStartRefresh);
                    }
                }
            }

        });

        mWeatherModel.updateWeather();

    }

    private void setupViewPager() {
        MainPageAdapter adapter = new MainPageAdapter(this, getSupportFragmentManager());

        Pair<BaseFragment, Integer> cityFragmentPair = CoreManager.getImpl(ICityProvider.class).provideCityFragment();
        adapter.addFrag(cityFragmentPair);

        BaseFragment weatherFragment = WeatherFragment.newInstance();
        adapter.addFrag(new Pair<>(weatherFragment,R.drawable.weather_tab_drawable));

        Pair<BaseFragment,Integer> settingFragment = CoreManager.getImpl(ISettingProvider.class).provideSettingFragment();
        adapter.addFrag(settingFragment);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        for(int index =0 ;index < adapter.getCount();index++) {
            mTabLayout.getTabAt(index).setCustomView(adapter.getTabView(index, mTabLayout));
        }

        mViewPager.setOffscreenPageLimit(adapter.getCount());
        mViewPager.setCurrentItem(adapter.getCount()/2);

    }

    void setupHoursForecast() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHoursForecastRecyclerView.setLayoutManager(linearLayoutManager);
        mHoursForecastAdapter = new BaseRecyclerAdapter(this);
        mHoursForecastAdapter.registerHolder(HourWeatherHolder.class, R.layout.weather_item_hour_forecast);
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
            mTitleIcon.setImageResource(ResourceProvider.getIconId(mWeatherStatus));
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


    @OnClick(R.id.float_action)
    public void onClick() {
        mWeatherModel.updateWeather();
    }

    private void onWeatherUpdate(boolean succeed) {

        if (succeed) {
            updateBaseWeatherInfo(mWeatherModel.getWeatherBaseData());
            mHoursForecastAdapter.setData(mWeatherModel.getHoursDatas());
            updateSucceed(mUpdateTime);
        } else {
            mPostTimeTv.setText(R.string.weather_refresh_fail);
        }
        stopRefreshing();

    }


    private void updateBaseWeatherInfo(WeatherData.BasicEntity basicData) {
        if(basicData == null) {
            return;
        }

        mLocationTv.setCompoundDrawables(mWeatherModel.locationIsCurrent()? mDrawableLocation : null, null, null, null);
        mLocationTv.setText(basicData.getCity());
        mUpdateTime = String.format(getString(R.string.weather_post), TimeUtil.getTimeTips(basicData.getTime()));

        mTemperature = basicData.getTemp();
        mWeatherStatus = basicData.getWeather();

        mMainTemp.setText(mTemperature);
        mMainInfoTv.setText(mWeatherStatus);

        if (TimeUtil.isNight()) {
            if (ResourceProvider.sunny(mWeatherStatus)) {
                mMainBgIv.setBackgroundResource(R.mipmap.weather_bg_night);
            } else {
                mMainBgIv.setBackgroundResource(R.mipmap.weather_bg_night_dark);
            }
        } else {
            mMainBgIv.setBackgroundResource(R.mipmap.weather_bg_day);
        }

    }


    private void updateSucceed(final String postTime) {

        mPostTimeTv.setText(R.string.weather_refresh_succeed);

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

    private void startRefresh() {
        mPostTimeTv.setText(R.string.weather_refreshing);
        mRefreshStatus.setVisibility(View.VISIBLE);
        mActionRotate.start();
        mFloatAction.hide();
        mStartRefresh = SystemClock.currentThreadTimeMillis();
    }

    private void stopRefreshing() {
        mActionRotate.end();
        mRefreshStatus.setVisibility(View.GONE);
        mFloatAction.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share_weather:
                CoreManager.getImpl(ICoreProvider.class).showShareDialog(this,true);
                break;
            case R.id.share_app:
                CoreManager.getImpl(ICoreProvider.class).showShareDialog(this,false);
                break;
            case R.id.about:
                CoreManager.getActivityRouter(IActivityRouter.class).toAboutActivity();
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

}
