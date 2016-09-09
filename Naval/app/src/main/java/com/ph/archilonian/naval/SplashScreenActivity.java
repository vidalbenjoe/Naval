package com.ph.archilonian.naval;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.ph.archilonian.naval.DrawerMenu.MenuActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static long SLEEP_TIME = 2;
    private static String TAG = SplashScreenActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen_layout);

        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME * 1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            Intent mainDrawerIntent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(mainDrawerIntent);
            SplashScreenActivity.this.finish();
        }
    }
}
