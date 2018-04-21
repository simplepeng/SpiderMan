package com.simple.spiderman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * author : ChenPeng
 * date : 2018/4/20
 * description :
 */
public class CrashActivity extends AppCompatActivity{

    public static final String EXCEPTION_MSG = "exception_msg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
    }
}
