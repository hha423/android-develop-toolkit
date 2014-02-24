package com.allthelucky.examples.view.vortex;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class VortexView extends GLSurfaceView {
    private VortexRenderer renderer;

    public VortexView(Context context) {
        super(context);
        renderer = new VortexRenderer();
        setRenderer(renderer);
    }

    public boolean onTouchEvent(final MotionEvent event) {
        queueEvent(new Runnable() {
            public void run() {//刷新界面
                renderer.setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
            }
        });
        return true;
    }
}
