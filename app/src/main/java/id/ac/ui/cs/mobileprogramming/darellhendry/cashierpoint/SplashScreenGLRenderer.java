package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;

public class SplashScreenGLRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES20.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);
        mTriangle = new Triangle();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        mTriangle.draw();
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)

        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)

        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it

        GLES20.glShaderSource(shader, shaderCode);

        GLES20.glCompileShader(shader);

        return shader;

    }
}
