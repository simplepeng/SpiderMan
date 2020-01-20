# SpiderMan

![MIT](https://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square) ![](https://img.shields.io/badge/Jcenter-v1.1.4-orange.svg?style=flat-square) ![](https://img.shields.io/badge/API-14%2B-brightgreen?style=flat-square) ![](https://img.shields.io/badge/Size-40k-yellow?style=flat-square) ![](https://img.shields.io/badge/Author-simplepeng-red?style=flat-square)


SpiderMan能为您做的事：

* 在Android手机上显示闪退崩溃信息，直接分享给相关开发人员!
* 再也不用担心测试妹妹给你重现怎样操作才能触发闪退崩溃的尴尬！
* 再也不用担心产品给你说哪儿哪儿会闪退崩溃，但是又不能场景还原的无奈！
* 再也不用担心某些国产Rom禁止异常log输出！
* 再也不用担心开发工具异常log信息输出时灵时不灵！

|                      Debug环境                       |                        Share                         |
| :--------------------------------------------------: | :--------------------------------------------------: |
| ![](https://i.loli.net/2019/02/24/5c726eacdd5b4.png) | ![](https://i.loli.net/2019/02/24/5c726ecdedd97.png) |

## 引入依赖

从`1.1.4`版本开始全面迁移到`androidx`，如不想迁移到`androidx`请继续使用`1.1.3`版本。

```groovy
def spider_man = "1.1.4"
```

### 方式一

```groovy
debugImplementation "com.simple:spiderman:$spider_man"
releaseImplementation "com.simple:spiderman-no-op:$spider_man"
```

### 方式二

```java
implementation "com.simple:spiderman:$spider_man"
```

上面`方式一`debug环境有奔溃信息提示，release环境则没有，`方式二`都有，但是记得添加混淆。

## 初始化

放到Application的`onCreate()`初始化中，因为static了传入的context，并且放在其他Library初始化的前面。

```java
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //放在其他库初始化前
        SpiderMan.init(this);
    }
}
```

## 直接显示错误页面

调用`SpiderMan.show(Throwable e)`方法

```java
try {
      String text = null;
      text.toUpperCase();
    } catch (Exception e) {
      SpiderMan.show(e);
}
```

## 冲突

### androidx

项目已经依赖了`androidx.appcompat:appcompat`包，如果产生冲突请使用下面的方式依赖。

```groovy
debugImplementation("com.simple:spiderman:$spider_man") {
        exclude group: "androidx.appcompat"
    }
    releaseImplementation("com.simple:spiderman-no-op:$spider_man") {
        exclude group: "androidx.appcompat"
    }
```

### support

项目已经依赖了`com.android.support:appcompat-v7`包，如果产生冲突请使用下面的方式依赖。

```groovy
debugImplementation("com.simple:spiderman:$spider_man") {
    exclude group: "com.android.support"
}

releaseImplementation("com.simple:spiderman-no-op:$spider_man") {
    exclude group: "com.android.support"
}
```

## 混淆

```java
-keep class com.simple.spiderman.** { *; }
-keepnames class com.simple.spiderman.** { *; }
-keep public class * extends android.app.Activity
-keep class * implements Android.os.Parcelable {
    public static final Android.os.Parcelable$Creator *;
}
# support
-keep public class * extends android.support.annotation.** { *; }
-keep public class * extends android.support.v4.content.FileProvider
# androidx
-keep public class * extends androidx.annotation.** { *; }
-keep public class * extends androidx.core.content.FileProvider
```

## 自定义界面样式

```java
SpiderMan.init(this)
         .setTheme(R.style.SpiderManTheme_Dark);
```

`SpiderMan`内置了两种主题样式`light`和`dark`。

|                        light                         |                         dark                         |                        custom                        |
| :--------------------------------------------------: | :--------------------------------------------------: | :--------------------------------------------------: |
| ![](https://i.loli.net/2019/02/24/5c726ef04a909.png) | ![](https://i.loli.net/2019/02/24/5c726f0dc7159.png) | ![](https://i.loli.net/2019/02/24/5c72a0f278b9b.png) |

所有自定义属性定义在`attrs.xml`中

* smToolbar：toolbar的背景色
* smToolbarText：toolb title的颜色
* smToolbarShareText：分享文字按钮的颜色
* smContentBackground：toolb下方内容的背景色
* smIdentText：标签名字的颜色
* smDescText：标签描述的颜色

具体可以参考`app`中的用法。

## 赞助

如果您觉得`SpideMan`帮助了您，可选择精准扶贫，要是`10.24`作者就在这里🙇🙇🙇啦！

您的支持是作者继续努力创作的动力😁😁😁

萌戳下方链接精准扶贫⤵️⤵️⤵️

**[扶贫方式](https://simplepeng.github.io/merge_pay_code/)**

## 版本迭代

* 1.1.4 切换到androidx
* 1.1.3 change minSdkVersion to 14
* 1.1.2 解决FileProvider file_path重名bug(bug来源LuckSiege/PictureSelector)
* 1.1.1 新增直接显示错误页面的方法`SpiderMan.show(Throwable e)`，优化错误类型
* 1.1.0  增加自定义界面主题和国际化
* 1.0.9 增加appcompat包冲突解决方案
* 1.0.8 发现很多小伙伴不会代理异常收集，所以删除了异常回调
* 1.0.7 删除spiderman-no-op never-crash，优化报错类型显示
* 1.0.6 增加spiderman-no-op
* 1.0.5 奔溃文本分享美化排版
* 1.0.4 崩溃输出改为error级别
* 1.0.3 增加 拷贝/分享 崩溃文字/图片信息
* 1.0.2 重构，新增设备信息
* 1.0.1 去除 allowBackup，label
* 1.0.0 首次上传

