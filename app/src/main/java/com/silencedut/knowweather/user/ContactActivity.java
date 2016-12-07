package com.silencedut.knowweather.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.BaseActivity;

import butterknife.BindView;

/**
 * Created by SilenceDut on 2016/12/1 .
 */

public class ContactActivity extends BaseActivity {
    @BindView(R.id.title)
    Toolbar mTitle;


    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, ContactActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_contact;
    }

    @Override
    public void initViews() {
        setSupportActionBar(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.contact);
    }

}
