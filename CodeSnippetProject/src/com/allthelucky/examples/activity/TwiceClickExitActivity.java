package com.allthelucky.examples.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * 再按一次退出程序
 * 
 * @author savant
 * 
 */
public class TwiceClickExitActivity extends Activity {
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long cur = System.currentTimeMillis();
            if (cur - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = cur;
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
