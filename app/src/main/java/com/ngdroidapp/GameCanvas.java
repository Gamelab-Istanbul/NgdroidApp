package com.ngdroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Log;
import istanbul.gamelab.ngdroid.util.Utils;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class GameCanvas extends BaseCanvas {

    private Bitmap spriteSheet, tileset;
    private Rect karesrc, karedst, tilesrc , tiledst;
    private int kareno, karerow;

    public GameCanvas(NgApp ngApp) {

        super(ngApp);
    }

    public void setup() {

        //Log.i(TAG, "setup");

        // initializing our objects:
        spriteSheet = Utils.loadImage(root,"cowboy.png"); // loading the image to the app as a Bitmap
        karesrc = new Rect();
        karedst = new Rect();

        kareno = 1;
        karerow = 0;

        tileset = Utils.loadImage(root,"tilea2.png");
        tilesrc = new Rect();
        tiledst = new Rect();
    }



    public void update() {

        //Log.i(TAG, "update");

        tilesrc.set(0, 0, 64, 64);

        karesrc.set(kareno * 128,karerow * 128,(kareno + 1) * 128, (karerow + 1) *128);   // take the first carecter from the image .. left and top are zeros ..
        // right and bottom are the dimensions of the first carechter which is 128*128
        kareno ++;
        if (kareno > 8) {
            kareno = 1;
            karerow++;
        }
        if (karerow > 9) karerow = 0;

        karedst.set(0,0,128,128);   // define where to draw it
        // it's left border, top border, right , bottom
        // we can use this to make it bigger or smaller
    }

    public void draw(Canvas canvas) {
        //canvas.drawBitmap(spriteSheet,0,0,null);    // test drawing the loaded Bitmap image to the screen, zeros are float left and top

        for (int i = 0; i < getWidth(); i+= 64)
        {
            for (int j = 0; j < getHeight(); j+= 64)
            {
                tiledst.set(i, j, i+64, j+64);
                canvas.drawBitmap(tileset,tilesrc,tiledst,null);    // test drawing the loaded Bitmap image to the screen, zeros are float left and top
            }

        }


        canvas.drawBitmap(spriteSheet,karesrc,karedst,null);    // modified to draw the first carechter which is defined by the karesrc and karedst
        //Log.i(TAG, "draw");
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return true;
    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

    }

    public void touchDown(int x, int y) {
    }

    public void touchMove(int x, int y) {
    }

    public void touchUp(int x, int y) {
    }


    public void pause() {

    }


    public void resume() {

    }


    public void reloadTextures() {

    }


    public void showNotify() {
    }

    public void hideNotify() {
    }

}
