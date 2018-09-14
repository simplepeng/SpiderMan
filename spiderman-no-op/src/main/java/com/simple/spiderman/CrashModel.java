package com.simple.spiderman;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : ChenPeng
 * date : 2018/4/21
 * description :
 */
public class CrashModel implements Parcelable {

    /**
     * 崩溃主体信息
     */
    private Throwable ex;
    /**
     * 包名，暂时未使用
     */
    private String packageName;
    /**
     * 崩溃主信息
     */
    private String exceptionMsg;
    /**
     * 崩溃类名
     */
    private String className;
    /**
     * 崩溃文件名
     */
    private String fileName;
    /**
     * 崩溃方法
     */
    private String methodName;
    /**
     * 崩溃行数
     */
    private int lineNumber;
    /**
     * 崩溃类型
     */
    private String exceptionType;
    /**
     * 全部信息
     */
    private String fullException;
    /**
     * 崩溃时间
     */
    private long time;
    /**
     * 设备信息
     */
    private Device device = new Device();

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

    public Device getDevice() {
        return device;
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

    public static class Device implements Parcelable {
        //设备名
        private String model = Build.MODEL;
        //设备厂商
        private String brand = Build.BRAND;
        //系统版本号
        private String version = String.valueOf(Build.VERSION.SDK_INT);

        public Device() {
        }

        protected Device(Parcel in) {
            model = in.readString();
            brand = in.readString();
            version = in.readString();
        }

        public static final Creator<Device> CREATOR = new Creator<Device>() {
            @Override
            public Device createFromParcel(Parcel in) {
                return new Device(in);
            }

            @Override
            public Device[] newArray(int size) {
                return new Device[size];
            }
        };

        public String getModel() {
            return model;
        }

        public String getBrand() {
            return brand;
        }

        public String getVersion() {
            return version;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(model);
            dest.writeString(brand);
            dest.writeString(version);
        }
    }

    @Override
    public String toString() {
        return "CrashModel{" +
                "ex=" + ex +
                ", packageName='" + packageName + '\'' +
                ", exceptionMsg='" + exceptionMsg + '\'' +
                ", className='" + className + '\'' +
                ", fileName='" + fileName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", lineNumber=" + lineNumber +
                ", exceptionType='" + exceptionType + '\'' +
                ", fullException='" + fullException + '\'' +
                ", time=" + time +
                ", device=" + device +
                '}';
    }
}
