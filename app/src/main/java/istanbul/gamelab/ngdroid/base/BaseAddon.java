package istanbul.gamelab.ngdroid.base;

import com.ngdroidapp.NgApp;

import java.util.Vector;

/**
 * Created by noyan on 27.10.2016.
 * Nitra Games Ltd.
 */

public abstract class BaseAddon {
    protected NgApp root;
    public static Vector<BaseAddon> ngAddons = new Vector<>();

    public BaseAddon(NgApp ngApp) {
        root = ngApp;
//        synchronized (ngAddons) {
            ngAddons.add(this);
//        }
    }

    public void onStart() {
        start();
    }

    public void onPause() {
        pause();
    }

    public void onResume() {
        resume();
    }

    public void onStop() {
        stop();
    }

    public void onDestroy() {
        destroy();
    }

    public void release(){
//        synchronized (ngAddons) {
            ngAddons.remove(this);
//        }
    }

    protected abstract void start();
    protected abstract void pause();
    protected abstract void resume();
    protected abstract void stop();
    protected abstract void destroy();

}
