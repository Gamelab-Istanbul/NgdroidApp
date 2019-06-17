package istanbul.gamelab.ngdroid.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
//import android.support.v7.app.AlertDialog;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.TelephonyManager;

import com.ngdroidapp.NgApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Vector;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by noyan on 24.09.2016.
 * Nitra Games Ltd.
 */

public class Utils {

    private static int gcbleft, gcbright, gcbtop, gcbbottom;
    private static int cppcpixel1, cppcpixel2;
    private static int cppci, cppcj;
    private static Rect cppcoverlap;
    private static Rect gcbbounds = new Rect();

    /**
     * Loads an immutable image from the /assets/images folder.
     *
     * @param ngApp The root app of the caller class
     * @param imagePath Path of the image to load. The image should be stored under /assets/images folder
     * @return The immutable image
     */
    public static Bitmap loadImage(NgApp ngApp, String imagePath) {
        Bitmap image;
        try {
            InputStream istr = ngApp.activity.getAssets().open("images/" + imagePath);
            image = BitmapFactory.decodeStream(istr);
        } catch(IOException ex) {
            return null;
        }
        return image;
    }


    /**
     * Checks if 2 Android Rect objects are colliding or not.
     *
     * @param rect1 The first Rect object
     * @param rect2 The second Rect object
     */
    public static boolean checkCollision(Rect rect1, Rect rect2) {
        return checkCollision(rect1.left, rect1.top, rect1.right, rect1.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom);
    }


    /**
     *  Checks if 2 rectangles are colliding or not. Thanks to @efecekirge for the algorithm of the method.
     *
     * @param xLeft1 Left x of the first rectangle
     * @param yUp1 Top y of the first rectangle
     * @param xRight1 Right x of the first rectangle
     * @param yBottom1 Bottom y of the first rectangle
     * @param xLeft2 Left x of the second rectangle
     * @param yUp2 Top y of the first rectangle
     * @param xRight2 Right x of the first rectangle
     * @param yBottom2 Bottom y of the first rectangle
     * @return true if the two rectangles are colliding, false if not.
     */
    public static boolean checkCollision(int xLeft1, int yUp1, int xRight1, int yBottom1, int xLeft2, int yUp2, int xRight2, int yBottom2) {
        if(xLeft1 < xRight2 && xRight1 > xLeft2 && yBottom1 > yUp2 && yUp1 < yBottom2) {
            return true;
        }
        return false;
    }

    /**
     * Checks pixel perfect collision.
     *
     * @param image1 The first Bitmap object
     * @param image2 The secons Bitmap object
     * @param rect1 The first Rect object
     * @param rect2 The second Rect object
     **/

    public static boolean checkPixelPerfectCollision(Bitmap image1, Bitmap image2, Rect rect1, Rect rect2) {
        if(checkCollision(rect1, rect2)) {
            cppcoverlap = getCollisionBounds(rect1, rect2);
            for (cppci = cppcoverlap.left; cppci < cppcoverlap.right; cppci++) {
                for (cppcj = cppcoverlap.top; cppcj < cppcoverlap.bottom; cppcj++) {
                    cppcpixel1 = image1.getPixel(cppci - rect1.left, cppcj - rect1.top);
                    cppcpixel2 = image2.getPixel(cppci - rect2.left, cppcj - rect2.top);
                    if (cppcpixel1 != Color.TRANSPARENT && cppcpixel2 != Color.TRANSPARENT) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns 2 Rect objects overlap.
     *
     * @param rect1 The first Rect object
     * @param rect2 The second Rect object
     **/

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        gcbleft = Math.max(rect1.left, rect2.left);
        gcbtop = Math.max(rect1.top, rect2.top);
        gcbright = Math.min(rect1.right, rect2.right);
        gcbbottom = Math.min(rect1.bottom, rect2.bottom);
        gcbbounds.set(gcbleft, gcbtop, gcbright, gcbbottom);
        return gcbbounds;
    }

    /**
     * Provides the country of the service provider. This is the best method to find out the country of the user.
     *
     * @param ngApp The root app
     * @return ISO code of the country
     */
    private static String getCountrySim(NgApp ngApp) {
        TelephonyManager tm = (TelephonyManager)ngApp.activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimCountryIso();
    }


    /**
     * Provides the country locale information of the device.
     *
     * @return Country code of the device locale
     */
    private static String getCountryLocale() {
        return Locale.getDefault().getCountry();
    }


    /**
     * Splits a string using the given delimiter and puts the tokens into a vector.
     *
     * @param str String to split
     * @param delimiter The delimiter string
     * @return The vector containing the tokens
     */
    public static Vector<String> splitString(String str, String delimiter) {
        Vector<String> strVec = new Vector<>();
        int p = 0, dp;
        do {
            dp = str.indexOf(delimiter, p);
            if (dp == -1) dp = str.length();
            if (dp == p) {
                strVec.add("");
                p += delimiter.length();
            } else {
                strVec.add(str.substring(p, dp));
                p = dp + 1;
            }
        } while(p < str.length());
        return strVec;
    }


    /**
     * Shows an {@link android.app.AlertDialog} with an 'OK' button and a message.
     *
     * @param ngApp the root app in which the Dialog should be displayed.
     * @param message the message to display in the Dialog.
     */
    public static void showAlert(NgApp ngApp, String message) {
        (new AlertDialog.Builder(ngApp.activity)).setMessage(message)
                .setNeutralButton(android.R.string.ok, null).create().show();
    }

    /**
     * Creates a simple {@link Dialog} with an 'OK' button and a message.
     *
     * @param ngApp the root app in which the Dialog should be displayed.
     * @param text the message to display on the Dialog.
     * @return an instance of {@link android.app.AlertDialog}
     */
    public static Dialog makeSimpleDialog(NgApp ngApp, String text) {
        return (new AlertDialog.Builder(ngApp.activity)).setMessage(text)
                .setNeutralButton(android.R.string.ok, null).create();
    }

    /**
     * Creates a simple {@link Dialog} with an 'OK' button, a title, and a message.
     *
     * @param ngApp the root app in which the Dialog should be displayed.
     * @param title the title to display on the dialog.
     * @param text the message to display on the Dialog.
     * @return an instance of {@link android.app.AlertDialog}
     */
    public static Dialog makeSimpleDialog(NgApp ngApp, String title, String text) {
        return (new AlertDialog.Builder(ngApp.activity))
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton(android.R.string.ok, null)
                .create();
    }

    /**
     * Vibrates android device.
     *
     * @param milliseconds vibrates duration
     * @param amplitude vibrates magnitude
     * */

    public static void vibrate(NgApp root, int milliseconds, int amplitude) {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) root.activity.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(milliseconds, amplitude));
        } else {
            ((Vibrator) root.activity.getSystemService(VIBRATOR_SERVICE)).vibrate(milliseconds);
        }
    }
}
