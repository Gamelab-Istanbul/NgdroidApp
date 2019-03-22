package istanbul.gamelab.ngdroid.base;

import android.graphics.Canvas;

import com.ngdroidapp.NgApp;

import istanbul.gamelab.ngdroid.util.Log;

/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public abstract class BaseCanvas {
    protected NgApp root;

    protected final String TAG = this.getClass().getSimpleName();

    public BaseCanvas(NgApp ngApp) {
        root = ngApp;
    }

    public abstract void setup();
    public abstract void update();
    public abstract void draw(Canvas canvas);
    public void onGuiClick(int objectId) {}
    public void onDialogueHide(int objectId) {}
    public void onNotificationHide(int objectId) {}
    public void onNgResult(int request, int response, String data) {}
    public void onConnected() {}
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
    public abstract void showNotify();
    public abstract void hideNotify();

    public int getWidth() {
        return root.appManager.getScreenWidth();
    }
    public int getHeight() {
        return root.appManager.getScreenHeight();
    }

    public int getWidthHalf() {
        return root.appManager.getScreenWidthHalf();
    }
    public int getHeightHalf() {
        return root.appManager.getScreenHeightHalf();
    }

    public int getUnitWidth() { return root.appManager.getWidthUnit();}
    public int getUnitHeight() { return root.appManager.getHeightUnit();}


    /**
     * Writes information log on the console.
     *
     * @param msg Message to write
     */
    protected void LOGI(String msg) {
        Log.i(TAG, msg);
    }

    /**
     * Takes a screen coordinate in the unit resolution and calculates a screen coordinate
     * proportional to current resolution
     *
     * @param number int coordinate in unit resolution
     * @return int coordinate in current resolution
     */
    protected int scaleUnitNum(int number) {
        return root.appManager.scaleUnitNum(number);
    }
}
