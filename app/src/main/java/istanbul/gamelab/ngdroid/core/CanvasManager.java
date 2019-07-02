package istanbul.gamelab.ngdroid.core;

import istanbul.gamelab.ngdroid.base.BaseApp;
import istanbul.gamelab.ngdroid.base.BaseCanvas;

/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class CanvasManager {
    private BaseApp root;
    public BaseCanvas currentCanvas;
    private BaseCanvas tempCanvas;
    int displaychange;
    private static final short DISPLAY_CHANGE_NONE = 0, DISPLAY_CHANGE_CURRENT = 1;

    public CanvasManager(BaseApp baseApp) {
        displaychange = DISPLAY_CHANGE_NONE;
        root = baseApp;
    }

    public void updateDisplay() {
        switch(displaychange) {
            case DISPLAY_CHANGE_NONE:
                break;
            case DISPLAY_CHANGE_CURRENT:
                if (currentCanvas != null) {
                    root.appManager.enableTouchEvents(false);
                    currentCanvas.hideNotify();
                    currentCanvas = null;
                }
                currentCanvas = tempCanvas;
                tempCanvas = null;
                currentCanvas.showNotify();
                currentCanvas.setup();
                root.appManager.enableTouchEvents(true);
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
