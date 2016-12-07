package com.silencedut.knowweather.citys.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.silencedut.knowweather.ModelManager;
import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.model.CityModel;
import com.silencedut.knowweather.model.WeatherModel;
import com.silencedut.knowweather.utils.UIUtil;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * Created by SilenceDut on 16/10/21 .
 */

public class FollowedCityHolder extends BaseViewHolder<FollowedCityData> {
    public static final int[] BLUR_IMAGE = {R.mipmap.blur0, R.mipmap.blur1, R.mipmap.blur2, R.mipmap.blur3, R.mipmap.blur4, R.mipmap.blur5};

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.city_temp)
    TextView mCityTemp;
    @BindView(R.id.city_name)
    TextView mCityName;
    @BindView(R.id.city_status)
    TextView mCityStatus;
    @BindView(R.id.content)
    RelativeLayout mContent;
    @BindView(R.id.checked)
    ImageView mChecked;
    @BindView(R.id.delete)
    ImageView mDelete;
    @BindView(R.id.hover)
    View mHover;


    private CityWeatherAdapter mCityWeatherAdapter;
    private FollowedCityData mFollowedCityData;
    private Drawable mDrawableLocation;
    private CityModel mCityModel;

    public FollowedCityHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
        mCityWeatherAdapter = (CityWeatherAdapter) mBaseAdapter;
        mCityModel = ModelManager.getModel(CityModel.class);

    }

    @Override
    public void initViews() {
        super.initViews();
        mDrawableLocation = UIUtil.getDrawable(getContext(), R.mipmap.location);
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

        Drawable drawableLeft = UIUtil.getDrawable(getContext(), Constants.getIconId(data.weatherStatus));
        drawableLeft.setBounds(0, 0, UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small), UIUtil.dipToPx(getContext(), R.dimen.common_icon_size_small));
        mCityStatus.setCompoundDrawables(drawableLeft, null, null, null);

        mDelete.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);
        mHover.setVisibility(mCityWeatherAdapter.mIsDeleting ? View.VISIBLE : View.GONE);

        if (data.cityId.equals(mCityModel.getLocationCityId())) {
            mDelete.setVisibility(View.GONE);
            mHover.setVisibility(View.GONE);

            mCityName.setCompoundDrawables(mDrawableLocation, null, null, null);
        } else {
            mCityName.setCompoundDrawables(null, null, null, null);
        }

        boolean isDefault = data.cityId.equals(mCityModel.getDefaultId());
        mChecked.setVisibility(isDefault ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getContentViewId() {
        return R.layout.item_followed_city;
    }

    @OnLongClick(R.id.content)
    boolean showDelete() {
        updateAdapter(true);
        return true;
    }

    private void updateAdapter(boolean deleting) {
        mCityWeatherAdapter.setDeleteAction(deleting);
        mCityWeatherAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.content, R.id.delete})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.content:

                if (!mCityWeatherAdapter.mIsDeleting) {

                    mCityModel.setDefaultId(mFollowedCityData.cityId);
                    ModelManager.getModel(WeatherModel.class).updateWeather(mFollowedCityData.cityId);
                }
                updateAdapter(false);
                break;
            case R.id.delete:
                mCityModel.unFollowCity(mFollowedCityData.cityId);
                mCityWeatherAdapter.getData().remove(getLayoutPosition());
                updateAdapter(true);
                break;
        }
    }

}
