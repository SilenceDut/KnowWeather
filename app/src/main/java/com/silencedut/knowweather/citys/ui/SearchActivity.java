package com.silencedut.knowweather.citys.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.citys.adapter.CityHolder;
import com.silencedut.knowweather.citys.adapter.CityInfoData;
import com.silencedut.knowweather.citys.adapter.HeaderData;
import com.silencedut.knowweather.citys.adapter.HeaderHolder;
import com.silencedut.knowweather.citys.ui.presenter.SearchCityView;
import com.silencedut.knowweather.citys.ui.presenter.SearchPresenter;
import com.silencedut.knowweather.common.BaseActivity;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.customview.SideLetterBar;
import com.silencedut.knowweather.utils.Check;
import com.silencedut.knowweather.utils.LogHelper;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by SilenceDut on 16/10/19.
 */

public class SearchActivity extends BaseActivity implements SearchCityView {

    @BindView(R.id.recyclerView)
    RecyclerView mAllCitiesRecyclerView;
    @BindView(R.id.tv_letter_overlay)
    TextView mTvLetterOverlay;
    @BindView(R.id.side)
    SideLetterBar mSide;
    @BindView(R.id.searchTextView)
    EditText mSearchTextView;
    @BindView(R.id.action_empty_btn)
    ImageButton mActionEmptyBtn;
    @BindView(R.id.search_result_view)
    RecyclerView mSearchResultView;
    @BindView(R.id.empty_view)
    LinearLayout mEmptyView;
    private SearchPresenter mSearchPresenter;


    private BaseRecyclerAdapter mSearchResultAdapter;

    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, SearchActivity.class);
        from.startActivity(intent);
    }

    public static void navigationFromApplication(Context from) {
        Intent intent = new Intent(from, SearchActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        from.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    public void initBeforeView() {
        mSearchPresenter = new SearchPresenter(this);
        mSearchPresenter.getAllCities();
    }

    @Override
    public void initViews() {
        initSearchView();
    }

    private int getLetterPosition(String letter, List<CityInfoData> allInfoDatas) {
        int position = 0;
        for (CityInfoData cityInfoData : allInfoDatas) {
            if (cityInfoData.getInitial().equals(letter)) {
                position = allInfoDatas.indexOf(cityInfoData);
            }
        }
        return position;
    }

    @Override
    public void onMatched(List<CityInfoData> result) {

        if (Check.isNull(result)) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
            mSearchResultAdapter.setData(result);

        }
    }

    @Override
    public void onAllCities(final List<CityInfoData> allInfoDatas) {


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAllCitiesRecyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter citiesAdapter = new BaseRecyclerAdapter(this);
        citiesAdapter.registerHolder(HeaderHolder.class, new HeaderData());
        citiesAdapter.registerHolder(CityHolder.class, allInfoDatas);
        mAllCitiesRecyclerView.setAdapter(citiesAdapter);

        LinearLayoutManager resultLayoutManager = new LinearLayoutManager(this);
        mSearchResultView.setLayoutManager(resultLayoutManager);
        mSearchResultAdapter = new BaseRecyclerAdapter(this);
        mSearchResultAdapter.registerHolder(CityHolder.class, R.layout.item_city);
        mSearchResultView.setAdapter(mSearchResultAdapter);

        mSide.setOverlay(mTvLetterOverlay);
        mSide.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                linearLayoutManager.scrollToPositionWithOffset(getLetterPosition(letter, allInfoDatas), 0);
            }
        });
    }

    private void initSearchView() {
        setCursorDrawable(R.drawable.color_cursor_white);

        mSearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                return true;
            }
        });

        mSearchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CharSequence text = mSearchTextView.getText();
                boolean hasText = !TextUtils.isEmpty(text);
                if (hasText) {
                    mActionEmptyBtn.setVisibility(VISIBLE);
                } else {
                    mActionEmptyBtn.setVisibility(GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    mActionEmptyBtn.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.GONE);
                    mSearchResultView.setVisibility(View.GONE);
                } else {
                    mActionEmptyBtn.setVisibility(View.VISIBLE);
                    mSearchResultView.setVisibility(View.VISIBLE);
                    mSearchPresenter.matchCities(keyword);
                }
            }
        });
    }


    public void setCursorDrawable(int drawable) {
        try {
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(mSearchTextView, drawable);
        } catch (Exception ignored) {
            LogHelper.e(ignored, ignored.toString());
        }
    }


    @OnClick({R.id.action_back, R.id.action_empty_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_back:
                finish();
                break;
            case R.id.action_empty_btn:
                mSearchTextView.setText("");
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
