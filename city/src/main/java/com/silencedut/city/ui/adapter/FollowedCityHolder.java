package com.silencedut.city.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;
import com.silencedut.baselib.commonhelper.utils.UIUtil;
import com.silencedut.city.R;
import com.silencedut.city.R2;
import com.silencedut.city.ui.CityModel;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.cityprovider.ICityProvider;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.corebase.ResourceProvider;
import com.silencedut.weather_core.location.ILocationApi;
import com.silencedut.weather_core.viewmodel.ModelProvider;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by SilenceDut on 16/10/21 .
 */

public class FollowedCityHolder extends BaseViewHolder<FollowedCityData> {
    public static final int[] BLUR_IMAGE = {R.mipmap.city_blur0, R.mipmap.city_blur1, R.mipmap.city_blur2, R.mipmap.city_blur3, R.mipmap.city_blur4, R.mipmap.city_blur5};

    @BindView(R2.id.image)
    ImageView mImage;
    @BindView(R2.id.city_temp)
    TextView mCityTemp;
    @BindView(R2.id.city_name)
    TextView mCityName;
    @BindView(R2.id.city_status)
    TextView mCityStatus;
    @BindView(R2.id.content)
    RelativeLayout mContent;
    @BindView(R2.id.checked)
    ImageView mChecked;
    @BindView(R2.id.delete)
    ImageView mDelete;
    @BindView(R2.id.hover)
    View mHover;

    private CityWeatherAdapter mCityWeatherAdapter;
    private FollowedCityData mFollowedCityData;
    private Drawable mDrawableLocation;
    private CityModel mCityModel;


    public FollowedCityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        mCityWeatherAdapter = (CityWeatherAdapter) mBaseAdapter;
        mCityModel = ModelProvider.getModel(getContext(),CityModel.class);
        initViews();

    }


    private void initViews() {
        mDrawableLocation = UIUtil.getDrawable(getContext(), R.mipmap.core_location);
        mDrawableLocation.setBounds(0, 0, UIUtil.dipToPx(getContext(), R.dimen.common_location_size), UIUtil.dipToPx(getContext(), R.dimen.common_location_size));
    }

    @Override
    public void updateItem(FollowedCityData data, int position) {
        if (data == null) {
            return;
        }

        mFollowedCityData = data;
        mImage.setScaleType(ImageView.ScaleType.FIT_XY);
        mImage.setImageResource(data.backgroundId);
        mCityTemp.setText(data.temp);
        mCityName.setText(data.cityName);
        mCityStatus.setText(data.weatherStatus);

        Drawable drawableLeft = UIUtil.getDrawable(getContext(), ResourceProvider.getIconId(data.weatherStatus));
        drawableLeft.setBounds(0, 0, UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small), UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small));
        mCityStatus.setCompoundDrawables(drawableLeft, null, null, null);

        mDelete.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);
        mHover.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);


        if (data.cityId.equals(CoreManager.getImpl(ILocationApi.class).getLocatedCityId())) {
            mDelete.setVisibility(View.GONE);
            mHover.setVisibility(View.GONE);

            mCityName.setCompoundDrawables(mDrawableLocation, null, null, null);
        } else {
            mCityName.setCompoundDrawables(null, null, null, null);
        }

        boolean isDefault = data.cityId.equals(CoreManager.getImpl(ICityProvider.class).getCurrentCityId());
        mChecked.setVisibility(isDefault ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getContentViewId() {
        return R.layout.city_item_followed_city;
    }

    @OnLongClick(R2.id.content)
    boolean showDelete() {
        updateAdapter(true);
        return true;
    }

    private void updateAdapter(boolean deleting) {
        mCityWeatherAdapter.setDeleteAction(deleting);
        mCityWeatherAdapter.notifyDataSetChanged();
    }

    @OnClick({R2.id.content, R2.id.delete})
    void onClick(View view) {
        int i = view.getId();
        if (i == R.id.content) {
            if (!mCityWeatherAdapter.mIsDeleting) {
                CoreManager.getImpl(IWeatherProvider.class).updateWeather(mFollowedCityData.cityId);
            }
            updateAdapter(false);

        } else if (i == R.id.delete) {
            mCityModel.deleteFollowedWeather(mFollowedCityData.cityId);
            updateAdapter(true);

        }
    }

}
