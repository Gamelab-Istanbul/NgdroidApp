package istanbul.gamelab.ngdroid.core;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import istanbul.gamelab.ngdroid.base.BaseApp;

/**
 * Created by noyan on 13.10.2016.
 * Nitra Games Ltd.
 */

public class SoundManager {
    private SoundPool soundpool;
    private BaseApp root;
    private boolean ismastersoundon;

    @SuppressLint("NewApi")
    public SoundManager(BaseApp baseApp) {
        root = baseApp;
        ismastersoundon = true;

        try {
            SoundPool.Builder spb = new SoundPool.Builder()
                    .setMaxStreams(8)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    );
            soundpool = spb.build();
        } catch (NoClassDefFoundError e) {
            soundpool = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void setMasterSoundOn(boolean isMasterSoundOn) {
        ismastersoundon = isMasterSoundOn;
    }

    public boolean isMasterSoundOn() {
        return ismastersoundon;
    }

    public int load(String soundFilePath) throws Exception {
        return soundpool.load(root.activity.getAssets().openFd(soundFilePath), 1);
    }

    public int load(int soundFileResId) throws Exception {
        return soundpool.load(root.activity, soundFileResId, 1);
    }

    public int play(int soundId) {
        if (ismastersoundon) return soundpool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
        return 0;
    }

    public void setVolume(int streamId, float leftVolume, float rightVolume) {
        soundpool.setVolume(streamId, leftVolume, rightVolume);
    }
}
