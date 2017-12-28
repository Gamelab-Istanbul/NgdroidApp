package istanbul.gamelab.ngdroid.gui;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import istanbul.gamelab.ngdroid.base.BaseApp;
import istanbul.gamelab.ngdroid.util.Utils;
import com.ngdroidapp.NgApp;

import java.util.Vector;

/**
 * Created by noyan on 11.07.2016.
 * Nitra Games Ltd.
 */

public class GUI {
    public static int OBJTYPE_OBJECT = 0, OBJTYPE_GROUP = 1, OBJTYPE_DIALOGUE = 2, OBJTYPE_BUTTON = 3, OBJTYPE_NOTIFICATION = 4;

    public static int BASEIMAGE_NONE = -1, BASEIMAGE_0 = 0, BASEIMAGE_1 = 1, BASEIMAGE_2 = 2, BASEIMAGE_3 = 3, BASEIMAGE_4 = 4, BASEIMAGE_5 = 5, BASEIMAGE_6 = 6, BASEIMAGE_7 = 7, BASEIMAGE_8 = 8, BASEIMAGE_9 = 9, BASEIMAGE_10 = 10, BASEIMAGE_11 = 11, BASEIMAGE_12 = 12, BASEIMAGE_13 = 13, BASEIMAGE_14 = 14;
    public static int FONTFILENO_NONE = -1, FONTFILENO_0 = 0, FONTFILENO_1 = 1, FONTFILENO_2 = 2, FONTFILENO_3 = 3, FONTFILENO_4 = 4, FONTFILENO_5 = 5, FONTFILENO_6 = 6, FONTFILENO_7 = 7, FONTFILENO_8 = 8, FONTFILENO_9 = 9;
    public static int FONT_NONE = -1, FONT_0 = 0, FONT_1 = 1, FONT_2 = 2, FONT_3 = 3, FONT_4 = 4, FONT_5 = 5, FONT_6 = 6, FONT_7 = 7, FONT_8 = 8, FONT_9 = 9;
    private Bitmap baseimage[];
    private Typeface basefont[];
    private GUI_Font guifont[];
    private int maxbaseimagenum, maxbasefontnum, maxfontnum;

    private Vector<GUI_Object> objects;
    private int lastobjectid;

    private BaseApp baseapp;
    private boolean isinitialized, isdialogueshown, isnotificationshown;


    public GUI() {
        maxbaseimagenum = 15;
        baseimage = new Bitmap[maxbaseimagenum];
        maxbasefontnum = 10;
        basefont = new Typeface[maxbasefontnum];
        maxfontnum = 10;
        guifont = new GUI_Font[maxfontnum];

        objects = new Vector<>();
        lastobjectid = 0;
        isinitialized = false;
        isdialogueshown = false;
        isnotificationshown = false;
    }

    public void initialize(BaseApp nitraBaseApp) {
        baseapp = nitraBaseApp;
        isinitialized = true;

        guifont[FONT_0] = new GUI_Font(this);
        guifont[FONT_0].setFontSize((48 * getScaleMult()) / getScaleDiv());
        guifont[FONT_0].setFontAlignmentHorizontal(GUI_Font.ALIGNHOR_LEFT);
        guifont[FONT_0].setFontStyle(GUI_Font.FONTSTYLE_SHADOWED);
        guifont[FONT_0].setFontColorShadow(GUI_Font.FONTCOLOR_BROWN);

        guifont[FONT_1] = new GUI_Font(this, guifont[FONT_0]);
        guifont[FONT_1].setFontAlignmentHorizontal(GUI_Font.ALIGNHOR_RIGHT);
        guifont[FONT_1].setFontColor(GUI_Font.FONTCOLOR_LTGRAY);
        guifont[FONT_1].setFontColorShadow(GUI_Font.FONTCOLOR_DKGRAY);

        Typeface customfont = Typeface.createFromAsset(baseapp.activity.getAssets(), "fonts/komika-text_bold.ttf");
        guifont[FONT_2] = new GUI_Font(this, customfont, (80 * getScaleMult()) / getScaleDiv(), GUI_Font.FONTSTYLE_SHADOWED);
        guifont[FONT_2].setFontColor(GUI_Font.FONTCOLOR_WHITE);
        guifont[FONT_2].setFontColorShadow(android.graphics.Color.argb(160, 96, 96, 96));
        guifont[FONT_2].setFontColorOutline(GUI_Font.FONTCOLOR_BLACK);
        guifont[FONT_2].setFontAlignmentHorizontal(GUI_Font.ALIGNHOR_CENTER);
        guifont[FONT_2].setFontAlignmentVertical(GUI_Font.ALIGNVER_CENTER);

        guifont[FONT_3] = new GUI_Font(this, guifont[FONT_2]);
        guifont[FONT_3].setFontColor(GUI_Font.FONTCOLOR_LTGRAY);
//        guifont[FONT_3].setFontColorShadow(GUI_Font.FONTCOLOR_DKGRAY);

        guifont[FONT_4] = new GUI_Font(this, guifont[FONT_2]);
        guifont[FONT_4].setFontSize((60 * getScaleMult()) / getScaleDiv());

        guifont[FONT_5] = new GUI_Font(this, guifont[FONT_0]);
        guifont[FONT_5].setFontSize((36 * getScaleMult()) / getScaleDiv());
        guifont[FONT_5].setFontColor(GUI_Font.FONTCOLOR_LTGRAY);
        guifont[FONT_5].setFontColorShadow(GUI_Font.FONTCOLOR_DKGRAY);

        guifont[FONT_6] = new GUI_Font(this, guifont[FONT_5]);
        guifont[FONT_6].setFontAlignmentHorizontal(GUI_Font.ALIGNHOR_RIGHT);
        guifont[FONT_6].setFontColor(GUI_Font.FONTCOLOR_LTGRAY);
        guifont[FONT_6].setFontColorShadow(GUI_Font.FONTCOLOR_DKGRAY);

        guifont[FONT_7] = new GUI_Font(this, customfont, (240 * getScaleMult()) / getScaleDiv(), GUI_Font.FONTSTYLE_OUTLINED);
        guifont[FONT_7].setFontAlignmentHorizontal(GUI_Font.ALIGNHOR_CENTER);
        guifont[FONT_7].setFontAlignmentVertical(GUI_Font.ALIGNHOR_CENTER);
        guifont[FONT_7].setFontColor(GUI_Font.FONTCOLOR_BLUE);
        guifont[FONT_2].setFontColorOutline(GUI_Font.FONTCOLOR_BLACK);
    }

    public NgApp getNgApp() {
        return baseapp.appManager.getNgApp();
    }

    public int getScaleMult() {
        return baseapp.appManager.scaleresmult;
    }

    public int getScaleDiv() {
        return baseapp.appManager.scaleresdiv;
    }

    public int generateObjectId() {
        lastobjectid++;
        return lastobjectid;
    }

    public Resources getResources() {
        return baseapp.activity.getResources();
    }

    public AssetManager getAssets() {
        return baseapp.activity.getAssets();
    }

    public void setBaseImage(int imageNo, Resources resources, int resId) {
        baseimage[imageNo] = BitmapFactory.decodeResource(resources, resId);
    }

    public void setBaseImage(int imageNo, String assetFilepath) {
        baseimage[imageNo] = Utils.loadImage(getNgApp(), assetFilepath);
    }

    public void setBaseImage(int imageNo, Bitmap bitmapImage) {
        baseimage[imageNo] = bitmapImage;
    }

    public Bitmap getBaseImage(int baseimageNo) {
        return baseimage[baseimageNo];
    }

    public void draw(Canvas canvas, int baseimageNo, Rect src, Rect dst, Paint paint) {
        canvas.drawBitmap(baseimage[baseimageNo], src, dst, paint);
    }

    public void setBaseFont(int fontNo, String path) {
        basefont[fontNo] = Typeface.createFromFile(path);
    }

    public Typeface getBaseFont(int basefontNo) {
        return basefont[basefontNo];
    }

    public void setFont(int fontNo, GUI_Font fontPaint) {
        guifont[fontNo] = fontPaint;
    }

    public GUI_Font getFont(int fontNo) {
        return guifont[fontNo];
    }

    public void drawText(Canvas canvas, String string, int x, int y, int basefontpaintNo) {
        guifont[basefontpaintNo].draw(canvas, string, x, y);
    }

    public void draw(Canvas canvas) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible()) {
                    objects.elementAt(i).draw(canvas);
                }
            }
        }
    }

    public int getWidth() {
        return baseapp.getWidth();
    }

    public int getHeight() {
        return baseapp.getHeight();
    }

    public void touchDown(int x, int y, int id, boolean onlyDialogue) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive() && !(isdialogueshown && onlyDialogue && (objects.elementAt(i).getObjectType() != OBJTYPE_DIALOGUE && objects.elementAt(i).getObjectType() != OBJTYPE_NOTIFICATION))) {
                    objects.elementAt(i).touchDown(x, y, id);
                }
            }
        }
    }

    public void touchMove(int x, int y, int id, boolean onlyDialogue) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive() && !(isdialogueshown && onlyDialogue && (objects.elementAt(i).getObjectType() != OBJTYPE_DIALOGUE && objects.elementAt(i).getObjectType() != OBJTYPE_NOTIFICATION))) {
                    objects.elementAt(i).touchMove(x, y, id);
                }
            }
        }
    }

    public void touchUp(int x, int y, int id, boolean onlyDialogue) {
        if (!objects.isEmpty()) {
            for (int i=objects.size()-1; i>=0; i--) {
                if ((objects.elementAt(i).getObjectType() == OBJTYPE_DIALOGUE || objects.elementAt(i).getObjectType() == OBJTYPE_NOTIFICATION) && objects.elementAt(i).isVisible() && objects.elementAt(i).isHideWhenOutClick()) {
                    if (!(x >= objects.elementAt(i).getX() && x < objects.elementAt(i).getX() + objects.elementAt(i).getWidth() && y >= objects.elementAt(i).getY() && y < objects.elementAt(i).getY() + objects.elementAt(i).getHeight())) {
                        hide(objects.elementAt(i));
                        return;
                    }
                }
            }
//            Log.i("GUI", "touchUp 1, objects.size:" + objects.size());
            for (int i=objects.size()-1; i>=0; i--) {
//                if (objects.elementAt(i).getId() == 3 || objects.elementAt(i).getId() == 4) {
//                    Log.i("GUI", "touchUp 2, objId:" + objects.elementAt(i).getId() + ", iV:" + objects.elementAt(i).isVisible() + ", iA:" + objects.elementAt(i).isActive());
//                }
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive() && !(isdialogueshown && onlyDialogue && (objects.elementAt(i).getObjectType() != OBJTYPE_DIALOGUE && objects.elementAt(i).getObjectType() != OBJTYPE_NOTIFICATION))) {
                    objects.elementAt(i).touchUp(x, y, id);
//                    break;
                }
            }
        }
    }

    public void add(GUI_Object object) {
        if (object.getObjectType() == OBJTYPE_DIALOGUE || object.getObjectType() == OBJTYPE_NOTIFICATION || objects.size() == 0) {
//            Log.i("GUI", "AddObject add1, objId:" + object.getId() + ", objects.size:" + objects.size());
            objects.add(object);
//            Log.i("GUI", "AddObject add2, objId:" + object.getId() + ", objects.size:" + objects.size());
        }
        else {
//            Log.i("GUI", "AddObject ins1, objId:" + object.getId() + ", objects.size:" + objects.size());
            objects.add(0, object);
//            Log.i("GUI", "AddObject ins2, objId:" + object.getId() + ", objects.size:" + objects.size());
        }
        object.setParent(null);
    }

    public void remove(GUI_Object object) {
        objects.remove(object);
        object.setParent(null);
    }

    public boolean isInitialized() {
        return isinitialized;
    }

    public void reset() {
        if (!objects.isEmpty()) {
            for (int i = 0; i < objects.size(); i++) {
                if (objects.elementAt(i).getObjectType() != OBJTYPE_BUTTON) objects.elementAt(i).reset();
            }
        }
    }

    public void show(GUI_Object object) {
        object.setVisible(true);
        if (object.getObjectType() == OBJTYPE_DIALOGUE || object.getObjectType() == OBJTYPE_NOTIFICATION) {
            objects.remove(object);
            objects.add(object);
            isdialogueshown = true;
            if (object.getObjectType() == OBJTYPE_NOTIFICATION) isnotificationshown = true;
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).getParent() != null) {
                    if (objects.elementAt(i).getParent().getId() == object.getId()) objects.elementAt(i).reset();
                }
            }
        }
    }

    public void show(int objectId) {
        if (!objects.isEmpty()) {
            int oi = -1;
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).getId() == objectId) {
                    oi = i;
                    break;
                }
            }
            if (oi > -1) {
                show(objects.elementAt(oi));
            }
        }
    }

    public void hide(GUI_Object object) {
        object.setVisible(false);
        if (object.getObjectType() == OBJTYPE_DIALOGUE || object.getObjectType() == OBJTYPE_NOTIFICATION) {
            isdialogueshown = false;
            isnotificationshown = false;
            checkIsAnyDialogueShown();
        }
    }

    public void hide(int objectId) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).getId() == objectId) {
                    objects.elementAt(i).setVisible(false);
                    break;
                }
            }
            isdialogueshown = false;
            isnotificationshown = false;
            checkIsAnyDialogueShown();
        }
    }

    private void checkIsAnyDialogueShown() {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if ((objects.elementAt(i).getObjectType() == OBJTYPE_DIALOGUE || objects.elementAt(i).getObjectType() == OBJTYPE_NOTIFICATION) && objects.elementAt(i).isVisible()) {
                    isdialogueshown = true;
                    if (objects.elementAt(i).getObjectType() == OBJTYPE_NOTIFICATION) isnotificationshown = true;
                    break;
                }
            }
        }
    }

    public void hideAllDialogues() {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).getObjectType() == GUI.OBJTYPE_DIALOGUE || objects.elementAt(i).getObjectType() == OBJTYPE_NOTIFICATION) {
                    objects.elementAt(i).setVisible(false);
                }
            }
            isdialogueshown = false;
            isnotificationshown = false;
        }
    }

    public void hideAll() {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                objects.elementAt(i).setVisible(false);
            }
            isdialogueshown = false;
            isnotificationshown = false;
        }
    }

    public void setObjectVisibility(GUI_Object object, boolean isVisible) {
        object.setVisible(isVisible);
    }

    public void setObjectVisibility(int objectId, boolean isVisible) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).getId() == objectId) {
                    objects.elementAt(i).setVisible(isVisible);
                    break;
                }
            }
        }
    }

    public boolean isDialogueShown() {
        return isdialogueshown;
    }

    public boolean isNotificationShown() {
        return isnotificationshown;
    }

    public void onGuiClick(int objectId) {
        baseapp.canvasManager.currentCanvas.onGuiClick(objectId);
    }

    public void onDialoguenHide(int objectId) {
        if (baseapp.canvasManager.currentCanvas != null) baseapp.canvasManager.currentCanvas.onDialogueHide(objectId);
    }

    public void onNotificationHide(int objectId) {
        if (baseapp.canvasManager.currentCanvas != null) baseapp.canvasManager.currentCanvas.onNotificationHide(objectId);
    }


}
