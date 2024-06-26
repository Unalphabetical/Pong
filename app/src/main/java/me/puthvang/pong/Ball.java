package me.puthvang.pong;

import android.graphics.Color;
import android.graphics.RectF;

class Ball {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private final RectF mRect;
    private float mXVelocity;
    private float mYVelocity;
    private final float mBallWidth;
    private final float mBallHeight;

    //// This is the color variable for the ball
    private int color;

    // This is the constructor method.
    // It is called when by the code:
    //  mBall = new Ball(mScreenX);
    // In the PongGame class
    Ball(int screenX) {

        //// The ball is now 2% of the screen width
        //// because it was a bit too small on 1%
        mBallWidth = screenX / 50F;
        mBallHeight = screenX / 50F;

        // Initialize the RectF with 0, 0, 0, 0
        // We do it here because we only want to
        // do it once.
        // WE will initialize the detail
        // at the start of each game
        mRect = new RectF();
    }


    // Return a reference to mRect to PongGame
    RectF getRect() {
        return mRect;
    }

    // Update the ball position.
    // Called each frame/loop
    void update(long fps) {
        // Move the ball based upon the
        // horizontal (mXVelocity) and
        // vertical(mYVelocity) speed
        // and the current frame rate(mFPS)

        // Move the top left corner
        mRect.left = mRect.left + (mXVelocity / fps);
        mRect.top = mRect.top + (mYVelocity / fps);

        // Match up the bottom right corner
        // based on the size of the ball
        mRect.right = mRect.left + mBallWidth;
        mRect.bottom = mRect.top + mBallHeight;
    }

    // Reverse the vertical direction of travel
    void reverseYVelocity() {
        mYVelocity = -mYVelocity;
    }

    // Reverse the horizontal direction of travel
    void reverseXVelocity() {
        mXVelocity = -mXVelocity;
    }

    void reset(int x, int y) {

        // Initialise the four points of
        // the rectangle which defines the ball
        mRect.left = x / 2F;
        mRect.top = 0;
        mRect.right = x / 2F + mBallWidth;
        mRect.bottom = mBallHeight;

        // How fast will the ball travel
        // You could vary this to suit
        // You could even increase it as the game progresses
        // to make it harder
        mYVelocity = -(y / 3F);
        mXVelocity = (y / 3F);
    }

    void increaseVelocity() {

        // Increase the speed by 10%
        mXVelocity = mXVelocity * 1.1f;
        mYVelocity = mYVelocity * 1.1f;
    }

    // Bounce the ball back based upon
    // whether it hits the left or right hand side
    void batBounce(RectF batPosition) {

        // Detect center of bat
        float batCenter = batPosition.left + (batPosition.width() / 2);

        // detect the center of the ball
        float ballCenter = mRect.left + (mBallWidth / 2);

        // Where on the bat did the ball hit?
        float relativeIntersect = (batCenter - ballCenter);

        // Pick a bounce direction
        if (relativeIntersect < 0) {
            // Go right
            mXVelocity = Math.abs(mXVelocity);
            // Math.abs is a static method that
            // strips any negative values from a value.
            // So -1 becomes 1 and 1 stays as 1
        } else {
            // Go left
            mXVelocity = -Math.abs(mXVelocity);
        }

        // Having calculated left or right for
        // horizontal direction simply reverse the
        // vertical direction to go back up
        // the screen
        reverseYVelocity();
    }

    //// Set the color of the ball via rgb values including Alpha
    public void setColor(int alpha, int red, int green, int blue) {
        this.color = Color.argb(alpha, red, green, blue);
    }

    //// Get the color of the bat
    public int getColor() {
        return color;
    }

}
