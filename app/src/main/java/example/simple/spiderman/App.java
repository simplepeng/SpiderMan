package example.simple.spiderman;

import android.app.Application;
import android.widget.Toast;

import com.simple.spiderman.SpiderMan;
import com.simple.spiderman.utils.CrashModel;
import com.simple.spiderman.utils.SpiderManUtils;

import java.io.File;


/**
 * author : ChenPeng
 * date : 2018/4/21
 * description :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpiderMan.init(this);

        //更改主题颜色
        SpiderMan.setTheme(R.style.SpiderManTheme_Dark);

        //回调crash
        SpiderMan.setOnCrashListener(new SpiderMan.OnCrashListener() {
            @Override
            public void onCrash(Thread t, Throwable ex) {
                saveCrash(t, ex);
            }
        });

    }

    private void saveCrash(Thread t, Throwable ex) {
        CrashModel model = SpiderManUtils.parseCrash(this, ex);
        String time = String.valueOf(System.currentTimeMillis());
        String logPath = this.getApplicationContext().getCacheDir().getAbsolutePath() + File.separator + "log-" + time + ".txt";
        String text = SpiderManUtils.getShareText(this.getApplicationContext(), model);
        saveToFile(text, logPath);
//        showToast(model.toString());
    }

    public static void saveToFile(String text, String filePath) {
        try {
            SpiderManUtils.saveTextToFile(text, filePath);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
