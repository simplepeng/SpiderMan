package example.simple.spiderman;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.simple.spiderman.SpiderMan;
import com.simple.spiderman.utils.CrashModel;
import com.simple.spiderman.utils.SpiderManUtils;

import java.io.File;
import java.io.FileWriter;


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
            public void onCrash(Thread t, Throwable ex) {
                saveCrash(t, ex);
                SpiderManUtils.killApp();
            }
        });

    }

    private void saveCrash(Thread t, Throwable ex) {
        CrashModel model = SpiderManUtils.parseCrash(this, ex);
        String logPath = this.getApplicationContext().getCacheDir().getAbsolutePath() + File.separator + "log.txt";
        saveToFile(model.toString(), logPath);
//        showToast(model.toString());
    }

    public static void saveToFile(String text, String filePath) {
        try {
            File logFile = new File(filePath);
            FileWriter writer = new FileWriter(logFile);
            writer.write(text);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
