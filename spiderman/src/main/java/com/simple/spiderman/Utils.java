package com.simple.spiderman;

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
}
