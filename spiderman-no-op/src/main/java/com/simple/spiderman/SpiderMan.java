package com.simple.spiderman;

import android.content.Context;

public class SpiderMan {

    public static final String TAG = "SpiderMan";

    private SpiderMan() {
    }

    protected static void init(Context context) {

    }

    public static void setTheme(int themeId) {

    }

    public static void show(Throwable e) {

    }

    public static void setOnCrashListener(OnCrashListener listener) {

    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex);
    }
}