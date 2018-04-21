package com.simple.spiderman;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        while (ex != null) {
            msgBuilder.append(ex.getMessage());
//            msgBuilder.append("\n");
            Item item;
            for (StackTraceElement element : ex.getStackTrace()) {
                item = new Item();
                item.setLineNumber(element.getLineNumber());
                item.setClassName(element.getClassName());
                item.setFileName(element.getFileName());
                item.setMethodName(element.getMethodName());
                itemList.add(item);
            }
            ex = ex.getCause();
        }

        View headerView = LayoutInflater.from(this)
                .inflate(R.layout.header, null);
        TextView textMessage = headerView.findViewById(R.id.textMessage);
        textMessage.setText(msgBuilder.toString());

        ListView listView = findViewById(R.id.listView);
        listView.addHeaderView(headerView);
        listView.setAdapter(new Adpater());
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
            return convertView;
        }

        public final class ViewHolder {

            public TextView mTvClassName;
            public TextView mTvMethodName;
            public TextView mTvLineNumber;

            public ViewHolder(View itemView) {
                mTvClassName = itemView.findViewById(R.id.tv_className);
                mTvMethodName = itemView.findViewById(R.id.tv_methodName);
                mTvLineNumber = itemView.findViewById(R.id.tv_lineNumber);
            }
        }
    }
}
