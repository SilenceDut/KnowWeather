package com.silencedut.knowweather.common.customview;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.adapter.BaseRecyclerAdapter;
import com.silencedut.knowweather.weather.ui.adapter.ShareData;
import com.silencedut.knowweather.weather.ui.adapter.ShareHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SilenceDut on 2016/11/16 .
 */

public class ShareDialog {

    private Dialog mDialog;
    private BaseRecyclerAdapter mShareAdapter;
    private TextView mTitle;

    public ShareDialog(AppCompatActivity context) {
        mDialog = new Dialog(context, R.style.BottomDialog);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        mDialog.setContentView(dialogView);
        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.share_list);
        mTitle = (TextView) dialogView.findViewById(R.id.share_title);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        mShareAdapter = new BaseRecyclerAdapter(context);
        recyclerView.setAdapter(mShareAdapter);
        mShareAdapter.registerHolder(ShareHolder.class, R.layout.item_share);

    }

    public void dismiss() {
        if (mDialog == null) {
            return;
        }
        mDialog.dismiss();
    }

    public void isShowing() {
        if (mDialog == null) {
            return;
        }
        mDialog.isShowing();
    }

    public void show(boolean isWeather) {
        if (mDialog == null) {
            return;
        }
        List<ShareData> shareDatas = new ArrayList<>();
        for (int SHARE_ICON : ShareHolder.SHARE_ICONS) {
            shareDatas.add(new ShareData(isWeather, this));
        }
        mTitle.setText(isWeather ? R.string.share_weather : R.string.share_app);
        mShareAdapter.setData(shareDatas);
        mDialog.show();
    }
}
