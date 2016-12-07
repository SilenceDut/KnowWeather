package com.silencedut.knowweather.weather.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.silencedut.knowweather.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/17.
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private int[] tabIconIds = {R.drawable.tab_city_drawable, R.drawable.tab_weather_drawable, R.drawable.tab_user_drawable};
    private Context context;

    public MainPageAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    public View getTabView(int position, ViewGroup parent) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.tab_view, parent, false);
        ImageView img = (ImageView) view.findViewById(R.id.tab_icon);
        img.setImageResource(tabIconIds[position]);
        return view;
    }
}
