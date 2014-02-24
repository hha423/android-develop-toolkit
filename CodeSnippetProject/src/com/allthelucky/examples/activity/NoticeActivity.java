package com.allthelucky.examples.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.allthelucky.examples.R;

/**
 * @author pxw
 */
public class NoticeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        String helloStr = intent.getStringExtra("HI");
        int id = intent.getIntExtra("ID", 0);

        Toast.makeText(getApplicationContext(), helloStr, 1).show();
        NotificationManager noticedManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noticedManager.cancel(id);
    }
    
    public static void sendNotification(Context context) {
        NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent appIntent = new Intent(context, NoticeActivity.class); // 启动Intent
        appIntent.setAction(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);// 绑定要启动的Intent

        Notification noticed = new Notification(R.drawable.ic_launcher, "慧优惠提醒", System.currentTimeMillis());
        noticed.defaults = Notification.DEFAULT_VIBRATE;
        noticed.setLatestEventInfo(context, "号外", "慧优惠有新消息啦,快来瞧瞧吧!", contentIntent);
        noticed.flags = Notification.FLAG_AUTO_CANCEL;

        mgr.notify(0, noticed);
    }
}
