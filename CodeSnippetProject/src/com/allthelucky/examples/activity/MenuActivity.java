package com.allthelucky.examples.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends Activity {
    private final static int MENU_EXIT = 1;
    private final static int MENU_ABOUT = 2;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { /* create options menu */
        menu.add(0, MENU_EXIT, 1, "exit");
        menu.add(0, MENU_ABOUT, 2, "about");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
            finish();
            break;
        case 2:
            showDialog();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialog() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("about").setMessage("nothig...")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();
        dialog.show();
    }
}
