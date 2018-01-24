package com.silencedut.knowweather.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.silencedut.knowweather.R;
import com.silencedut.weather_core.corebase.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/17.
 */
public class MainPageAdapter extends FragmentPagerAdapter {
    private final List<Pair<BaseFragment,Integer>> mFragmentList = new ArrayList<>();

    private Context context;

    public MainPageAdapter(Context context, FragmentManager manager) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position).first;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Pair<BaseFragment,Integer> fragmentPair) {
        mFragmentList.add(fragmentPair);
    }

    public View getTabView(int position, ViewGroup parent) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.weather_tab_view, parent, false);
        ImageView img =  view.findViewById(R.id.tab_icon);
        img.setImageResource(mFragmentList.get(position).second);
        return view;
    }
}
