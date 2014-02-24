package com.allthelucky.examples.view.vortex;

import android.app.Activity;
import android.os.Bundle;

public class Vortex extends Activity {
    private VortexView  vortexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vortexView = new VortexView(this);
        setContentView(vortexView);
    }
}
