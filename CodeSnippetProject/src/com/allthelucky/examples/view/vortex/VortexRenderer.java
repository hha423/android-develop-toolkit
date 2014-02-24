package com.allthelucky.examples.view.vortex;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

/**
 * 刷新器
 */
public class VortexRenderer implements GLSurfaceView.Renderer {
    private float _red = 0.9f;
    private float _green = 0.2f;
    private float _blue = 0.2f;
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Do nothing special.
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int w, int h) {
        gl.glViewport(0, 0, w, h);
    }

    //重绘
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClearColor(_red, _green, _blue, 1.0f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    public void setColor(float r, float g, float b) {
        _red = r;
        _green = g;
        _blue = b;
    }
}