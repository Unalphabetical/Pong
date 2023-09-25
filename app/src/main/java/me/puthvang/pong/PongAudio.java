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

    private boolean soundPaused = false;

    //// This is for playing the background music

    private MediaPlayer mediaPlayer;

    private boolean bgPaused = false;

    //// The constructor for the PongAudio class
    //// which is required to initialize the sound and background music
    public PongAudio(Context context) {
        this.context = context;

        try {

            //// Prepare the media player for the background music

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

    //// These are the methods to play the sounds
    //// The first one plays the beep sound
    public void beep() {
        mSP.play(mBeepID, 1, 1, 0, 0, 1);
    }

    //// The second one plays the miss sound
    public void miss() {
        mSP.play(mMissID, 1, 1, 0, 0, 1);
    }

    //// The third one plays the boop sound
    public void boop() {
        mSP.play(mBoopID, 1, 1, 0, 0, 1);
    }

    //// The fourth one plays the bop sound
    public void bop() {
        mSP.play(mBopID, 1, 1, 0, 0, 1);
    }

    //// The fifth one plays the background music
    public void bg() {
        this.mediaPlayer.start();
    }

    //// This method pauses the media player
    public void bgPause() {
        this.mediaPlayer.pause();
    }

    //// This method pauses the background music
    //// It's required in order to show the mute icon on the toggle button correctly
    public void setBgPaused(boolean bgPaused) {
        this.bgPaused = bgPaused;
    }

    //// This method checks if the background music is paused
    //// This is required to show the unmute icon on the toggle button correctly
    public boolean isBgPaused(){
        return bgPaused;
    }

    //// This method pauses the sound
    public void setSoundPaused(boolean soundPaused) {
        this.soundPaused = soundPaused;
    }

    //// This method checks if the sound is paused
    //// This is required to show the unmute icon on the toggle button correctly
    public boolean isSoundPaused() {
        return soundPaused;
    }

}
