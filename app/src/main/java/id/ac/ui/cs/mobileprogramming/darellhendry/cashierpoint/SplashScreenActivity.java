package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT=2000;
    private GLSurfaceView gLView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gLView = new SplashScreenGLSurfaceView(this);
        setContentView(gLView);

//        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreenActivity.this,
                        MainActivity.class);

                startActivity(i);

                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}