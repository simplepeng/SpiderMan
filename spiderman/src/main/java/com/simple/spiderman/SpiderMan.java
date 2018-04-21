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
        if (mBuilder.mCatchEnable){
            handleException(ex);
        }else {
            if (mExceptionHandler != null){
                mExceptionHandler.uncaughtException(t,ex);
            }
        }
    }

    private void handleException(Throwable ex) {
        ex.printStackTrace();
        String message;
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        message = writer.toString();

        Intent intent = new Intent(mContext, CrashActivity.class);
        intent.putExtra(CrashActivity.EXCEPTION_MSG, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

//        System.exit(1);//关闭已奔溃的app进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex);
    }


    public class Builder{

        private boolean mCatchEnable;
        private OnCrashListener mOnCrashListener;

        public Builder setCatchEnable(boolean enable) {
            this.mCatchEnable = enable;
            return this;
        }

        public Builder setOnCrashListener(OnCrashListener listener) {
            this.mOnCrashListener = listener;
            return this;
        }
    }
}