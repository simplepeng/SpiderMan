package com.simple.spiderman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.StyleRes;

@SuppressLint("StaticFieldLeak")
public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static Context mContext;

    private static OnCrashListener mOnCrashListener;

    private SpiderMan() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    protected static void init(Context context) {
        mContext = context;
        new SpiderMan();
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        callbackCrash(t, ex);
    }

    public static void setTheme(@StyleRes int themeId) {

    }

    public static void show(Throwable e) {

    }

    public static Context getContext() {
        if (mContext == null) {
            throw new NullPointerException("Please call init method in Application onCreate");
        }
        return mContext;
    }

    public static void setOnCrashListener(OnCrashListener listener) {
        mOnCrashListener = listener;
    }

   public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex);
    }

    private static void callbackCrash(Thread t, Throwable ex) {
        if (mOnCrashListener == null) return;
        mOnCrashListener.onCrash(t, ex);
    }
}