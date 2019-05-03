package com.mycompany.myngdroidapp;

import android.content.Intent;
import android.os.Bundle;

import istanbul.gamelab.ngdroid.base.BaseActivity;
import istanbul.gamelab.ngdroid.core.AppManager;
import istanbul.gamelab.ngdroid.util.Log;

public class GameActivity extends BaseActivity {

    private static final String TAG = GameActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appmanager.setUnitResolution(AppManager.RESOLUTION_QHD);
        appmanager.setFrameRate(20);
        setDevelopmentMode(true);
        setFreeVersion(true);
        setGPRelease(true);
        Log.i(TAG, "Game starting!");
    }




        @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
    }

}
