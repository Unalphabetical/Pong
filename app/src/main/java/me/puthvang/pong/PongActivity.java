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

        //// This builder is to create the Toggle Button more easily
        //// and make it neater because the Game View was not
        //// made via xml files

        ToggleButton muteBgMusicButton = new CustomToggleButtonBuilder(this)
                .minWidth(0).minHeight(0).minimumWidth(0).minimumHeight(0)
                .x(width - 75).y(-25)
                .backgroundColor(Color.TRANSPARENT)
                .textColor(Color.BLACK)
                .textOn("\uD83D\uDEAB")
                .textOff("\uD83C\uDFA7︎")
                .checked(false)
                .layoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .build();

        //// Click event listener for the toggle button
        //// Sadly could not do it within the builder

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

        //// Adds the button to the new View
        game.addView(muteBgMusicButton);

        //// Sets the View to Pong
        setContentView(game);

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