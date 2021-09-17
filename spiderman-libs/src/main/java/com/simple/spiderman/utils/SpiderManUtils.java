package com.simple.spiderman.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class SpiderManUtils {

    /**
     * 把Throwable解析成CrashModel实体
     */
    public static CrashModel parseCrash(Context context, Throwable ex) {
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

            StackTraceElement element = parseThrowable(context, ex);
            if (element == null) return model;

            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);

            model.setFullException(sw.toString());

            model.setVersionCode(SpiderManUtils.getVersionCode(context));
            model.setVersionName(SpiderManUtils.getVersionName(context));
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    /**
     * 把Throwable解析成StackTraceElement
     */
    public static StackTraceElement parseThrowable(Context context, Throwable ex) {
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) return null;
        StackTraceElement element;
        String packageName = context.getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

    /**
     * 获取缓存目录
     */
    public static String getCachePath(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获取versionCode
     */
    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
        } catch (Exception e) {

        }
        return versionCode;
    }

    /**
     * 获取versionName
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = String.valueOf(packageInfo.versionName);
        } catch (Exception e) {

        }
        return versionName;
    }

    /**
     * 用反射获取Application
     */
    public static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("you should init first");
            }
            return (Application) app;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("you should init first");
    }

    public static void killApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
