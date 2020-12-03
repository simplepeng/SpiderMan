package com.simple.spiderman;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

class Utils {

    static CrashModel parseCrash(Throwable ex) {
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

            StackTraceElement element = parseThrowable(ex);
            if (element == null) return model;

            model.setLineNumber(element.getLineNumber());
            model.setClassName(element.getClassName());
            model.setFileName(element.getFileName());
            model.setMethodName(element.getMethodName());
            model.setExceptionType(exceptionType);

            model.setFullException(sw.toString());

            model.setVersionCode(Utils.getVersionCode());
            model.setVersionName(Utils.getVersionName());
        } catch (Exception e) {
            return model;
        }
        return model;
    }

    static StackTraceElement parseThrowable(Throwable ex) {
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) return null;
        StackTraceElement element;
        String packageName = SpiderMan.getContext().getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

    static String getCachePath() {
        return SpiderMan.getContext().getCacheDir().getAbsolutePath();
    }

    static String getVersionCode() {
        String versionCode = "";
        try {
            PackageManager pm = SpiderMan.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(SpiderMan.getContext().getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
        } catch (Exception e) {

        }
        return versionCode;
    }

    static String getVersionName() {
        String versionName = "";
        try {
            PackageManager pm = SpiderMan.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(SpiderMan.getContext().getPackageName(), 0);
            versionName = String.valueOf(packageInfo.versionName);
        } catch (Exception e) {

        }
        return versionName;
    }

    static Application getApplicationByReflect() {
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
}
