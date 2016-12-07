package com.silencedut.knowweather.weather.ui.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.common.adapter.BaseViewHolder;
import com.silencedut.knowweather.utils.UIUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 2016/11/16 .
 */

public class ShareHolder extends BaseViewHolder<ShareData> {

    @BindView(R.id.share_icon)
    ImageView mShareIcon;
    @BindView(R.id.share_tip)
    TextView mShareTip;
    private int mPosition;
    private ShareData mShareData;

    public static final int[] SHARE_ICONS = {R.mipmap.weixin, R.mipmap.moments, R.mipmap.qq, R.mipmap.kongjian};
    private static final String[] TIPS = {"微信", "朋友圈", "QQ", "QQ空间"};
    private static final SHARE_MEDIA[] SHAREMEDIAS = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};

    public ShareHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }


    @Override
    public void updateItem(ShareData data, int position) {
        mShareData = data;
        mShareIcon.setImageResource(SHARE_ICONS[position]);
        mShareTip.setText(TIPS[position]);
        mPosition = position;
    }

    @Override
    public int getContentViewId() {
        return R.layout.item_share;
    }

    @OnClick(R.id.share_container)
    public void onClick() {
        final UMImage umImage;
        Bitmap screenBitmap = null;
        ShareAction shareAction = new ShareAction((Activity) getContext()).setPlatform(SHAREMEDIAS[mPosition]).withTitle("知天气，天气尽在掌握之中").withText("简洁，实用，美观的天气应用,你的专属天气");
        if (mShareData.mIsWeather) {
            screenBitmap = UIUtil.takeScreenShot((Activity) getContext());
            if (screenBitmap == null) {
                Toast.makeText(getContext(), "抱歉，分享失败", Toast.LENGTH_LONG).show();
                return;
            }
            umImage = new UMImage(getContext(), screenBitmap);
            shareAction.withMedia(umImage);
        } else {
            umImage = new UMImage(getContext(), R.mipmap.icon);
            shareAction.withMedia(umImage).withTargetUrl("https://beta.bugly.qq.com/knowweather");
        }

        final Bitmap finalScreenBitmap = screenBitmap;

        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(getContext(), "分享成功", Toast.LENGTH_SHORT).show();
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                if (t != null) {
                    Toast.makeText(getContext(), "抱歉，分享失败", Toast.LENGTH_LONG).show();
                }
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }
        });
        shareAction.share();
        mShareData.mShareDialog.dismiss();

    }
}
