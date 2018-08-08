package com.ngdroidaddons.ngaencryptedpreferences;

import istanbul.gamelab.ngdroid.base.BaseAddon;
import com.ngdroidapp.NgApp;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

/**
 * Created by noyan on 27.10.2016.
 * Nitra Games Ltd.
 */

public class NgaEncryptedPreferences extends BaseAddon {
    private EncryptedPreferences encryptedPreferences;

    public NgaEncryptedPreferences(NgApp ngApp) {
        super(ngApp);
    }

    public void initialize(String password) {
        encryptedPreferences = new EncryptedPreferences.Builder(root.activity)
                .withEncryptionPassword(password)
                .build();
    }

    public void putString(String key, String value) {
        encryptedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public void putFloat(String key, Float value) {
        encryptedPreferences.edit()
                .putFloat(key, value)
                .apply();
    }

    public void putLong(String key, Long value) {
        encryptedPreferences.edit()
                .putLong(key, value)
                .apply();
    }

    public void putBoolean(String key, boolean value) {
        encryptedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    public String getString(String key, String defaultValue) {
        return encryptedPreferences.getString(key, defaultValue);
    }

    public Float getFloat(String key, Float defaultValue) {
        return encryptedPreferences.getFloat(key, defaultValue);
    }

    public Long getLong(String key, Long defaultValue) {
        return encryptedPreferences.getLong(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return encryptedPreferences.getBoolean(key, defaultValue);
    }

    public void start() {}
    public void pause() {}
    public void resume() {}
    public void stop() {}
    public void destroy() {}

}
