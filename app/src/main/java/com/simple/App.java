package com.simple;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.simple.spiderman.CrashModel;
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
                        showToast("诶，虽然发生异常了，但是就是不闪退");
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
