package com.simple.spiderman;

import android.content.Context;
import android.content.Intent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static SpiderMan spiderMan = new SpiderMan();

    private static Context mContext;
    private Thread.UncaughtExceptionHandler mExceptionHandler;
    private OnCrashListener mOnCrashListener;

    private SpiderMan() {
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static SpiderMan init(Context context) {
        mContext = context;
        return spiderMan;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {

        CrashModel model = parseCrash(ex);
        if (mOnCrashListener != null) {
            mOnCrashListener.onCrash(t, ex, model);
        }

        handleException(model);

    }

    private void handleException(CrashModel model) {


        Intent intent = new Intent(mContext, CrashActivity.class);
        intent.putExtra(CrashActivity.CRASH_MODEL, model);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        model.setEx(ex);
        model.setTime(new Date().getTime());
        if (ex.getCause() != null) {
            ex = ex.getCause();
        }
        model.setExceptionMsg(ex.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String exceptionType = ex.getClass().getName();

        if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            StackTraceElement element = ex.getStackTrace()[0];

            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);
        }

        model.setFullException(sw.toString());
        return model;
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex, CrashModel model);
    }

    public void setOnCrashListener(OnCrashListener listener) {
        this.mOnCrashListener = listener;
    }


}