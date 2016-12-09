package com.silencedut.knowweather.user;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.BaseActivity;
import com.silencedut.knowweather.utils.LogHelper;
import com.silencedut.knowweather.utils.Version;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 2016/11/18 .
 */

public class AboutActivity extends BaseActivity {
    @BindView(R.id.title)
    Toolbar mTitle;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.mark)
    TextView mMark;
    @BindView(R.id.suggestion)
    TextView mSuggestion;
    @BindView(R.id.new_version)
    TextView mNewVersion;
    @BindView(R.id.new_version_tip)
    ImageView mNewVersionTip;

    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, AboutActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews() {
        setSupportActionBar(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about);

        mVersion.setText(mVersion.getText() + Version.getVersionName(this));

        loadUpgradeInfo();
    }

    private void loadUpgradeInfo() {

        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            LogHelper.i("AboutActivity", "no new version");
            return;
        }

        if (upgradeInfo.versionCode > Version.getVersionCode(this)) {
            mNewVersion.setText("有新版本更新");
            mNewVersionTip.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.mark, R.id.suggestion, R.id.update_version, R.id.pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mark:
                openAppMarket();
                break;
            case R.id.suggestion:
                ContactActivity.navigationActivity(this);
                break;
            case R.id.pay:
                PayActivity.navigationActivity(this);
                break;
            case R.id.update_version:
                if (mNewVersionTip.getVisibility() == View.VISIBLE) {

                    Beta.checkUpgrade();

                } else {
                    Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void openAppMarket() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException anf) {
            Toast.makeText(this,"未找到相关应用",Toast.LENGTH_SHORT).show();
        }

    }
}
