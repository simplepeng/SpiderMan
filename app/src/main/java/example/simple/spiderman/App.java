package example.simple.spiderman;

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

        //更改主题颜色
        SpiderMan.setTheme(R.style.SpiderManTheme_Dark);
    }
}
