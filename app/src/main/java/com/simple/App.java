package com.simple;

import android.app.Application;

import com.simple.spiderman.SpiderMan;

/**
 * author : ChenPeng
 * date : 2018/4/21
 * description :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SpiderMan.getInstance()
                .init(this)
                .setEnable(true)
                .showCrashMessage(true)
                .setOnCrashListener(new SpiderMan.OnCrashListener() {
                    @Override
                    public void onCrash(Thread t, Throwable ex) {

                    }
                });

    }
}
