package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SplashScreenGLSurfaceView extends GLSurfaceView {

    private final SplashScreenGLRenderer renderer;

    public SplashScreenGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        renderer = new SplashScreenGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer);
    }
}
