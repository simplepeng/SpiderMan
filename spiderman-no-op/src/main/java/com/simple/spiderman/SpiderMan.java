package com.simple.spiderman;

import android.content.Context;

public class SpiderMan {

    public static final String TAG = "SpiderMan";
    private static SpiderMan spiderMan = new SpiderMan();

    private SpiderMan() {
    }

    public static SpiderMan init(Context context) {
        return spiderMan;
    }

    public void setTheme(int themeId) {

    }

}