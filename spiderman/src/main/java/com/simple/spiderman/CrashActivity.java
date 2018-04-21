package com.simple.spiderman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
