package com.simple.spiderman;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
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
        if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
            File cacheDir = SpiderMan.getContext().getExternalCacheDir();
            if (cacheDir == null) return SpiderMan.getContext().getCacheDir().getAbsolutePath();
            return cacheDir.getAbsolutePath();
        }
        return SpiderMan.getContext().getCacheDir().getAbsolutePath();
    }
}
