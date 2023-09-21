package me.puthvang.pong;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

        //// Create a RelativeLayout View window for the game
        RelativeLayout game = new RelativeLayout(this);
        game.addView(mPongGame);

        // Width and height detection
        int width = size.x;
        int height = size.y;

        //// This builder is to create the Toggle Button more easily
        //// and make it neater because the Game View was not
        //// made via xml files
        ToggleButton muteBgMusicButton = new CustomToggleButtonBuilder(this)
                .minWidth(0).minHeight(0).minimumWidth(0).minimumHeight(0)
                .textSize(height / 200)
                .x((int) (width / 1.05)).y(-25)
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

        //// This builder is for the mute sound button
        ToggleButton muteSoundButton = new CustomToggleButtonBuilder(this)
                .minWidth(0).minHeight(0).minimumWidth(0).minimumHeight(0)
                .textSize(height / 200)
                .x(width - (width / 20)).y(50)
                .backgroundColor(Color.TRANSPARENT)
                .textColor(Color.WHITE)
                .textOn("\uD83D\uDD08")
                .textOff("\uD83D\uDD0A")
                .checked(false)
                .layoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))
                .build();

        //// Click event listener for the mute sound button
        muteSoundButton.setOnClickListener(view -> {
            muteSoundButton.setChecked(muteSoundButton.isChecked());

            //// Pause the sound if the speaker has no sound wave coming out
            //// Plays the music if the speaker has sound wave coming out
            mPongGame.getPongAudio().setSoundPaused(muteSoundButton.isChecked());
        });

        //// Adds the button to the new View
        game.addView(muteBgMusicButton);
        game.addView(muteSoundButton);

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