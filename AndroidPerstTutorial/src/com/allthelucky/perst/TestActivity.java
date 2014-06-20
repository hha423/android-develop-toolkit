package com.allthelucky.perst;

import android.app.Activity;
import android.os.Bundle;

public class TestActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BinService test = BinService.getInstance(this);
        test.runTest();
        
    }
}
