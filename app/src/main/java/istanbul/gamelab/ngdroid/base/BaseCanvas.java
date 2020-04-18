package istanbul.gamelab.ngdroid.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.ngdroidapp.NgApp;

import istanbul.gamelab.ngdroid.util.Log;
import istanbul.gamelab.ngdroid.util.Utils;

/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public abstract class BaseCanvas {
    protected NgApp root;
    public Canvas canvas;
    private Paint font;

    protected final String TAG = this.getClass().getSimpleName();

    public BaseCanvas(NgApp ngApp) {
        root = ngApp;
        font = new Paint();
        font.setTextSize(48);
        font.setColor(Color.WHITE);
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
     * Writes debug log on the console.
     *
     * @param msg Message to write
     */
    protected void LOGD(String msg) {
        Log.d(TAG, msg);
    }

    /**
     * Takes a screen coordinate in the unit resolution and calculates a screen coordinate
     * proportional to current resolution
     *
     * @param number int coordinate in unit resolution
     * @return int coordinate in current resolution
     */
    protected int scaleNum(int number) {
        return root.appManager.scaleNum(number);
    }


    protected Bitmap loadImage(String imagePath) {
        Bitmap newimage;
        newimage = Utils.loadImage(root, imagePath);
        return newimage;
    }

    protected void drawBitmap(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    protected void drawBitmap(Bitmap bitmap, Rect src, Rect dst) {
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    protected void drawText(String text, int x, int y) {
        canvas.drawText(text, x, y, font);
    }

    /**
     * This method sets the drawing color.
     *
     * @param color int value of the color.
     */
    protected void setColor(int color) {
        font.setColor(color);
    }

    /**
     * This method gets the drawing color.
     *
     * @return int value of the color.
     */
    protected int getColor() {
        return font.getColor();
    }

    /**
     * This method sets the font size.
     *
     * @param size font size
     */
    protected void setTextSize(int size) {
        font.setTextSize(size);
    }

    /**
     * This method returns the font size.
     *
     * @return font size
     */
    protected float getTextSize() {
        return font.getTextSize();
    }
}
