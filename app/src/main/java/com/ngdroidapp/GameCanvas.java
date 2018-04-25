package com.ngdroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Utils;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class GameCanvas extends BaseCanvas {

    private Bitmap tileset, plane;
    private Rect tiledestination, tilesource, planedestination, planesource;
    private int tilesrcw, tilesrch;
    private int tiledstw, tiledsth;
    private int planesrcx, planesrcy, planesrcw, planesrch;
    private int planedstx, planedsty, planedstw, planedsth;

    private Bitmap logo;
    private int logox, logoy, logow, logoh;

    public GameCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {

        tileset = Utils.loadImage(root,"forest1.png");
        tiledestination = new Rect();
        tilesource = new Rect();

        tilesrcw = tileset.getWidth();
        tilesrch = tileset.getHeight();
        tiledstw = tileset.getWidth();
        tiledsth = tileset.getHeight();

        plane = Utils.loadImage(root,"plane.png");
        planedestination = new Rect();
        planesource = new Rect();

        planesrcw = plane.getWidth();
        planesrch = plane.getHeight();
        planedstw = plane.getWidth() / 3;
        planedsth = plane.getHeight() / 3;

        planedstx = (getWidth() - planedstw) / 2;
        planedsty = ((getHeight() - planedsth) + 1500) / 2;

    }

    public void update() {
//        Log.i(TAG, "update");
        program_tehlikeUreticisi_tehlikeUret();
        program_tehlikeUreticisi_tehlikeleriHareketEttir();
        otopilot_tehlikeDenetleyicisi_yeniTehlikeKontroluYap();
        otopilot_tehlikeDenetleyicisi_yeniTehlikeleriBildir();
        otopilot_rotaYoneticisi_manevrayiBelirle();
        otopilot_rotaYoneticisi_rotayiGuncelle();
        otopilot_ucagiIlerlet();
        program_carpismaTestleriniYap();
    }

    public void draw(Canvas canvas) {
/*      Log.i(TAG, "draw");
        logox = (getWidth() - logow) / 2;
        logoy = (getHeight() - logoh) / 2;
        canvas.drawBitmap(logo, logox, logoy, null);
*/
        sahneyiCiz(canvas);
        guiCiz();
    }
    private void program_tehlikeUreticisi_tehlikeUret(){
    }

    private void program_tehlikeUreticisi_tehlikeleriHareketEttir(){
    }

    private void  otopilot_tehlikeDenetleyicisi_yeniTehlikeKontroluYap(){
    }

    private void otopilot_tehlikeDenetleyicisi_yeniTehlikeleriBildir(){
    }

    private void otopilot_rotaYoneticisi_manevrayiBelirle(){
    }

    private void otopilot_rotaYoneticisi_rotayiGuncelle(){
    }

    private void otopilot_ucagiIlerlet() {

        planedsty -= 10;

      }

    private void program_carpismaTestleriniYap(){

    }
    private void sahneyiCiz(Canvas canvas) {

      for (int i = 0; i < getHeight(); i += tiledsth) {
          for (int j = 0; j < getWidth(); j += tiledstw) {

              tiledestination.set(j, i, j + tiledstw, i + tiledsth);
              tilesource.set(0, 0, tilesrcw, tilesrch);
              canvas.drawBitmap(tileset, tilesource, tiledestination, null);
          }

      }
            planesource.set(0, 0, planesrcw, planesrch);
            planedestination.set(planedstx, planedsty,planedstx + planedstw, planedsty + planedsth);
            canvas.drawBitmap(plane, planesource, planedestination, null);

    }

    private void guiCiz(){

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

    public void touchDown(int x, int y, int id) {
    }

    public void touchMove(int x, int y, int id) {
    }

    public void touchUp(int x, int y, int id) {
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
