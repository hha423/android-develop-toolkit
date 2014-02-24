package com.allthelucky.examples.view.viewgroup;

import android.app.Activity;
import android.os.Bundle;

public class ViewGroupActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        WorkSpace work = new WorkSpace(this);
        setContentView(work);
    }
}
