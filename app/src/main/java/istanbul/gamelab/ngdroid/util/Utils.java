package istanbul.gamelab.ngdroid.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
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
     * Checks if 2 rectangles are colliding or not. Thanks to @efecekirge for the algorithm of the method.
     *
     * @param rect1 The first rectangle
     * @param rect2 The second rectangle
     */
    public static boolean checkCollision(int xLeft1, int yUp1, int xRight1, int yBottom1, int xLeft2, int yUp2, int xRight2, int yBottom2) {
        if(xLeft1 < xRight2 && xRight1 > xLeft2 && yBottom1 > yUp2 && yUp1 < yBottom2) {
            return true;
        }
        return false;
    }


    private static String getCountrySim(NgApp ngApp) {
        TelephonyManager tm = (TelephonyManager)ngApp.activity.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimCountryIso();
    }


    private static String getCountryLocale() {
        return Locale.getDefault().getCountry();
    }



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
     * Show an {@link android.app.AlertDialog} with an 'OK' button and a message.
     *
     * @param activity the Activity in which the Dialog should be displayed.
     * @param message the message to display in the Dialog.
     */
    public static void showAlert(Activity activity, String message) {
        (new AlertDialog.Builder(activity)).setMessage(message)
                .setNeutralButton(android.R.string.ok, null).create().show();
    }

    /**
     * Create a simple {@link Dialog} with an 'OK' button and a message.
     *
     * @param activity the Activity in which the Dialog should be displayed.
     * @param text the message to display on the Dialog.
     * @return an instance of {@link android.app.AlertDialog}
     */
    public static Dialog makeSimpleDialog(Activity activity, String text) {
        return (new AlertDialog.Builder(activity)).setMessage(text)
                .setNeutralButton(android.R.string.ok, null).create();
    }

    /**
     * Create a simple {@link Dialog} with an 'OK' button, a title, and a message.
     *
     * @param activity the Activity in which the Dialog should be displayed.
     * @param title the title to display on the dialog.
     * @param text the message to display on the Dialog.
     * @return an instance of {@link android.app.AlertDialog}
     */
    public static Dialog makeSimpleDialog(Activity activity, String title, String text) {
        return (new AlertDialog.Builder(activity))
                .setTitle(title)
                .setMessage(text)
                .setNeutralButton(android.R.string.ok, null)
                .create();
    }

}
