package istanbul.gamelab.ngdroid.core;

/**
 * Created by noyan on 24.06.2016.
 */


import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;


/**
 * The Main thread which contains the game loop. The thread must have access to
 * the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();
    private SurfaceHolder surfaceHolder;
    private AppManager appManager;
    public boolean running;
    private int framerate;
    private long starttime, endtime, endtime2, timediff, timediff2, millisecondsperframe;

    public void setRunning(boolean running) {
        synchronized (this) {
            this.running = running;
        }
    }

    public boolean getIsRunning() {
        synchronized(this) {
            return this.running;
        }
    }

    public MainThread(SurfaceHolder surfaceHolder, AppManager appManager) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.appManager = appManager;
        framerate = 60;
        millisecondsperframe = 1000 / framerate;
        starttime = System.currentTimeMillis();
        endtime = System.currentTimeMillis();
        endtime2 = System.currentTimeMillis();
        timediff = endtime - starttime;
        timediff2 = endtime2 - starttime;
    }

    public void setFrameRateTarget(int frameRate) {
        framerate = frameRate;
        millisecondsperframe = 1000 / framerate;
    }

    public int getFrameRate() {
        return (int)(1000 / timediff2);
    }

    public int getFrameRateTarget() {
        return framerate;
    }

    @Override
    @SuppressLint("WrongCall")
    public void run() {
        Canvas canvas;

        while (true) {
            if (running) {
//            Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_DISPLAY);
                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        starttime = System.currentTimeMillis();
                        this.appManager.onUpdate();
                        this.appManager.onDraw(canvas);
                        endtime = System.currentTimeMillis();
                        timediff = endtime - starttime;
                        if (timediff < millisecondsperframe) {
                            sleep(millisecondsperframe - timediff);
                        }
                        endtime2 = System.currentTimeMillis();
                        timediff2 = endtime2 - starttime;
                    }
                } catch (InterruptedException e) {
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }

    }
}
