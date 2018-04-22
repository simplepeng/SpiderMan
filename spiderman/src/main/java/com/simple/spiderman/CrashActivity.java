package com.simple.spiderman;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * author : ChenPeng
 * date : 2018/4/20
 * description :
 */
public class CrashActivity extends Activity {

    public static final String EXCEPTION_MSG = "exception_msg";
    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        Throwable ex = (Throwable) getIntent().getSerializableExtra(EXCEPTION_MSG);
        ex.printStackTrace();

        StringBuilder msgBuilder = new StringBuilder();
        String exceptionMsg = null;

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();
        String exceptionType = ex.getClass().getName();
        while (ex != null) {
            exceptionMsg = ex.getMessage();
            msgBuilder.append(ex.getMessage());
            msgBuilder.append("\n");
            if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
                StackTraceElement element = ex.getStackTrace()[0];
                Item item = new Item();
                item.setLineNumber(element.getLineNumber());
                item.setClassName(element.getClassName());
                item.setFileName(element.getFileName());
                item.setMethodName(element.getMethodName());
                item.setExceptionType(exceptionType);
                itemList.add(item);
            }
            ex = ex.getCause();
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.header, null);
        TextView textMessage = headerView.findViewById(R.id.textMessage);
        textMessage.setText(exceptionMsg);

        ListView listView = findViewById(R.id.listView);
        listView.addHeaderView(headerView);
        listView.setAdapter(new Adpater());

        View footerView = inflater.inflate(R.layout.footer, null);
        TextView tvFooter = footerView.findViewById(R.id.tv_footer);
        tvFooter.setText(sw.toString());
        listView.addFooterView(footerView);
    }

    public class Adpater extends BaseAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item item = itemList.get(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(CrashActivity.this)
                        .inflate(R.layout.item_info, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mTvClassName.setText(item.getFileName());
            holder.mTvMethodName.setText(item.getMethodName());
            holder.mTvLineNumber.setText(String.valueOf(item.getLineNumber()));
            holder.mExceptionType.setText(item.getExceptionType());
            return convertView;
        }

        public final class ViewHolder {

            public TextView mTvClassName;
            public TextView mTvMethodName;
            public TextView mTvLineNumber;
            public TextView mExceptionType;

            public ViewHolder(View itemView) {
                mTvClassName = itemView.findViewById(R.id.tv_className);
                mTvMethodName = itemView.findViewById(R.id.tv_methodName);
                mTvLineNumber = itemView.findViewById(R.id.tv_lineNumber);
                mExceptionType = itemView.findViewById(R.id.tv_exceptionType);
            }
        }
    }
}
