package com.simple.spiderman;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class SpiderMan implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "SpiderMan";

    private static SpiderMan spiderMan = new SpiderMan();

    private Thread.UncaughtExceptionHandler mExceptionHandler;
    private OnCrashListener mOnCrashListener;

    private SpiderMan() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Looper.loop();
                    } catch (Throwable e) {
                        if (mOnCrashListener != null) {
                            CrashModel model = parseCrash(e);
                            mOnCrashListener.onCrash(Looper.getMainLooper().getThread(), e, model);
                        }
                    }
                }
            }
        });
        mExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static SpiderMan init(Context context) {
        return spiderMan;
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {

        CrashModel model = parseCrash(ex);
        if (mOnCrashListener != null) {
            mOnCrashListener.onCrash(t, ex, model);
        }
        mExceptionHandler.uncaughtException(t, ex);
    }

    private CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        model.setEx(ex);
        model.setTime(new Date().getTime());
        StringBuilder msgBuilder = new StringBuilder();
        String exceptionMsg = null;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String exceptionType = ex.getClass().getName();
//        while (ex != null) {
        exceptionMsg = ex.getMessage();
        msgBuilder.append(ex.getMessage());
        msgBuilder.append("\n");
        if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            StackTraceElement element = ex.getStackTrace()[0];
            model.setExceptionMsg(exceptionMsg);
            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);
        }
//            ex = ex.getCause();
//        }
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