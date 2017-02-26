package com.ngdroidaddons.ngaapphashkey;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import istanbul.gamelab.ngdroid.base.BaseAddon;
import istanbul.gamelab.ngdroid.util.Log;
import com.ngdroidapp.NgApp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by noyan on 10.11.2016.
 * Nitra Games Ltd.
 */

public class NgaAppHashKey extends BaseAddon {

    public NgaAppHashKey(NgApp ngApp) {
        super(ngApp);
    }

    public void showHashKey() {
        try {
            PackageInfo info = root.activity.getPackageManager().getPackageInfo(root.activity.getPackageName(), PackageManager.GET_SIGNATURES); //Your            package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }


    public void start() {}
    public void pause() {}
    public void resume() {}
    public void stop() {}
    public void destroy() {}
}
