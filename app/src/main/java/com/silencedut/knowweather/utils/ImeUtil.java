package com.silencedut.knowweather.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ImeUtil {

    public static void hideIME(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            hideIME(activity, view);
        }
    }

    public static void hideIME(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void hideIME(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static void showIME(Activity activity, View view) {
        if (null == view) {
            view = activity.getCurrentFocus();
            if (null == view) {
                return;
            }
        }
        if (view.requestFocus()) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void showIME(View view) {
        if (view.requestFocus()) {
            ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }

    }
}
