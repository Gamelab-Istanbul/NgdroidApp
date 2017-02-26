package istanbul.gamelab.ngdroid.gui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * Created by noyan on 23.08.2016.
 * Nitra Games Ltd.
 */

public class GUI_Font {
    public static int FONTTYPE_PLAIN = 0, FONTTYPE_BOLD = 1, FONTTYPE_ITALIC = 2, FONTTYPE_BOLDITALIC = 3;

    public static int FONTSTYLE_NORMAL = 0, FONTSTYLE_SHADOWED = 1, FONTSTYLE_OUTLINED = 2;

    public static int FONTCOLOR_TRANSPARENT = 0x00000000, FONTCOLOR_WHITE = 0xffffffff, FONTCOLOR_BLACK = 0xff000000;
    public static int FONTCOLOR_LTGRAY = 0xffdddddd, FONTCOLOR_GRAY = 0xff888888, FONTCOLOR_DKGRAY = 0xff444444;
    public static int FONTCOLOR_YELLOW = 0xffffff00, FONTCOLOR_RED = 0xffff0000, FONTCOLOR_MAGENTA = 0xffff00ff;
    public static int FONTCOLOR_CYAN = 0xff00ffff, FONTCOLOR_BLUE = 0xff0000ff, FONTCOLOR_GREEN = 0xff00ff00;
    public static int FONTCOLOR_BEIGE = 0xffffcc99, FONTCOLOR_LTBROWN = 0xffcc9966, FONTCOLOR_BROWN = 0xff996633, FONTCOLOR_DKBROWN = 0xff663300;

    public static int ALIGNHOR_LEFT = 0, ALIGNHOR_CENTER = 1, ALIGNHOR_RIGHT = 2;
    public static int ALIGNVER_TOP = 0, ALIGNVER_CENTER = 1, ALIGNVER_BOTTOM = 2;

    private Paint paintfont, paintfont_shadow, paintfont_outline;
    private Rect tbounds, tboundstemp;
    private int fontstyle, fontalignhor, fontalignver, shadowdiffx, shadowdiffy;
    private Paint.Align alignhor[] = {Paint.Align.LEFT, Paint.Align.CENTER, Paint.Align.RIGHT};
    private GUI gui;

    public GUI_Font(GUI Gui) {
        gui = Gui;
        tbounds = new Rect();
        tboundstemp = new Rect();

        //font properties
        paintfont = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintfont.setTypeface(Typeface.create(paintfont.getTypeface(), FONTTYPE_BOLD));
        paintfont.setTextSize((80 * gui.getScaleMult()) / gui.getScaleDiv());
        paintfont.setColor(FONTCOLOR_WHITE);
        fontalignhor = ALIGNHOR_LEFT;
        paintfont.setTextAlign(alignhor[fontalignhor]);
        fontalignver = ALIGNVER_TOP;
        fontstyle = FONTSTYLE_NORMAL;

        recalculateTextBounds();

        //shadow font
        paintfont_shadow = new Paint(paintfont);
        paintfont_shadow.setColor(FONTCOLOR_BLACK);
        shadowdiffx = (int)paintfont.getTextSize() / 20;
        shadowdiffy = shadowdiffx;

        //outline font
        paintfont_outline = new Paint(paintfont);
        paintfont_outline.setColor(FONTCOLOR_BLACK);
        paintfont_outline.setStyle(Paint.Style.STROKE);
        paintfont_outline.setStrokeWidth(3 * (int)paintfont.getTextSize() / 20);
    }

    public GUI_Font(GUI Gui, int fontType, int fontSize, int fontColor, int alignHor, int alignVer, int fontStyle, int shadowColor, int outlineColor) {
        gui = Gui;
        tbounds = new Rect();
        tboundstemp = new Rect();

        //font properties
        paintfont = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintfont.setTypeface(Typeface.create(paintfont.getTypeface(), fontType));
        paintfont.setTextSize(fontSize);
        paintfont.setColor(fontColor);
        fontalignhor = alignHor;
        paintfont.setTextAlign(alignhor[fontalignhor]);
        fontalignver = alignVer;
        fontstyle = fontStyle;

        recalculateTextBounds();

        //shadow font
        paintfont_shadow = new Paint(paintfont);
        paintfont_shadow.setColor(shadowColor);
        shadowdiffx = (int)paintfont.getTextSize() / 20;
        shadowdiffy = shadowdiffx;

        //outline font
        paintfont_outline = new Paint(paintfont);
        paintfont_outline.setColor(outlineColor);
        paintfont_outline.setStyle(Paint.Style.STROKE);
        paintfont_outline.setStrokeWidth(3 * (int)paintfont.getTextSize() / 20);
    }

    public GUI_Font(GUI Gui, GUI_Font original) {
        gui = Gui;
        tbounds = new Rect();
        tboundstemp = new Rect();

        //font properties
        paintfont = new Paint(original.getPaintFont());
        fontalignhor = original.getFontAlignmentHorizontal();
        fontalignver = original.getFontAlignmentVertical();
        fontstyle = original.getFontStyle();

        recalculateTextBounds();

        //shadow font
        paintfont_shadow = new Paint(original.getPaintFontShadow());
        shadowdiffx = (int)paintfont.getTextSize() / 20;
        shadowdiffy = shadowdiffx;

        //outline font
        paintfont_outline = new Paint(original.getPaintFontOutline());
    }

    public GUI_Font(GUI Gui, Typeface customFont, int fontSize, int fontStyle) {
        gui = Gui;
        tbounds = new Rect();
        tboundstemp = new Rect();

        //font properties
        paintfont = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintfont.setTypeface(customFont);
        paintfont.setTextSize(fontSize);
        paintfont.setColor(FONTCOLOR_WHITE);
        fontalignhor = ALIGNHOR_LEFT;
        paintfont.setTextAlign(alignhor[fontalignhor]);
        fontalignver = ALIGNVER_TOP;
        fontstyle = fontStyle;

        recalculateTextBounds();

        //shadow font
        paintfont_shadow = new Paint(paintfont);
        paintfont_shadow.setColor(FONTCOLOR_BLACK);
        shadowdiffx = (int)paintfont.getTextSize() / 20;
        shadowdiffy = shadowdiffx;

        //outline font
        paintfont_outline = new Paint(paintfont);
        paintfont_outline.setColor(FONTCOLOR_BLACK);
        paintfont_outline.setStyle(Paint.Style.STROKE);
        paintfont_outline.setStrokeWidth(3 * (int)paintfont.getTextSize() / 20);
    }

    public Paint getPaint() {
        return paintfont;
    }

    public float getFontSize() {
        return paintfont.getTextSize();
    }


    public Paint getPaintFont() {
        return paintfont;
    }


    public Paint getPaintFontShadow() {
        return paintfont_shadow;
    }


    public Paint getPaintFontOutline() {
        return paintfont_outline;
    }


    public void setFontType(int fontType) {
        paintfont.setTypeface(Typeface.create(paintfont.getTypeface(), fontType));
        recalculateTextBounds();
    }


    public void setFontSize(float fontSize) {
        paintfont.setTextSize(fontSize);
        recalculateTextBounds();

        paintfont_shadow.setTextSize(fontSize);
        shadowdiffx = (int)paintfont.getTextSize() / 20;
        shadowdiffy = shadowdiffx;

        paintfont_outline.setTextSize(fontSize);
        paintfont_outline.setStrokeWidth(3 * (int)paintfont.getTextSize() / 20);
    }


    public void setFontColor(int fontColor) {
        paintfont.setColor(fontColor);
    }


    public void setFontColorShadow(int fontColor) {
        paintfont_shadow.setColor(fontColor);
    }


    public void setFontColorOutline(int fontColor) {
        paintfont_outline.setColor(fontColor);
    }


    public void setFontStyle(int fontStyle) {
        fontstyle = fontStyle;
    }

    public int getFontStyle() {
        return fontstyle;
    }

    public void setFontAlignmentHorizontal(int alignmentHorizontal) {
        fontalignhor = alignmentHorizontal;
        paintfont.setTextAlign(alignhor[fontalignhor]);
        paintfont_shadow.setTextAlign(alignhor[fontalignhor]);
        paintfont_outline.setTextAlign(alignhor[fontalignhor]);
    }


    public void setFontAlignmentVertical(int alignmentVertical) {
        fontalignver = alignmentVertical;
    }


    public int getFontAlignmentHorizontal() {
        return fontalignhor;
    }

    public int getFontAlignmentVertical() {
        return fontalignver;
    }


    public int StringWidth(String string) {
        paintfont.getTextBounds(string, 0, string.length(), tboundstemp);
        return tboundstemp.width();
    }


    public int StringHeight(String string) {
        paintfont.getTextBounds(string, 0, string.length(), tboundstemp);
        return tboundstemp.height();
    }



    private void recalculateTextBounds() {
        paintfont.getTextBounds("QA", 0, 2, tbounds);
    }


    public void draw(Canvas canvas, String string, int x, int y) {
        if (fontstyle == FONTSTYLE_SHADOWED) canvas.drawText(string, x + shadowdiffx, y + shadowdiffy, paintfont_shadow);
        else if (fontstyle == FONTSTYLE_OUTLINED) canvas.drawText(string, x, y, paintfont_outline);
        canvas.drawText(string, x, y, paintfont);
    }

}
