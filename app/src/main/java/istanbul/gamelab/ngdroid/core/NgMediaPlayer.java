package istanbul.gamelab.ngdroid.core;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.ngdroidapp.NgApp;

import java.io.IOException;

/**
 * Created by noyan on 14.10.2016.
 * Nitra Games Ltd.
 */

public class NgMediaPlayer {
    private NgApp root;
    private MediaPlayer mediaplayer;

    public NgMediaPlayer(NgApp ngApp) {
        root = ngApp;
//        mediaplayer = new MediaPlayer();
    }

    public void load(int mediaFileResId) {
//        mediaplayer.setDataSource(root.activity, mediaFileResId);
        mediaplayer = MediaPlayer.create(root.activity, mediaFileResId);
    }

    public void load(String assetFilePath) {
        mediaplayer = new MediaPlayer();
        AssetFileDescriptor afd = null;
        try {
            afd = root.activity.getAssets().openFd(assetFilePath);
            mediaplayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(Uri uri) {
        try {
            mediaplayer.setDataSource(root.activity, uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void prepare() {
        try {
            mediaplayer.prepare();
        } catch (IOException e) {}
    }

    public void setLooping(boolean isLooping) {
        mediaplayer.setLooping(isLooping);
    }

    public void start() {
        mediaplayer.start();
    }

    public void pause() {
        mediaplayer.pause();
    }

    public void stop() {
        mediaplayer.stop();
    }

    public boolean isPlaying() {
        return mediaplayer.isPlaying();
    }

    public boolean isLooping() {
        return mediaplayer.isLooping();
    }

    public void reset() {
        mediaplayer.reset();
    }

    public void release() {
        mediaplayer.release();
    }

    public int getCurrentPosition() {
        return mediaplayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaplayer.getDuration();
    }

    public void setVolume(float volumeLevel) {
        mediaplayer.setVolume(volumeLevel, volumeLevel);
    }

    public int getVideoWidth() {
        return mediaplayer.getVideoWidth();
    }

    public int getVideoHeight() {
        return mediaplayer.getVideoHeight();
    }
}
