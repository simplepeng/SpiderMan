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
        //放在其他库初始化前
        SpiderMan.init(this)
                .setTheme(R.style.CustomTheme);
    }
}
