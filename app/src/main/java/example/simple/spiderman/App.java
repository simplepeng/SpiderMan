package example.simple.spiderman;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
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

        //更改主题颜色
        SpiderMan.setTheme(R.style.SpiderManTheme_Dark);

        //回调crash
        SpiderMan.setOnCrashListener(new SpiderMan.OnCrashListener() {
            @Override
            public void onCrash(CrashModel model) {
                printCrash(model);
            }
        });
    }

    private void printCrash(final CrashModel model) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("App", model.toString());
            }
        }).start();

//        Log.d("App","");
//        showToast(model.toString());
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
