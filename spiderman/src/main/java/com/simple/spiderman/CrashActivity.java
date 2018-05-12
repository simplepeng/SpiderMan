package com.simple.spiderman;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author : ChenPeng
 * date : 2018/4/20
 * description :
 */
public class CrashActivity extends Activity {

    public static final String CRASH_MODEL = "crash_model";
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        CrashModel model = getIntent().getParcelableExtra(CRASH_MODEL);
        if (model == null) {
            return;
        }
        model.getEx().printStackTrace();

        TextView tv_packageName = findViewById(R.id.tv_packageName);
        TextView textMessage = findViewById(R.id.textMessage);
        TextView tv_className = findViewById(R.id.tv_className);
        TextView tv_methodName = findViewById(R.id.tv_methodName);
        TextView tv_lineNumber = findViewById(R.id.tv_lineNumber);
        TextView tv_exceptionType = findViewById(R.id.tv_exceptionType);
        TextView tv_fullException = findViewById(R.id.tv_fullException);
        TextView tv_time = findViewById(R.id.tv_time);
        TextView tv_model = findViewById(R.id.tv_model);
        TextView tv_brand = findViewById(R.id.tv_brand);
        TextView tv_version = findViewById(R.id.tv_version);
//
        tv_packageName.setText(model.getClassName());
        textMessage.setText(model.getExceptionMsg());
        tv_className.setText(model.getFileName());
        tv_methodName.setText(model.getMethodName());
        tv_lineNumber.setText(String.valueOf(model.getLineNumber()));
        tv_exceptionType.setText(model.getExceptionType());
        tv_fullException.setText(model.getFullException());
        tv_time.setText(df.format(model.getTime()));

        tv_model.setText(model.getDevice().getModel());
        tv_brand.setText(model.getDevice().getBrand());
        tv_version.setText(model.getDevice().getVersion());
    }
}
