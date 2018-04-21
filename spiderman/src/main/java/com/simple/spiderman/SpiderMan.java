package com.simple.spiderman;

import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class SpiderMan implements Thread.UncaughtExceptionHandler {

    private static SpiderMan spiderMan = new SpiderMan();

    private Context mContext;
    private Thread.UncaughtExceptionHandler mExceptionHandler;

    private Builder mBuilder;

    private SpiderMan() {

    }

    public static SpiderMan getInstance() {
        return spiderMan;
    }

    public Builder init(Context context) {
        this.mContext = context;
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mBuilder = new Builder();
        return mBuilder;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {
        if (mBuilder == null)return;
        if (mBuilder.mOnCrashListener != null) {
            mBuilder.mOnCrashListener.onCrash(t, ex);
        }
        if (mBuilder.mEnable){
            handleException(ex);
        }else {
            if (mExceptionHandler != null){
                mExceptionHandler.uncaughtException(t,ex);
            }
        }
    }

    private void handleException(Throwable ex) {

        if (mBuilder.mEnable && mBuilder.mShowCrashMessage) {
            Intent intent = new Intent(mContext, CrashActivity.class);
            intent.putExtra(CrashActivity.EXCEPTION_MSG, ex);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }

//        System.exit(1);//关闭已奔溃的app进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex);
    }


    public class Builder{

        private boolean mEnable;
        private boolean mShowCrashMessage;
        private OnCrashListener mOnCrashListener;

        public Builder setEnable(boolean enable) {
            this.mEnable = enable;
            return this;
        }

        public Builder showCrashMessage(boolean show) {
            this.mShowCrashMessage = show;
            return this;
        }

        public void setOnCrashListener(OnCrashListener listener) {
            this.mOnCrashListener = listener;
        }


    }
}