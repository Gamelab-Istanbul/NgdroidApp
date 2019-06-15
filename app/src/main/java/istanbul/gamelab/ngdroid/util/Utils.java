package istanbul.gamelab.ngdroid.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
//import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;

import com.ngdroidapp.NgApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Vector;

/**
 * Created by noyan on 24.09.2016.
 * Nitra Games Ltd.
 */

public class Utils {

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

    public static boolean pixelPerfectCollision(Bitmap image1, Bitmap image2, Rect rect1, Rect rect2) {
        if(Rect.intersects(rect1, rect2)) {
            Rect overlap = getCollisionBounds(rect1, rect2);
            for (int i = overlap.left; i < overlap.right; i++) {
                for (int j = overlap.top; j < overlap.bottom; j++) {
                    int pixel1 = image1.getPixel(i - rect1.left, j - rect1.top);
                    int pixel2 = image2.getPixel(i - rect2.left, j - rect2.top);
                    if (pixel1 != Color.TRANSPARENT && pixel2 != Color.TRANSPARENT) {
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
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);;
        return new Rect(left, top, right, bottom);
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

}
