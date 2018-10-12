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
                            if (isBlackScreenException(e)) {
                                mExceptionHandler.uncaughtException(Looper.getMainLooper().getThread(), e);
                            } else {
                                mOnCrashListener.onCrash(Looper.getMainLooper().getThread(), e, model);
                            }
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

//        CrashModel model = parseCrash(ex);
//        if (mOnCrashListener != null) {
//            mOnCrashListener.onCrash(t, ex, model);
//        }
//        mExceptionHandler.uncaughtException(t, ex);
    }

    private CrashModel parseCrash(Throwable ex) {
        CrashModel model = new CrashModel();
        try {
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
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    public boolean isBlackScreenException(Throwable e) {
        if (e == null) {
            return false;
        }
        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace == null || stackTrace.length == 0) {
            return false;
        }
//        if (stackTrace.length > 50) {
//            return false;
//        }
        for (StackTraceElement element : stackTrace) {
            if ("android.view.Choreographer".equals(element.getClassName())
                    && "Choreographer.java".equals(element.getFileName())
                    && "doFrame".equals(element.getMethodName())) {
                return true;
            }
        }
        return false;
    }

    public interface OnCrashListener {
        void onCrash(Thread t, Throwable ex, CrashModel model);
    }

    public void setOnCrashListener(OnCrashListener listener) {
        this.mOnCrashListener = listener;
    }


}