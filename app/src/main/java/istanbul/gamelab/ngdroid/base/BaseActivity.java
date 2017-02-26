package istanbul.gamelab.ngdroid.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import istanbul.gamelab.ngdroid.core.AppManager;
import istanbul.gamelab.ngdroid.util.Log;
import com.mycompany.myngdroidapp.R;

public class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private RelativeLayout gamesurface;
    protected AppManager appmanager;
    private boolean isdevelopmentmode, isfreeversion, isgprelease;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        makeFullScreen();
        appmanager = new AppManager(this);

        setContentView(R.layout.activity_game);
        gamesurface = (RelativeLayout)findViewById(R.id.gameSurface);
        gamesurface.addView(appmanager);

        isdevelopmentmode = false;
        isfreeversion = true;
        isgprelease = true;
    }

    public void makeFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //19 or above api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        appmanager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeFullScreen();
        appmanager.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        makeFullScreen();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        makeFullScreen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean changed) {
        super.onWindowFocusChanged(changed);
        makeFullScreen();
    }

    protected void setDevelopmentMode(boolean isTestMode) {
        isdevelopmentmode = isTestMode;
        if (isdevelopmentmode) Log.setLogLevel(Log.LOGLEVEL_VERBOSE);
        else Log.setLogLevel(Log.LOGLEVEL_SILENT);
    }

    public boolean isDevelopmentMode() {
        return isdevelopmentmode;
    }

    protected void setFreeVersion(boolean isFullVersion) {
        isfreeversion = isFullVersion;
    }

    public boolean isFreeVersion() {
        return isfreeversion;
    }

    protected void setGPRelease(boolean isGPRelease) {
        isgprelease = isGPRelease;
    }

    public boolean isGPRelease() {
        return isgprelease;
    }

    public void addView(View view) {
        gamesurface.addView(view);
    }

}
