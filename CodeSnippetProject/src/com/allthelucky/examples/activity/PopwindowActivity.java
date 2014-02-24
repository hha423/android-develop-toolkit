package com.allthelucky.examples.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.allthelucky.examples.R;

/**
 * PopWindow Test
 * 
 * @author pxw
 * 
 */
public class PopwindowActivity extends Activity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    private void showPopWindow(final View button, List<String> list) {
        final View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_listview, null);
        contentView.setBackgroundColor(Color.WHITE);

        final PopupWindow popupWindow = new PopupWindow(contentView, button.getWidth(), LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) contentView.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        popupWindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);// 点击周边可取消
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                popupWindow.showAsDropDown(button, (-(button.getWidth() / 2) + button.getWidth() / 2), 0);
            }
        };
        handler.sendEmptyMessage(0);
    }


    @Override
    public void onClick(View v) {
        List<String> list = new ArrayList<String>();
        if (R.id.button1 == v.getId()) {
            list.add("hello1");
            list.add("hello2");
            list.add("hello3");
        } else if (R.id.button2 == v.getId()) {
            list.add("hello2");
            list.add("hello3");
            list.add("hello4");
        } else {
            list.add("hello3");
            list.add("hello4");
            list.add("hello5");
        }
        showPopWindow(v, list);
    }

}
