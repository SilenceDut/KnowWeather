package com.silencedut.setting.ui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.baselib.commonhelper.persistence.PreferencesHelper;
import com.silencedut.router.Router;
import com.silencedut.setting.R;
import com.silencedut.setting.R2;
import com.silencedut.weather_core.CoreManager;
import com.silencedut.weather_core.api.weatherprovider.IWeatherProvider;
import com.silencedut.weather_core.callback.EventCenter;
import com.silencedut.weather_core.corebase.BaseFragment;
import com.silencedut.weather_core.corebase.ResourceProvider;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R2.id.theme_switch)
    SwitchCompat mThemeSwitch;
    @BindView(R2.id.notification_theme)
    TextView mNotificationTheme;
    @BindView(R2.id.alarm_switch)
    SwitchCompat mAlarmSwitch;
    @BindView(R2.id.update_schedule)
    TextView mUpdateSchedule;

    private AlertDialog.Builder mNotificationThemeDialog;
    private AlertDialog.Builder mScheduleDialog;
    private String[] mScheduleKeys;

    public static BaseFragment newInstance() {
        SettingFragment settingFragment;
        settingFragment = new SettingFragment();
        return settingFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.setting_fragment;
    }

    @Override
    public void initViews() {

        mScheduleKeys = getResources().getStringArray(R.array.setting_schedule);

        mThemeSwitch.setChecked(PreferencesHelper.get(ResourceProvider.NOTIFICATION_ALLOW, true));
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesHelper.put(ResourceProvider.NOTIFICATION_ALLOW, isChecked);
                Router.instance().getReceiver(EventCenter.NotificationStatus.class).onAllowNotification(isChecked);
            }
        });

        mNotificationThemeDialog = new AlertDialog.Builder(getContext(), R.style.core_AlertDialogStyle);
        mNotificationThemeDialog.setTitle(R.string.notification_theme);
        int which = PreferencesHelper.get(ResourceProvider.NOTIFICATION_THEME, 1);
        mNotificationTheme.setText(ResourceProvider.getNotificationName(which));

        mAlarmSwitch.setChecked(PreferencesHelper.get(ResourceProvider.ALARM_ALLOW, false));

        //close alarm this version
        PreferencesHelper.put(ResourceProvider.ALARM_ALLOW, false);

        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesHelper.put(ResourceProvider.ALARM_ALLOW, isChecked);
            }
        });

        mScheduleDialog = new AlertDialog.Builder(getContext(), R.style.core_AlertDialogStyle);
        mScheduleDialog.setTitle(R.string.update_allow);
        int scheduleWhich = PreferencesHelper.get(ResourceProvider.POLLING_TIME, 0);
        mUpdateSchedule.setText(mScheduleKeys[scheduleWhich]);
        if (isAdded()) {
            CoreManager.getImpl(IWeatherProvider.class).startService(getActivity(), scheduleWhich != mScheduleKeys.length - 1);
        }

    }

    @OnClick({R2.id.notification_choose, R2.id.update_allow, R2.id.about})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.notification_choose) {
            int which = PreferencesHelper.get(ResourceProvider.NOTIFICATION_THEME, 1);
            mNotificationThemeDialog.setSingleChoiceItems(R.array.setting_notification_theme_key, which, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (which == 0) {
                        Toast.makeText(getContext(), R.string.follow_system_warn, Toast.LENGTH_LONG).show();
                    }
                    PreferencesHelper.put(ResourceProvider.NOTIFICATION_THEME, which);
                    mNotificationTheme.setText(ResourceProvider.getNotificationName(which));
                    Router.instance().getReceiver(EventCenter.NotificationStatus.class).onUpdateNotification();
                }
            });
            mNotificationThemeDialog.show();

        } else if (i == R.id.update_allow) {
            int whichSchedule = PreferencesHelper.get(ResourceProvider.POLLING_TIME, 0);
            mScheduleDialog.setSingleChoiceItems(R.array.setting_schedule, whichSchedule, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PreferencesHelper.put(ResourceProvider.POLLING_TIME, which);

                    mUpdateSchedule.setText(mScheduleKeys[which]);

                    dialog.dismiss();
                }
            });
            mScheduleDialog.show();

        } else if (i == R.id.about) {
            AboutActivity.navigationActivity(getActivity());

        }
    }

}
