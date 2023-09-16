package me.puthvang.pong;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class PongActivity extends Activity {
    private PongGame mPongGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mPongGame = new PongGame(this, size.x, size.y);

        //// Create a new View window for the game

        FrameLayout game = new FrameLayout(this);
        game.addView(mPongGame);

        //// Detect the width of the game

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        //// This is to create the Toggle Button since the Game View was not
        //// made via xml files

        ToggleButton muteBgMusicButton = new ToggleButton(this);

        muteBgMusicButton.setMinWidth(0);
        muteBgMusicButton.setMinHeight(0);
        muteBgMusicButton.setMinimumWidth(0);
        muteBgMusicButton.setMinimumHeight(0);

        //// Sets the x and y to the top right of the screen

        muteBgMusicButton.setX(width - 75);
        muteBgMusicButton.setY(-25);
        muteBgMusicButton.setBackgroundColor(Color.TRANSPARENT);
        muteBgMusicButton.setTextColor(Color.BLACK);

        //// Changes the text when enabled to a headset sign
        //// And when disabled to a mute sign
        muteBgMusicButton.setTextOn("\uD83D\uDEAB");
        muteBgMusicButton.setTextOff("\uD83C\uDFA7︎");
        muteBgMusicButton.setChecked(false);

        muteBgMusicButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //// Adds the button to the new View
        game.addView(muteBgMusicButton);

        //// Sets the View to Pong
        setContentView(game);

        //// Adds in a click event for the music toggle button
        muteBgMusicButton.setOnClickListener(view -> {
            muteBgMusicButton.setChecked(muteBgMusicButton.isChecked());

            //// Pause the music if the mute sign is on
            //// Plays the music if the microphone sign is on
            if (muteBgMusicButton.isChecked()) {
                mPongGame.getPongAudio().setBgPaused(true);
                mPongGame.getPongAudio().bgPause();
            } else {
                mPongGame.getPongAudio().setBgPaused(false);
                mPongGame.getPongAudio().bg();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // More code here later in the chapter
        mPongGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // More code here later in the chapter
        mPongGame.pause();
    }
}