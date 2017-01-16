package com.silencedut.knowweather.user;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.knowweather.R;
import com.silencedut.knowweather.common.BaseFragment;
import com.silencedut.knowweather.common.Constants;
import com.silencedut.knowweather.scheduleJob.PollingUtils;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.weather.callbacks.WeatherCallBack;
import com.silencedut.router.Router;

import butterknife.BindView;
import butterknife.OnClick;

import static com.silencedut.knowweather.R.array.schedule;

/**
 * Created by SilenceDut on 16/10/20.
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.theme_switch)
    SwitchCompat mThemeSwitch;
    @BindView(R.id.notification_theme)
    TextView mNotificationTheme;
    @BindView(R.id.alarm_switch)
    SwitchCompat mAlarmSwitch;
    @BindView(R.id.update_schedule)
    TextView mUpdateSchedule;

    private AlertDialog.Builder mNotificationThemeDialog;
    private AlertDialog.Builder mScheduleDialog;
    private String[] mScheduleKeys;

    public static UserFragment newInstance() {
        UserFragment userFragment;
        userFragment = new UserFragment();
        return userFragment;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initViews() {

        mScheduleKeys = getResources().getStringArray(schedule);

        mThemeSwitch.setChecked(PreferencesUtil.get(Constants.NOTIFICATION_ALLOW, true));
        mThemeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtil.put(Constants.NOTIFICATION_ALLOW, isChecked);
                Router.instance().getReceiver(WeatherCallBack.NotificationStatus.class).onAllowNotification(isChecked);
            }
        });

        mNotificationThemeDialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        mNotificationThemeDialog.setTitle(R.string.notification_theme);
        int which = PreferencesUtil.get(Constants.NOTIFICATION_THEME, 1);
        mNotificationTheme.setText(Constants.getNotificationName(which));

        mAlarmSwitch.setChecked(PreferencesUtil.get(Constants.ALARM_ALLOW, false));

        //close alarm this version
        PreferencesUtil.put(Constants.ALARM_ALLOW, false);

        mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferencesUtil.put(Constants.ALARM_ALLOW, isChecked);
            }
        });

        mScheduleDialog = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        mScheduleDialog.setTitle(R.string.update_allow);
        int scheduleWhich = PreferencesUtil.get(Constants.POLLING_TIME, 0);
        mUpdateSchedule.setText(mScheduleKeys[scheduleWhich]);
        if (isAdded()) {
            PollingUtils.startService(getActivity(), scheduleWhich != mScheduleKeys.length - 1);
        }

    }

    @OnClick({R.id.notification_choose, R.id.update_allow, R.id.about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notification_choose:
                int which = PreferencesUtil.get(Constants.NOTIFICATION_THEME, 1);
                mNotificationThemeDialog.setSingleChoiceItems(R.array.notification_theme_key, which, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if(which==0) {
                            Toast.makeText(getContext(), R.string.follow_system_warn, Toast.LENGTH_LONG).show();
                        }
                        PreferencesUtil.put(Constants.NOTIFICATION_THEME, which);
                        mNotificationTheme.setText(Constants.getNotificationName(which));
                        Router.instance().getReceiver(WeatherCallBack.NotificationStatus.class).onUpdateNotification();
                    }
                });
                mNotificationThemeDialog.show();
                break;
            case R.id.update_allow:
                int whichSchedule = PreferencesUtil.get(Constants.POLLING_TIME, 0);
                mScheduleDialog.setSingleChoiceItems(schedule, whichSchedule, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferencesUtil.put(Constants.POLLING_TIME, which);

                        mUpdateSchedule.setText(mScheduleKeys[which]);

                        dialog.dismiss();
                    }
                });
                mScheduleDialog.show();
                break;
            case R.id.about:
                AboutActivity.navigationActivity(getActivity());
                break;
        }
    }

}
