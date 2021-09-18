package com.simple.spiderman;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.StyleRes;

import com.simple.spiderman.utils.CrashModel;
import com.simple.spiderman.utils.SpiderManUtils;

@SuppressLint("StaticFieldLeak")
public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static Context mContext;

    private static int mThemeId = R.style.SpiderManTheme_Light;

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

        //处理自己的逻辑
        CrashModel model = SpiderManUtils.parseCrash(mContext, ex);
        handleException(model);

        //回调异常
        callbackCrash(t, ex);

        //杀掉App进程
        SpiderManUtils.killApp();
    }

    public static void setTheme(@StyleRes int themeId) {
        mThemeId = themeId;
    }

    protected static int getThemeId() {
        return mThemeId;
    }

    private static void handleException(CrashModel model) {
        Intent intent = new Intent(getContext(), CrashActivity.class);
        intent.putExtra(CrashActivity.CRASH_MODEL, model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public static void show(Throwable e) {
        CrashModel model = SpiderManUtils.parseCrash(mContext, e);
        handleException(model);
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