package istanbul.gamelab.ngdroid.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import istanbul.gamelab.ngdroid.base.BaseActivity;
import istanbul.gamelab.ngdroid.base.BaseAddon;
import com.ngdroidapp.NgApp;
import istanbul.gamelab.ngdroid.util.Log;


/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */

public class AppManager extends SurfaceView implements SurfaceHolder.Callback {
    public static int STATE_RUNNING = 0, STATE_PAUSED = 1;
    public static final int RESOLUTION_UHD = 0, RESOLUTION_QHD = 1, RESOLUTION_FULLHD = 2, RESOLUTION_HD = 3, RESOLUTION_QFHD = 4, RESOLUTION_WVGA = 5, RESOLUTION_HVGA = 6;

    private static final String TAG = AppManager.class.getSimpleName();
    private MainThread thread;
    private NgApp ngapp;
    private CanvasManager canvasmanager;
    private boolean initialstart;
    private int state, screenwidth, screenheight, screenwidthhalf, screenheighthalf;
    private int resolution, unitresolution;
    private int max_touch_num, touch_oldx[], touch_oldy[];
    private int touch_num, touch_x, touch_y, touch_index, touch_id, touch_action;
    private int tmoi, tmoid, tmox, tmoy;
    public int scaleresmult, scaleresdiv, scaleresmultunit, scaleresdivunit;
    private final Runtime runtime = Runtime.getRuntime();

    public AppManager(BaseActivity baseActivity) {
        super(baseActivity);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        initialstart = true;
        state = STATE_RUNNING;
        setupScreenSize(baseActivity);
        setupScreenResolution(screenwidth, screenheight);
        setUnitResolution(0);
        max_touch_num = 10;
        touch_oldx = new int[max_touch_num];
        touch_oldy = new int[max_touch_num];

        ngapp = new NgApp(baseActivity, this);
        canvasmanager = ngapp.canvasManager;

        for(BaseAddon ngAddon : BaseAddon.ngAddons){
            ngAddon.onStart();
        }
    }

    public RelativeLayout getParentLayout() {
        return (RelativeLayout)this.getParent();
    }

    @SuppressLint("NewApi")
    private void setupScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        try {
            display.getRealSize(size);
            screenwidth = size.x;
            screenheight = size.y;
        } catch (NoSuchMethodError e) {
            screenwidth = display.getWidth();
            screenheight = display.getHeight();
        }
        screenwidthhalf = screenwidth / 2;
        screenheighthalf = screenheight / 2;
    }

    private int resolution_sizes[][] = {
            {2160, 3840}, //uhd
            {1440, 2560}, //qhd
            {1080, 1920}, //fhd
            { 720, 1280}, //hd
            { 540,  960}, //qfhd
            { 480,  800}, //wvga
            { 320,  480}  //hvga
    };
    private int resolution_multipliers[][] = {
            { 3,   2}, //uhd
            { 1,   1}, //qhd
            { 3,   4}, //fhd
            { 6,  12}, //hd
            { 3,   8}, //qfhd
            {12,  40}, //wvga
            {84, 400}  //hvga
    };

    private void setupScreenResolution(int screenWidth, int screenHeight) {
        int temp = 0;
        if (screenwidth > screenHeight) {
            for (int i=0; i<resolution_sizes.length; i++) {
                temp = resolution_sizes[i][0];
                resolution_sizes[i][0] = resolution_sizes[i][1];
                resolution_sizes[i][1] = temp;

                temp = resolution_multipliers[i][0];
                resolution_multipliers[i][0] = resolution_multipliers[i][1];
                resolution_multipliers[i][1] = temp;
            }
        }

        resolution = 0;

        for (int i=0; i<resolution_sizes.length-1; i++) {
            if (screenWidth < resolution_sizes[i][0] && screenHeight < resolution_sizes[i][1] * 0.92f) {
                resolution = i + 1;
            }
        }

        scaleresmult = resolution_multipliers[resolution][0];
        scaleresdiv = resolution_multipliers[resolution][1];
    }

    public int getWidthUnit() {
        return resolution_sizes[unitresolution][0];
    }

    public int getHeightUnit() {
        return resolution_sizes[unitresolution][1];
    }

    public void setUnitResolution(int unitResolution) {
        if (unitResolution < 0 || unitResolution > RESOLUTION_HVGA) {
            return;
        }
        unitresolution = unitResolution;
        scaleresmultunit = resolution_multipliers[unitresolution][0];
        scaleresdivunit = resolution_multipliers[unitresolution][1];
    }

    public int getUnitResolution() {
        return unitresolution;
    }

    public int scaleNum(int unitNumber) {
        return (unitNumber * scaleresdivunit * scaleresmult) / (scaleresmultunit * scaleresdiv);
    }

    public int getScreenResolution() {
        return resolution;
    }

    public int getScreenWidthHalf() {
        return screenwidthhalf;
    }

    public int getScreenHeightHalf() {
        return screenheighthalf;
    }

    public int getScreenWidth() {
        return screenwidth;
    }

    public int getScreenHeight() {
        return screenheight;
    }

    public void setFrameRateTarget(int frameRate) {
        thread.setFrameRateTarget(frameRate);
    }

    public int getFrameRate() {
        return thread.getFrameRate();
    }

    public int getFrameRateTarget() {
        return thread.getFrameRateTarget();
    }

    /**
     * Gets the max memory that Java VM can use in bytes.
     * Divide the returned number by 1024 for KB and divide it by 1048576 for MB
     *
     * @return The max memory Java VM can use
     */
    public final long getMaxMemory() {
        return runtime.maxMemory();
    }

    public final long getUsedMemory() {
        return runtime.totalMemory() - runtime.freeMemory();
    }

    public final long getAvailableMemory() {
        return getMaxMemory() - getUsedMemory();
    }

    public int getState() {
        return state;
    }

        @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("NAM", "surfaceChanged");
        ngapp.activity.makeFullScreen();
        ngapp.surfaceChanged(width, height);
        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.surfaceChanged(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i("NAM", "surfaceCreated");
        ngapp.surfaceCreated();
        if (initialstart) {
            ngapp.setup();
            initialstart = false;
        }
        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.surfaceCreated();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("NAM", "surfaceDestroyed");
        //ngapp.surfaceDestroyed();
        //if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.surfaceDestroyed();
        //System.exit(0);

        boolean retry = thread != null;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join(10);
                retry = false;
            } catch (Exception e) {
                // try again shutting down the thread
            }
        }
    }

    public void onPause() {
        Log.i("NAM", "onPause");

        if (thread != null) {
            ngapp.pause();
            if (canvasmanager.isCanvasShown()) {
                canvasmanager.currentCanvas.pause();
                canvasmanager.currentCanvas.hideNotify();
            }

            for(BaseAddon ngAddon : BaseAddon.ngAddons){
                ngAddon.onPause();
            }

            thread.setRunning(false);
        }

        state = STATE_PAUSED;
    }

    public void onResume() {
        Log.i("NAM", "onResume 1, thread:" + thread);
        if (!initialstart) {
            ngapp.resume();
            if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.resume();
            ngapp.reloadTextures();
            if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.reloadTextures();

            for(BaseAddon ngAddon : BaseAddon.ngAddons){
                ngAddon.onResume();
            }

            ngapp.gui.reset();
            if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.showNotify();
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            state = STATE_RUNNING;

            Log.i("NAM", "onResume 2, thread:" + thread);
        }
    }

    public void onStop() {
        for(BaseAddon ngAddon : BaseAddon.ngAddons){
            ngAddon.onStop();
        }
    }

    public void onDestroy() {
        for(BaseAddon ngAddon : BaseAddon.ngAddons){
            ngAddon.onDestroy();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (canvasmanager.isCanvasShown()) return canvasmanager.currentCanvas.backPressed();
            else return ngapp.backPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touch_num = event.getPointerCount();
        if (touch_num >= max_touch_num) return true;
        touch_action = event.getActionMasked();

        switch(touch_action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                touch_index = event.getActionIndex();
                touch_id = event.getPointerId(touch_index);
                touch_x = (int)event.getX(touch_index);
                touch_y = (int)event.getY(touch_index);
                touch_oldx[touch_id] = touch_x;
                touch_oldy[touch_id] = touch_y;
                if (ngapp.gui.isInitialized() && ngapp.gui.isDialogueShown()) {
                    ngapp.gui.touchDown(touch_x, touch_y, touch_id, true);
                } else {
                    if (!ngapp.gui.isInitialized() || !ngapp.gui.isDialogueShown()) {
                        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.touchDown(touch_x, touch_y, touch_id);
                        else ngapp.touchDown(touch_x, touch_y, touch_id);
                    }
                    if (ngapp.gui.isInitialized() && !ngapp.gui.isDialogueShown()) ngapp.gui.touchDown(touch_x, touch_y, touch_id, false);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                for (tmoi = 0; tmoi < touch_num; tmoi++) {
                    tmoid = event.getPointerId(tmoi);
                    tmox = (int)event.getX(tmoi);
                    tmoy = (int)event.getY(tmoi);
                    if (tmox != touch_oldx[tmoid] && tmoy != touch_oldy[tmoid]) {
                        if (ngapp.gui.isInitialized() && ngapp.gui.isDialogueShown()) {
                            ngapp.gui.touchMove(tmox, tmoy, tmoid, true);
                        } else {
                            if (!ngapp.gui.isInitialized() || !ngapp.gui.isDialogueShown()) {
                                if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.touchMove(tmox, tmoy, tmoid);
                                else ngapp.touchMove(tmox, tmoy, tmoid);
                            }
                            if (ngapp.gui.isInitialized() && !ngapp.gui.isDialogueShown()) ngapp.gui.touchMove(tmox, tmoy, tmoid, false);
                        }

                        touch_oldx[tmoid] = tmox;
                        touch_oldy[tmoid] = tmoy;
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                touch_index = event.getActionIndex();
                touch_id = event.getPointerId(touch_index);
                touch_x = (int)event.getX(touch_index);
                touch_y = (int)event.getY(touch_index);
                if (ngapp.gui.isInitialized() && ngapp.gui.isDialogueShown()) {
                    ngapp.gui.touchUp(touch_x, touch_y, touch_id, true);
                } else {
                    if (!ngapp.gui.isInitialized() || !ngapp.gui.isDialogueShown()) {
                        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.touchUp(touch_x, touch_y, touch_id);
                        else ngapp.touchUp(touch_x, touch_y, touch_id);
                    }
                    if (ngapp.gui.isInitialized() && !ngapp.gui.isDialogueShown()) ngapp.gui.touchUp(touch_x, touch_y, touch_id, false);
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }


    public void onUpdate() {
        canvasmanager.updateDisplay();
        ngapp.update();
        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.update();
    }

    @Override
    public void onDraw(Canvas canvas) {
        ngapp.draw(canvas);
        if (canvasmanager.isCanvasShown()) canvasmanager.currentCanvas.draw(canvas);
        ngapp.gui.draw(canvas);
//        if (ngapp.gui.isDialogueShown()) ngapp.gui.draw(canvas);
    }

    public NgApp getNgApp() {
        return ngapp;
    }


}
