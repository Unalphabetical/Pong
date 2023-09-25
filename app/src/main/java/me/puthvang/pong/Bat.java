package me.puthvang.pong;

import android.graphics.Color;
import android.graphics.RectF;

class Bat {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private final RectF mRect;
    private final float mLength;
    private float mXCoord;
    private final float mBatSpeed;
    private final int mScreenX;

    //// This is the color variable for the bat
    private int color;

    // These variables are public and final
    // They can be directly accessed by
    // the instance (in PongGame)
    // because they are part of the same
    // package but cannot be changed
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    // Keeps track of if an how the ball is moving
    // Starting with STOPPED
    private int mBatMoving = STOPPED;

    Bat(int sx, int sy) {

        // Bat needs to know the screen
        // horizontal resolution
        // Outside of this method
        mScreenX = sx;

        // Configure the size of the bat based on
        // the screen resolution

        // One eighth the screen width
        mLength = mScreenX / 8F;

        // One fortieth the screen height
        float height = sy / 40F;

        // Configure the starting locaion of the bat
        // Roughly the middle horizontally
        mXCoord = mScreenX / 2F;

        // The height of the bat
        // off of the bottom of the screen
        float mYCoord = sy - height;

        // Initialize mRect based on the size and position
        mRect = new RectF(mXCoord, mYCoord,
                mXCoord + mLength,
                mYCoord + height);

        // Configure the speed of the bat
        // This code means the bat can cover the
        // width of the screen in 1 second
        mBatSpeed = mScreenX;
    }

    // Return a reference to the mRect object
    RectF getRect() {
        return mRect;
    }

    // Update the movement state passed
    // in by the onTouchEvent method
    void setMovementState(int state) {
        mBatMoving = state;
    }


    // Update the bat- Called each frame/loop
    void update(long fps) {

        // Move the bat based on the mBatMoving variable
        // and the speed of the previous frame
        if(mBatMoving == LEFT) {
            mXCoord = mXCoord - mBatSpeed / fps;
        }

        if(mBatMoving == RIGHT) {
            mXCoord = mXCoord + mBatSpeed / fps;
        }

        // Stop the bat going off the left side of the screen
        if(mXCoord < 0) {
            mXCoord = 0;
        }

        //// Stop the bat going off the right side of the screen
        if(mXCoord + mLength > mScreenX) {
            mXCoord = mScreenX - mLength;
        }

        // Update mRect based on the results from
        // the previous code in update
        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }

    //// Set the color of the bat via rgb values including Alpha
    public void setColor(int alpha, int red, int green, int blue) {
        this.color = Color.argb(alpha, red, green, blue);
    }

    //// Get the color of the bat
    public int getColor() {
        return color;
    }

}
