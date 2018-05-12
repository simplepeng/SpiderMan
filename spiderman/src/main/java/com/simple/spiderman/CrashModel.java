package com.simple.spiderman;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : ChenPeng
 * date : 2018/4/21
 * description :
 */
public class CrashModel implements Parcelable {

    private Throwable ex;
    private String packageName;
    private String exceptionMsg;
    private String className;
    private String fileName;
    private String methodName;
    private int lineNumber;
    private String exceptionType;
    private String fullException;
    private long time;

    protected CrashModel(Parcel in) {
        ex = (Throwable) in.readSerializable();
        exceptionMsg = in.readString();
        className = in.readString();
        fileName = in.readString();
        methodName = in.readString();
        lineNumber = in.readInt();
        exceptionType = in.readString();
        fullException = in.readString();
        time = in.readLong();
    }

    public CrashModel() {
    }

    public static final Creator<CrashModel> CREATOR = new Creator<CrashModel>() {
        @Override
        public CrashModel createFromParcel(Parcel in) {
            return new CrashModel(in);
        }

        @Override
        public CrashModel[] newArray(int size) {
            return new CrashModel[size];
        }
    };

    public Throwable getEx() {
        return ex;
    }

    public void setEx(Throwable ex) {
        this.ex = ex;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getFullException() {
        return fullException;
    }

    public void setFullException(String fullException) {
        this.fullException = fullException;
    }

    public String getPackageName() {
        return getClassName().replace(getFileName(), "");
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(ex);
        dest.writeString(exceptionMsg);
        dest.writeString(className);
        dest.writeString(fileName);
        dest.writeString(methodName);
        dest.writeInt(lineNumber);
        dest.writeString(exceptionType);
        dest.writeString(fullException);
        dest.writeLong(time);
    }
}
