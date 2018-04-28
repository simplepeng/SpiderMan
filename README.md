# SpiderMan

SpiderMan能为您做的：

* 正式环境不弹出难看的崩溃框啦！
* 测试环境可以在手机上显示崩溃信息啦！
* 再也不用担心测试妹妹给你重现怎么操作崩溃的啦！
* 再也不用担心产品相关人员给你说哪儿哪儿崩溃，但是又重现不了的尴尬啦！
* 再也不用担心某些rom禁止异常输出啦！
* 再也不用担心开发工具log信息时灵时不灵啦

![](http://p6uvwa6u4.bkt.clouddn.com/img/spiderman.png)


## 引入依赖

```groovy
implementation 'com.simple:spiderman:1.0.0'
```

## 初始化

```java
SpiderMan.getInstance()
                .init(this)
                //设置是否捕获异常，不弹出崩溃框
                .setEnable(true)
                //设置是否显示崩溃信息展示页面
                .showCrashMessage(true)
                //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
                    @Override
                    public void onCrash(Thread t, Throwable ex) {

                    }
                });
```

## 版本迭代

* 1.0.1 去除 allowBackup，label
* 1.0.0 首次上传

## 联系方式

QQ群：274306954

[快速加群](https://shang.qq.com/wpa/qunwpa?idkey=cbcf9a42faf2fe730b51004d33ac70863617e6999fce7daf43231f3cf2997460)

如有好的建议或意见可提issue或加群联系我