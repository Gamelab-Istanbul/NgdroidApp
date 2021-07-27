package istanbul.gamelab.ngdroid.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.ngdroidapp.NgApp;

import java.util.Vector;

import istanbul.gamelab.ngdroid.core.AppManager;
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
    private int posx, posy;
    private Vector<Integer> posvec;
    private int tempx, tempy, tempx2, tempy2;

    public static final int ALIGN_LEFT = 0, ALIGN_CENTER = 1, ALIGN_RIGHT = 2;
    public static final int ALIGN_TOP = 0, ALIGN_BOTTOM = 2;

    // The Rect to be used in the calculation of string width and height.
    private Rect textbound;


    protected final String TAG = this.getClass().getSimpleName();

    public BaseCanvas(NgApp ngApp) {
        root = ngApp;
        font = new Paint();
        font.setTextSize(48);
        font.setColor(Color.WHITE);
        posx = 0;
        posy = 0;
        posvec = new Vector();
        posvec.add(0);
        posvec.add(0);

        // Initialization of the rectangle that we will use as helper in the calculations
        // of the dimensions of the given text strings.
        textbound = new Rect();
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
        if (root.appManager.getScreenScaling() >= AppManager.SCREENSCALING_AUTO) return getUnitWidth();
        return getCurrentWidth();
    }
    public int getHeight() {
        if (root.appManager.getScreenScaling() >= AppManager.SCREENSCALING_AUTO) return getUnitHeight();
        return getCurrentHeight();
    }

    public int getWidthHalf() {
        return root.appManager.getScreenWidthHalf();
    }
    public int getHeightHalf() {
        return root.appManager.getScreenHeightHalf();
    }

    public int getUnitWidth() { return root.appManager.getWidthUnit();}
    public int getUnitHeight() { return root.appManager.getHeightUnit();}
    public int getTempWidth() {
        return root.appManager.getWidthTemp();
    }
    public int getTempHeight() {
        return root.appManager.getHeightTemp();
    }
    public int getCurrentWidth() { return root.appManager.getScreenWidth();}
    public int getCurrentHeight() { return root.appManager.getScreenHeight();}
    public int getOrientation() {
        return root.appManager.getOrientation();
    }

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

    /**
     * Loads an immutable image from the /assets/images folder.
     *
     * @param imagePath Path of the image to load. The image should be stored under /assets/images folder
     * @return The immutable image
     */
    protected Bitmap loadImage(String imagePath) {
        return Utils.loadImage(root, imagePath);
    }

    /**
     * Loads a true type font from the /assets/fonts folder.
     *
     * @param fontPath Path of the true type font to load. The font should be stored under /assets/fonts folder
     * @return The loaded true type font
     */
    protected Typeface loadFont(String fontPath) {
        return Utils.loadFont(root, fontPath);
    }

    /**
     * Scales the images drawn on the device from fullhd to the resolution being used by creating a
     * new matrix.
     */
    public void startDraw() {
        pushMatrix();
        canvas.scale((float)getCurrentWidth() / getTempWidth(), (float)getCurrentHeight() / getTempHeight());
//        canvas.scale((float)getCurrentWidth() / getUnitWidth(), (float)getCurrentHeight() / getUnitHeight());
    }

    /**
     * Closes the matrix created to scale the images according to the resolution of the screen.
     */
    public void endDraw() {
        popMatrix();
    }

    /**
     * Scales the x values of the place that is touched from fullhd to the resolution of the screen.
     *
     * @param x The x-coordinate of the place that is touched that is sent to be scaled.
     * @return Scaled x-coordinate
     */
    public int scaleTouchX(int x) {
        return (int)((x * getUnitWidth()) / getCurrentWidth());
    }

    /**
     * Scales the y values of the place that is touched from fullhd to the resolution of the screen.
     *
     * @param y The y-coordinate of the place that is touched that is sent to be scaled.
     * @return Scaled y-coordinate
     */
    public int scaleTouchY(int y) {
        return (int)((y * getUnitHeight()) / getCurrentHeight());
    }


    /**
     * Draws the given bitmap on the screen. The left-upper corner of the bitmap will be drawn just
     * on {x, y} point of the screen
     *
     * @param bitmap The image to be drawn
     * @param x Left coordinate of the image
     * @param y Top coordinate of the image
     */
    protected void drawBitmap(Bitmap bitmap, int x, int y) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                x *= root.appManager.getTempCoef();
            } else {
                y *= root.appManager.getTempCoef();
            }
        }
        canvas.drawBitmap(bitmap, x, y, null);
    }

    // Please use this method with caution. When the aligment is set to center, right or bottom, the
    // x coordinate may change on wide screens
    protected void drawBitmap(Bitmap bitmap, int x, int y, int alignHor, int alignVer) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                x *= root.appManager.getTempCoef();
            } else {
                y *= root.appManager.getTempCoef();
            }
        }
        canvas.drawBitmap(bitmap, x - ((alignHor * bitmap.getWidth()) / 2), y - ((alignVer * bitmap.getHeight()) / 2), null);
    }

    /**
     * Draws a subpart of an image onto the given part of the screen. The subimage and subscreen are
     * represented by rectangles.
     *
     * @param bitmap The image to be drawn onto screen
     * @param src The rectangle that determines the sub-part of the image
     * @param dst The rectangle that determines the sub-part of the screen to draw onto.
     */
    protected void drawBitmap(Bitmap bitmap, Rect src, Rect dst) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                tempx = (int)(dst.left * root.appManager.getTempCoef());
                tempx2 = tempx + dst.width();
                dst.set(tempx, dst.top, tempx2, dst.bottom);
            } else {
                tempy = (int)(dst.top * root.appManager.getTempCoef());
                tempy2 = tempy + dst.height();
                dst.set(dst.left, tempy, dst.bottom, tempy2);
            }
        }
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    // Please use this method with caution. When the aligment is set to center, right or bottom, the
    // left coordinate of the destination rectangle may change on wide screens
    protected void drawBitmap(Bitmap bitmap, Rect src, Rect dst, int alignHor, int alignVer) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                if (alignHor == ALIGN_RIGHT) {
                    tempx2 = (int)(dst.right * root.appManager.getTempCoef());
                    tempx = tempx2 - dst.width();
                } else if (alignHor == ALIGN_CENTER) {
                    tempx = (int)(((dst.left + dst.right) / 2) * root.appManager.getTempCoef()) - (dst.width() / 2);
                    tempx2 = tempx + dst.width();
                } else {
                    tempx = (int)(dst.left * root.appManager.getTempCoef());
                    tempx2 = tempx + dst.width();
                }
                dst.set(tempx, dst.top, tempx2, dst.bottom);
            } else {
                if (alignVer == ALIGN_BOTTOM) {
                    tempy2 = (int)(dst.bottom * root.appManager.getTempCoef());
                    tempy = tempy2 - dst.height();
                } else if (alignVer == ALIGN_CENTER) {
                    tempy = (int)(((dst.top + dst.bottom) / 2) * root.appManager.getTempCoef()) - (dst.height() / 2);
                    tempy2 = tempy + dst.height();
                } else {
                    tempy = (int)(dst.top * root.appManager.getTempCoef());
                    tempy2 = tempy + dst.height();
                }
                dst.set(dst.left, tempy, dst.bottom, tempy2);
            }
        }
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    protected void drawBitmap(Bitmap bitmap) {
        canvas.drawBitmap(bitmap, posvec.elementAt(posvec.size() - 2), posvec.elementAt(posvec.size() - 1), null);
    }

    /**
     * Draws a string onto the screen using the default font
     *
     * @param text The string to be drawn
     * @param x Left coordinate of the text
     * @param y Top coordinate of the text
     */
    protected void drawText(String text, int x, int y) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                x *= root.appManager.getTempCoef();
            } else {
                y *= root.appManager.getTempCoef();
            }
        }
        canvas.drawText(text, x, y, font);
    }

    protected void drawText(String text, int x, int y, int alignHor, int alignVer) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                x *= root.appManager.getTempCoef();
                if (alignHor > ALIGN_LEFT) x -= ((alignHor * getStringWidth(text)) / 2);
            } else {
                y *= root.appManager.getTempCoef();
                if (alignHor > ALIGN_TOP) y -= ((alignVer * getStringHeight(text)) / 2);
            }
        }
        canvas.drawText(text, x, y, font);
    }

    // This method is implemented to get rid of the cost of the string's width and height recalculations
    protected void drawText(String text, int x, int y, int textw, int texth, int alignHor, int alignVer) {
        if (root.appManager.getScreenScaling() == AppManager.SCREENSCALING_AUTOWIDE) {
            if (root.appManager.getOrientation() == AppManager.ORIENTATION_LANDSCAPE) {
                x *= root.appManager.getTempCoef();
                if (alignHor > ALIGN_LEFT) x -= ((alignHor * textw) / 2);
            } else {
                y *= root.appManager.getTempCoef();
                if (alignHor > ALIGN_TOP) y -= ((alignVer * texth) / 2);
            }
        }
        canvas.drawText(text, x, y, font);
    }

    /**
     * This method sets the drawing color. The color is an integer value enumerated in android.graphics.Color. A few examples are Color.WHITE, Color.BLACK, Color.BLUE, etc...
     *
     * @param color int value of the color.
     */
    protected void setColor(int color) {
        font.setColor(color);
    }

    protected void setColor(int r, int g, int b, int a) {
        font.setARGB(a, r, g, b);
    }

    protected void setColor(int r, int g, int b) {
        font.setARGB(255, r, g, b);
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

    /**
     * Returns the width of the string sent as parameter. The width is measured in pixels using the
     * face and the size of the last font set. Written by @iremerus
     *
     * @param text The string for which the width will be calculated.
     * @return The width of the string in pixel numbers.
     */
    protected int getStringWidth(String text) {
        // We propogate the textbound Rect we defined, with the position and dimension data of the
        // text, calculated using the current font.
        font.getTextBounds(text, 0, text.length(), textbound);

        // Returns the calculated and propogated width of the textbound Rect.
        return textbound.width();
    }

    /**
     *  Returns the height of the string sent as parameter. The height is measured in pixels using
     *  the face and the size of the last font set. Written by @iremerus
     *
     * @param text The string for which the height will be calculated.
     * @return The height of the string in pixel numbers.
     */
    protected int getStringHeight(String text) {
        // We propogate the textbound Rect we defined, with the position and dimension data of the
        // text, calculated using the current font.
        font.getTextBounds(text, 0, text.length(), textbound);

        // Returns the calculated and propogated height of the textbound Rect.
        return textbound.height();
    }

    protected void setTextBold(boolean isBold) {
        font.setFakeBoldText(isBold);
    }

    protected void setPosition(int x, int y) {
        posvec.set(posvec.size() - 2, x);
        posvec.set(posvec.size() - 1, y);
    }

    protected int getPosX() {
        return posvec.elementAt(posvec.size() - 2);
    }

    protected int getPosY() {
        return posvec.elementAt(posvec.size() - 1);
    }

    protected void drawText(String text) {
        canvas.drawText(text, posvec.elementAt(posvec.size() - 2), posvec.elementAt(posvec.size() - 1), font);
    }

    protected void translate(int dx, int dy) {
        posvec.set(posvec.size() - 2, posvec.elementAt(posvec.size() - 2) + dx);
        posvec.set(posvec.size() - 1, posvec.elementAt(posvec.size() - 1) + dy);
    }

    // rotate and scale functions should be tested in immediate mode
    protected void rotate(float degrees, float px, float py) {
        canvas.rotate(degrees, px, py);
    }

    protected void scale(float sx, float sy, float px, float py) {
        canvas.scale(sx, sy, px, py);
    }

    protected void scale(float sx, float sy) {
        canvas.scale(sx, sy);
    }

    protected void clearColor(int color) {
        canvas.drawColor(color);
    }

    protected void pushMatrix() {
        posvec.add(posvec.elementAt(posvec.size() - 2));
        posvec.add(posvec.elementAt(posvec.size() - 2));
        canvas.save();
    }

    protected void popMatrix() {
        posvec.remove(posvec.size() - 1);
        posvec.remove(posvec.size() - 1);
        canvas.restore();
    }

    /**
     * Returns the font being used.
     *
     * @return The font used.
     */
    protected Paint getFont()  {
        return font;
    }

    /**
     * Sets a new font on top of the old one. This font will be used in drawString operations.
     *
     * @param newFont New font to be used.
     */
    protected void setFont(Paint newFont) {
        font = newFont;
    }

    /**
     * Checks if two images are colliding or not.
     *
     * @param image1 The first Bitmap object
     * @param x1 x Left coordinate of the first image
     * @param y1 y Top coordinate of the first image
     * @param image2 The second Bitmap object
     * @param x2 x Left coordinate of the second image
     * @param y2 y Top coordinate of the second image
     * @return true if the two images are colliding, false if not.
     */
    protected boolean checkCollision(Bitmap image1, int x1, int y1, Bitmap image2, int x2, int y2) {
        return Utils.checkCollision(x1, y1, x1 + image1.getWidth(), y1 + image1.getHeight(),
                x2, y2, x2 + image2.getWidth(), y2 + image2.getHeight());
    }
}
