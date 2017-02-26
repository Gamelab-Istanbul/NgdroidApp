package com.ngdroidapp;

import android.graphics.Canvas;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Log;

/**
 * Created by noyan on 27.06.2016.
 * Nitra Games Ltd.
 */

public class MenuCanvas extends BaseCanvas {


    public MenuCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
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
        return false;
    }

    public void touchDown(int x, int y) {
    }

    public void touchMove(int x, int y) {
    }

    public void touchUp(int x, int y) {
    }

    public void surfaceChanged(int width, int height) {
        Log.i("MC", "surfaceChanged");
    }

    public void surfaceCreated() {
        Log.i("MC", "surfaceCreated");
    }

    public void surfaceDestroyed() {
        Log.i("MC", "surfaceDestroyed");
    }

    public void pause() {
        Log.i("MC", "pause");
    }

    public void resume() {
        Log.i("MC", "resume");
    }

    public void reloadTextures() {
        Log.i("MC", "reloadTextures");
    }

    public void showNotify() {
        Log.i("MC", "showNotify");
    }

    public void hideNotify() {
        Log.i("MC", "hideNotify");
    }

}
