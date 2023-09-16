package me.puthvang.pong;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

public class PongAudio {

    //// This is the context instance from the PongGame class
    Context context;

    // All these are for playing sounds
    private SoundPool mSP;
    private int mBeepID = -1;
    private int mBoopID = -1;
    private int mBopID = -1;
    private int mMissID = -1;

    // This is for playing the background music

    private MediaPlayer mediaPlayer;

    public PongAudio(Context context) {
        this.context = context;

        try {

            this.mediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd = context.getAssets().openFd("The_Infinite_Race.ogg");
            this.mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            afd.close();
            this.mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            this.mediaPlayer.prepare();
            this.mediaPlayer.setVolume(1f, 1f);
            this.mediaPlayer.setLooping(true);

            if(!this.mediaPlayer.isPlaying()) {
                bg();
            }

            // Prepare the SoundPool instance
            // Depending upon the version of Android

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build();

                mSP = new SoundPool.Builder()
                        .setMaxStreams(10)
                        .setAudioAttributes(audioAttributes)
                        .build();
            } else mSP = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

            // Open each of the sound files in turn
            // and load them in to Ram ready to play
            // The try-catch blocks handle when this fails
            // and is required.

            try {
                AssetManager assetManager = this.context.getAssets();
                AssetFileDescriptor descriptor;

                descriptor = assetManager.openFd("beep.ogg");
                mBeepID = mSP.load(descriptor, 0);

                descriptor = assetManager.openFd("boop.ogg");
                mBoopID = mSP.load(descriptor, 0);

                descriptor = assetManager.openFd("bop.ogg");
                mBopID = mSP.load(descriptor, 0);

                descriptor = assetManager.openFd("miss.ogg");
                mMissID = mSP.load(descriptor, 0);
            } catch (IOException e) {
                Log.e("error", "failed to load sound files");
            }

        } catch (IOException e) {
            Log.e("error", "failed to load background music files");
        }

    }

    public void beep() {
        mSP.play(mBeepID, 1, 1, 0, 0, 1);
    }

    public void miss() {
        mSP.play(mMissID, 1, 1, 0, 0, 1);
    }

    public void boop() {
        mSP.play(mBoopID, 1, 1, 0, 0, 1);
    }

    public void bop() {
        mSP.play(mBopID, 1, 1, 0, 0, 1);
    }

    public void bg() {
        this.mediaPlayer.start();
    }

    public void bgPause() {
        this.mediaPlayer.pause();
    }

}
