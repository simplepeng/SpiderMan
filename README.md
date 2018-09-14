# SpiderMan

 [![Travis (.org)](https://img.shields.io/badge/jcenter-1.0.5-blue.svg)](https://bintray.com/simplepeng/maven/SpiderMan)

SpiderMan能为您做的：

* Release环境不弹出难看的崩溃框啦！
* Debug环境可以在手机上显示崩溃信息啦！
* 再也不用担心测试妹妹给你重现怎么操作崩溃的啦！
* 再也不用担心产品相关人员给你说哪儿哪儿崩溃，但是又重现不了的尴尬啦！
* 再也不用担心某些rom禁止异常输出啦！
* 再也不用担心开发工具log信息时灵时不灵啦

|                         Release环境                          |                          Debug环境                           |                            Share                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![](/Users/simple/Desktop/WorkSpace/ws-android/SpiderMan/statics/release.gif) | ![](/Users/simple/Desktop/WorkSpace/ws-android/SpiderMan/statics/debug.gif) | ![](https://ws1.sinaimg.cn/mw690/00677ch9gy1ftoekwmvl3j30af0hygof) |




## 引入依赖

```groovy
implementation 'com.simple:spiderman:1.0.5'
```

## 初始化

> 放到Application的初始化中，因为static了传入的context，并且放在其他Library初始化的前面

```java
SpiderMan.init(this)
                //设置回调异常信息，友盟等第三方崩溃信息收集平台会用到,
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
                    /**
                     *
                     * @param t
                     * @param ex
                     * @param model 崩溃信息记录，包含设备信息
                     */
                    @Override
                    public void onCrash(Thread t, Throwable ex, CrashModel model) {

                    }
                });

```

## Demo中的示例代码

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SpiderMan.init(this)
                //设置回调异常信息，友盟等第三方崩溃信息收集平台会用到,
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
                    /**
                     *
                     * @param t
                     * @param ex
                     * @param model 崩溃信息记录，包含设备信息
                     */
                    @Override
                    public void onCrash(Thread t, Throwable ex, CrashModel model) {
                        showToast(model.toString());
                    }
                });

    }

    private void showToast(final String text) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

## CrashModel

> 崩溃信息记录实体，包含设备信息

```java
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
    private Device device;
    
    public static class Device implements Parcelable {
        //设备名
        private String model = Build.MODEL;
        //设备厂商
        private String brand = Build.BRAND;
        //系统版本号
        private String version = String.valueOf(Build.VERSION.SDK_INT);
    }
}
```

## 版本迭代

* 1.0.5 奔溃文本分享美化排版
* 1.0.4 崩溃输出改为error级别
* 1.0.3 增加 拷贝/分享 崩溃文字/图片信息
* 1.0.2 重构，新增设备信息
* 1.0.1 去除 allowBackup，label
* 1.0.0 首次上传

