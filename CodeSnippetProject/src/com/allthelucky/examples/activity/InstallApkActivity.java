package com.allthelucky.examples.activity;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

/**
 * Android安装/卸载Apk
 */
public class InstallApkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        install();
    }

    protected void install() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File("/sdcard/1.apk")), "app lication/vnd.android.package-archive");
       
        //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q=pname:your.app.id"));
        startActivity(intent);
    }

    protected void uninstall() {
        Uri packageURI = Uri.parse("package: abc.com.android");
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent); // 返回则没卸载
    }
}
