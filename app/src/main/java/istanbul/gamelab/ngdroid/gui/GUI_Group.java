package istanbul.gamelab.ngdroid.gui;

import android.graphics.Canvas;

import java.util.Vector;

/**
 * Created by noyan on 16.08.2016.
 * Nitra Games Ltd.
 */

public class GUI_Group extends GUI_Object {

    private Vector<GUI_Object> objects;


    public GUI_Group(GUI gui) {
        super(gui);
        setObjectType(GUI.OBJTYPE_GROUP);
        objects = new Vector<>();
    }

    public boolean add(GUI_Object guiObject) {
        boolean isadded = false;
        if (!guiObject.hasParent()) {
            if (objects.add(guiObject)) {
                guiObject.setParent(this);
                isadded = true;
            }
        }
        return isadded;
    }

    public boolean remove(GUI_Object guiObject) {
        boolean isremoved = false;
        if (guiObject.hasParent()) {
            if (objects.remove(guiObject)) {
                guiObject.setParent(null);
                isremoved = true;
            }
        }
        return isremoved;
    }

    public int getChildNum() {
        return objects.size();
    }

    @Override
    public void setVisible(boolean isVisible) {
        super.setVisible(isVisible);
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                objects.elementAt(i).setVisible(isVisible);
            }
        }
    }

    public void reset() {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                objects.elementAt(i).reset();
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!isvisible) return;
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible()) objects.elementAt(i).draw(canvas);
            }
        }
    }

    public void touchDown(int x, int y, int id) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive()) {
                    objects.elementAt(i).touchDown(x, y, id);
                }
            }
        }
    }

    public void touchMove(int x, int y, int id) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive()) {
                    objects.elementAt(i).touchMove(x, y, id);
                }
            }
        }
    }

    public void touchUp(int x, int y, int id) {
        if (!objects.isEmpty()) {
            for (int i=0; i<objects.size(); i++) {
                if (objects.elementAt(i).isVisible() && objects.elementAt(i).isActive()) {
                    objects.elementAt(i).touchUp(x, y, id);
                }
            }
        }
    }

}
