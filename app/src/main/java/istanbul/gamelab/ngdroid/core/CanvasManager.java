package istanbul.gamelab.ngdroid.core;

import istanbul.gamelab.ngdroid.base.BaseCanvas;

/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class CanvasManager {
    public BaseCanvas currentCanvas;
    private BaseCanvas tempCanvas;
    int displaychange;
    private static final short DISPLAY_CHANGE_NONE = 0, DISPLAY_CHANGE_CURRENT = 1;

    public CanvasManager() {
        displaychange = DISPLAY_CHANGE_NONE;
    }

    public void updateDisplay() {
        switch(displaychange) {
            case DISPLAY_CHANGE_NONE:
                break;
            case DISPLAY_CHANGE_CURRENT:
                if (currentCanvas != null) {
                    currentCanvas.hideNotify();
                    currentCanvas = null;
                }
                currentCanvas = tempCanvas;
                tempCanvas = null;
                currentCanvas.showNotify();
                currentCanvas.setup();
                displaychange = DISPLAY_CHANGE_NONE;
                break;
            default:
                break;
        }
    }

    public boolean isCanvasShown() {
        if (currentCanvas == null) return false;
        return true;
    }

    public void setCurrentCanvas(BaseCanvas canvas) {
        tempCanvas = canvas;
        displaychange = DISPLAY_CHANGE_CURRENT;
    }

//    public BaseCanvas getCurrentCanvas() {
//        return currentCanvas;
//    }


}
