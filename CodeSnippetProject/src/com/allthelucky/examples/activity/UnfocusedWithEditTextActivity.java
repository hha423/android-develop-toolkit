package com.allthelucky.examples.activity;

import android.os.Bundle;

import com.allthelucky.examples.R;

/**
 * 让有EditText界面不聚集方法（无输入法界面弹出）
 * 
 * @author savant
 * 
 */
public class UnfocusedWithEditTextActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unfocusablepage_withedittext);
    }
}
