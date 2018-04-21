package com.simple.spiderman;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * author : ChenPeng
 * date : 2018/4/20
 * description :
 */
public class CrashActivity extends Activity{

    public static final String EXCEPTION_MSG = "exception_msg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        Throwable ex = (Throwable) getIntent().getSerializableExtra(EXCEPTION_MSG);
        ex.printStackTrace();

        StringBuilder sb = new StringBuilder();
        while (ex != null) {
            sb.append(ex.getMessage());
            sb.append("\n");
            for (StackTraceElement element : ex.getStackTrace()) {
//                Log.e("SpiderMan","elemnt == "+element.getLineNumber());
                sb.append(element.getClassName()+"::"+element.getClassName()+"("+element.getLineNumber()+")");
                sb.append("\n");
            }
            ex = ex.getCause();
        }
        TextView textView = findViewById(R.id.textView);
//        textView.setText(writer.toString());

        TextView textView2 = findViewById(R.id.textView2);
        textView2.setText(sb.toString());
    }
}
