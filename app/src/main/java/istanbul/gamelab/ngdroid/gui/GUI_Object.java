package istanbul.gamelab.ngdroid.gui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import istanbul.gamelab.ngdroid.util.Log;

/**
 * Created by noyan on 16.08.2016.
 * Nitra Games Ltd.
 */

public abstract class GUI_Object {
    protected GUI gui;
    protected boolean hasparent, isvisible, isactive, drawbackground, hastext, hastitle, drawtext, drawtitle, hidewhenoutclick;
    protected GUI_Group parent;
    protected int objectid;
    private int objecttype;

    protected Rect baserectsrc, baserectdst, baserectsrctemp, baserectdsttemp;
    protected int basesbi, basesx, basesy, basesw, basesh, basedx, basedy, basedw, basedh;
    protected int basetextfontno, basetexty, basetitlex, basetitley, basetitlextemp, basetitleytemp;
    protected String basetext, basetitle;
    protected Paint basepaint;


    public GUI_Object(GUI gui) {
        this.gui = gui;
        objectid = this.gui.generateObjectId();
        objecttype = GUI.OBJTYPE_OBJECT;
        hasparent = false;
        isvisible = true;
        isactive = true;
        drawbackground = false;
        hidewhenoutclick = false;

        baserectsrc = new Rect();
        baserectsrc.set(0, 0, 0, 0);
        baserectdst = new Rect();
        baserectdst.set(0, 0, 0, 0);
        baserectsrctemp = new Rect();
        baserectsrctemp.set(0, 0, 0, 0);
        baserectdsttemp = new Rect();
        baserectdsttemp.set(0, 0, 0, 0);
        basesx = 0;
        basesy = 0;
        basesw = 0;
        basesh = 0;
        basedx = 0;
        basedy = 0;
        basedw = 0;
        basedh = 0;

        basetextfontno = 0;
        basetext = "";
        basetitle = "";
        hastext = false;
        hastitle = false;
        drawtext = true;
        drawtitle = true;
        basepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        basepaint.setTypeface(Typeface.create(basepaint.getTypeface(), Typeface.BOLD));
        basepaint.setColor(Color.WHITE);
        basepaint.setTextSize((80 * this.gui.getScaleMult()) / this.gui.getScaleDiv());
        basepaint.setTextAlign(Paint.Align.CENTER);
        Rect tbounds = new Rect();
        basepaint.getTextBounds("aa", 0, 2, tbounds);
        basetexty = (getHeight() - tbounds.height()) / 2;
        basetitlex = basedx;
        basetitley = basedy;
        basetitlextemp = basedx;
        basetitleytemp = basedy;
    }

    protected void setObjectType(int objectType) {
        objecttype = objectType;
    }

    public int getObjectType() {
        return objecttype;
    }

    public void setBackgroundImage(int baseimageNo, int srcx1, int srcy1, int srcx2, int srcy2) {
        basesbi = baseimageNo;
        basesx = srcx1;
        basesy = srcy1;
        basesw = srcx2 - srcx1;
        basesh = srcy2 - srcy1;
        baserectsrc.set(basesx, basesy, basesx + basesw, basesy + basesh);
        basedw = basesw;
        basedh = basesh;
        drawbackground = true;
    }

    public void setFont(int basefontNo) {
        basetextfontno = basefontNo;
        if (basetextfontno >= 0 && !basetext.equals("")) hastext = true;
        else hastext = false;
        if (basetextfontno >= 0 && !basetitle.equals("")) hastitle = true;
        else hastitle = false;

        Rect tbounds = new Rect();
        basepaint.getTextBounds("aa", 0, 2, tbounds);
        basetexty = (getHeight() - tbounds.height()) / 2;
    }

    public void setText(String text) {
        basetext = text;
        if (basetextfontno >= 0 && !basetext.equals("")) hastext = true;
        else hastext = false;

        Rect tbounds = new Rect();
        basepaint.getTextBounds("aa", 0, 2, tbounds);
        basetexty = (getHeight() - tbounds.height()) / 2;
    }

    public void setTitle(String title) {
        basetitle = title;
        if (basetextfontno >= 0 && !basetitle.equals("")) hastitle = true;
        else hastitle = false;
/*
        Rect tbounds = new Rect();
        basepaint.getTextBounds("QA", 0, 2, tbounds);
        basetitley = (getHeight() - tbounds.height()) / 2;
*/
    }

    public void setTextDrawing(boolean drawText) {
        drawtext = drawText;
    }

    public boolean isTextDrawing() {
        return drawtext;
    }

    public void setTitleDrawing(boolean drawTitle) {
        drawtitle = drawTitle;
    }

    public boolean isTitleDrawing() {
        return drawtitle;
    }

    public void setSize(int width, int height) {
        basedw = width;
        basedh = height;
        baserectdst.set(basedx, basedy, basedx + basedw, basedy + basedh);

        basetitlex = basedx + (basedw / 2);
        basetitley = basedy + ((95 * gui.getScaleMult()) / gui.getScaleDiv());
    }

    public int getWidth() {
        return basedw;
    }

    public int getHeight() {
        return basedh;
    }

    public void setPosition(int basedestx1, int basedesty1) {
//        Log.i("GUI_Object", "objId:" + objectid + ", destx1:" + basedestx1 + ", desty1:" + basedesty1);
        basedx = basedestx1;
        basedy = basedesty1;
        baserectdst.set(basedx, basedy, basedx + basedw, basedy + basedh);

        basetitlex = basedx + (basedw / 2);
        basetitley = basedy + ((95 * gui.getScaleMult()) / gui.getScaleDiv());
    }

    public int getX() {
        return basedx;
    }

    public int getY() {
        return basedy;
    }


    public void setVisible(boolean isVisible) {
        isvisible = isVisible;
    }

    public boolean isVisible() {
        return isvisible;
    }

    public void setActive(boolean isActive) {
        isactive = isActive;
    }

    public boolean isActive() {
        return isactive;
    }

    public void setParent(GUI_Group parentGroup) {
        parent = parentGroup;
        if (parent == null) hasparent = false;
        else hasparent = true;
    }

    public GUI_Group getParent() {
        return parent;
    }

    public boolean hasParent() {
        return hasparent;
    }


    public void draw(Canvas canvas) {
        if (!isvisible) return;
        if (drawbackground) gui.draw(canvas, basesbi, baserectsrc, baserectdst, null);
        if (hastitle && drawtitle) {
            gui.drawText(canvas, basetitle, basetitlex, basetitley, GUI.FONT_2);
        }
    }

    public void draw(Canvas canvas, int destX, int destY) {
        if (!isvisible) return;
        baserectdsttemp.set(destX, destY, destX + basedw, destY + basedh);

        basetitlextemp = destX + (basedw / 2);
        basetitleytemp = destY + ((95 * gui.getScaleMult()) / gui.getScaleDiv());

        if (drawbackground) gui.draw(canvas, basesbi, baserectsrc, baserectdsttemp, null);
        if (hastitle && drawtitle) {
            gui.drawText(canvas, basetitle, basetitlextemp, basetitleytemp, GUI.FONT_2);
        }
    }

    public void draw(Canvas canvas, int destX, int destY, int destW, int destH) {
        if (!isvisible) return;
        baserectdsttemp.set(destX, destY, destX + destW, destY + destH);

        basetitlextemp = destX + (destW / 2);
        basetitleytemp = destY + ((95 * gui.getScaleMult()) / gui.getScaleDiv());

        if (drawbackground) gui.draw(canvas, basesbi, baserectsrc, baserectdsttemp, null);
        if (hastitle && drawtitle) {
            gui.drawText(canvas, basetitle, basetitlextemp, basetitleytemp, GUI.FONT_2);
        }
    }

    public void draw(Canvas canvas, int srcH, int destX, int destY, int destW, int destH, int srcX, int srcY, int srcW) {
        if (!isvisible) return;
        baserectsrctemp.set(srcX, srcY, srcX + srcW, srcY + srcH);
        baserectdsttemp.set(destX, destY, destX + destW, destY + destH);

        basetitlextemp = destX + (destW / 2);
        basetitleytemp = destY + ((95 * gui.getScaleMult()) / gui.getScaleDiv());

        if (drawbackground) gui.draw(canvas, basesbi, baserectsrctemp, baserectdsttemp, null);
        if (hastitle && drawtitle) {
            gui.drawText(canvas, basetitle, basetitlextemp, basetitleytemp, GUI.FONT_2);
        }
    }

    public abstract void touchDown(int x, int y);

    public abstract void touchMove(int x, int y);

    public abstract void touchUp(int x, int y);

    public int getId() {
        return objectid;
    }

    public void setHideWhenOutClick(boolean hide) {
        hidewhenoutclick = hide;
    }

    public boolean isHideWhenOutClick() {
        return hidewhenoutclick;
    }

    public abstract void reset();

}
