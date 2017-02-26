package istanbul.gamelab.ngdroid.util;


/**
 * Created by noyan on 23.09.2016.
 * Nitra Games Ltd.
 */

public class Log {
    public static final int LOGLEVEL_VERBOSE = 2, LOGLEVEL_DEBUG = 3, LOGLEVEL_INFO = 4, LOGLEVEL_WARN = 5, LOGLEVEL_ERROR = 6, LOGLEVEL_ASSERT = 7, LOGLEVEL_SILENT = 8;

    private static int loglevel = LOGLEVEL_VERBOSE;

    public static void setLogLevel(int logLevel) {
        loglevel = logLevel;
    }

    public static int getLogLevel() {
        return loglevel;
    }

    public static void v(String tag, String msg) {
        if (loglevel <= LOGLEVEL_VERBOSE) android.util.Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (loglevel <= LOGLEVEL_DEBUG) android.util.Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (loglevel <= LOGLEVEL_INFO) android.util.Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (loglevel <= LOGLEVEL_WARN) android.util.Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (loglevel <= LOGLEVEL_ERROR) android.util.Log.e(tag, msg);
    }

    public static void wtf(String tag, String msg) {
        if (loglevel <= LOGLEVEL_ASSERT) android.util.Log.wtf(tag, msg);
    }
}
