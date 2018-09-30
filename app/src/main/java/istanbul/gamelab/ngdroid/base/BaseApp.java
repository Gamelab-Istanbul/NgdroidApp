package istanbul.gamelab.ngdroid.base;

import android.graphics.Canvas;

import istanbul.gamelab.ngdroid.core.AppManager;
import istanbul.gamelab.ngdroid.core.CanvasManager;
import istanbul.gamelab.ngdroid.core.SoundManager;
import istanbul.gamelab.ngdroid.gui.GUI;
import istanbul.gamelab.ngdroid.util.Log;

/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */

public abstract class BaseApp {
    public BaseActivity activity;
    public AppManager appManager;
    public CanvasManager canvasManager;
    public GUI gui;
    public SoundManager soundManager;

    protected final String TAG = this.getClass().getSimpleName();


    public BaseApp(BaseActivity nitraBaseActivity, AppManager appManager) {
        this.activity = nitraBaseActivity;
        this.appManager = appManager;
        canvasManager = new CanvasManager();
        gui = new GUI();
        gui.initialize(this);
        soundManager = new SoundManager(this);
    }

    public abstract void setup();
    public abstract void update();
    public abstract void draw(Canvas canvas);

    public abstract void keyPressed(int key);
    public abstract void keyReleased(int key);
    public abstract boolean backPressed();

    public abstract void touchDown(int x, int y, int id);
    public abstract void touchMove(int x, int y, int id);
    public abstract void touchUp(int x, int y, int id);

    public abstract void surfaceChanged(int width, int height);
    public abstract void surfaceCreated();
    public abstract void surfaceDestroyed();
    public abstract void pause();
    public abstract void resume();
    public abstract void reloadTextures();

    public int getWidth() {
        return appManager.getScreenWidth();
    }

    public int getHeight() {
        return appManager.getScreenHeight();
    }

    public int getWidthHalf() {
        return appManager.getScreenWidthHalf();
    }

    public int getHeightHalf() {
        return appManager.getScreenHeightHalf();
    }

    protected void LOGI(String msg) {
        Log.i(TAG, msg);
    }

    protected int getNum(int number) {
        return appManager.getNum(number);
    }
}
