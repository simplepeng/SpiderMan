package example.simple.spiderman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.TextView;

import com.simple.spiderman.SpiderMan;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CrashViewModel viewModel = new ViewModelProvider(this).get(CrashViewModel.class);

        TextView tv_buildType = findViewById(R.id.tv_buildType);
        tv_buildType.setText(BuildConfig.BUILD_TYPE);
        findViewById(R.id.btn_crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.makeCrash();
//                String text = null;
//                text.toUpperCase();
//                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String text = null;
                    text.toUpperCase();
                } catch (Exception e) {
                    SpiderMan.show(e);
                }
            }
        });
    }
}
