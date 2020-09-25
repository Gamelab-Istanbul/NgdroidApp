package com.ngdroidapp;

import android.graphics.Canvas;

import istanbul.gamelab.ngdroid.base.BaseActivity;
import istanbul.gamelab.ngdroid.core.AppManager;
import istanbul.gamelab.ngdroid.base.BaseApp;
import istanbul.gamelab.ngdroid.util.Log;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */

public class NgApp extends BaseApp {

    public NgApp(BaseActivity nitraBaseActivity, AppManager appManager) {
        super(nitraBaseActivity, appManager);
    }

    /**
     * Resolution is the number of pixels rendered on the screen. All of the pixels are entered
     * according to the unit resolution chosen. The resolutions that are available are uhd, qhd,
     * fullhd, hd, qfhd, wvga, and hvga. They are respectively numbered from 0 to 6 and named as
     * RESOLUTION_, as can be seen in AppManager.java. The unit resolution can be set using the
     * method setUnitResolution() as seen below. getUnitResolution() is used to retrieve the
     * unit resolution that is being used.
     *
     * In order to scale the values entered from the unit resolution to another chosen resolution
     * defragmentation can be used. The automatic defragmentation method is written as
     * SCREENSCALING_AUTO. This mode scales both the images and the x and y coordinates of the
     * places touched. To scale the images a new matrix is created, and is scaled according to:
     * image width scale factor = (width in resolution of the emulator / width in unit resolution);
     * image height scale factor = (height in resolution of the emulator / height in unit
     * resolution). After the image is drawn, the matrix is popped. This code can be found in
     * BaseCanvas.java in the methods startDraw() and endDraw(). To scale the x and y values of the
     * places touched the two equations are used: new x coordinate = old x * width of unit
     * resolution) / width of the resolution used; new y coordinate = (old y * height of unit
     * resolution) / height of the resolution used. This code can be found in BaseCanvas.java in the
     * methods scaleTouchX() and scaleTouchY().
     *
     * There are two other defragmentation options. SCREENSCALING_MIPMAP automatically scales the x
     * and y coordinates of the specific points that are touched, but leaves it up to the user to
     * make different dimensions of the images that are going to be used. The scaling of the x and y
     * coordinates are the same as the ones done for SCREENSCALING_AUTO. The images should be
     * stored in the assets/mipmaps folder, which has in it a folder opened for each
     * one of the unit resolutions that are provided, as mentioned above. There is
     * SCREENSCALING_NONE, which does not do any automatic defragmentation. Defragmentation is
     * totally left to the user to do so. If screen scaling type is SCREENSCALING_AUTO or
     * SCREENSCALING_NONE, assets/images folder should be used to store the images.
     *
     * The frame rate is frames drawn per second. This can be changed using the method
     * setFrameRate(), and entering the desired frame rate between the parenthesis. The frame rate
     * can be retrieved using the getFrameRate() method.
     */
    public void setup() {
        appManager.setUnitResolution(AppManager.RESOLUTION_FULLHD);
        appManager.setScreenScaling(AppManager.SCREENSCALING_AUTO);
        appManager.setFrameRate(20);

        MenuCanvas mc = new MenuCanvas(this);
        canvasManager.setCurrentCanvas(mc);
    }


    public void update() {

    }

    public void draw(Canvas canvas) {

    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return true;
    }

    public void touchDown(int x, int y, int id) {

    }

    public void touchMove(int x, int y, int id) {

    }

    public void touchUp(int x, int y, int id) {

    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

    }

    public void pause() {
        Log.i("NGAPP", "pause");
    }

    public void resume() {
        Log.i("NGAPP", "resume");
    }

    public void reloadTextures() {
        Log.i("NGAPP", "reloadTextures");
    }
}
