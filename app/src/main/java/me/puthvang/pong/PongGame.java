package me.puthvang.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class PongGame extends SurfaceView implements Runnable{

    // Are we debugging?
    private final boolean DEBUGGING = true;

    // These objects are needed to do the drawing
    private SurfaceHolder mOurHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    // How many frames per second did we get?
    private long mFPS;
    // The number of milliseconds in a second
    private final int MILLIS_IN_SECOND = 1000;

    // Holds the resolution of the screen
    private int mScreenX;
    private int mScreenY;
    // How big will the text be?
    private int mFontSize;
    private int mFontMargin;

    // The game objects
    private Bat mBat;
    private Ball mBall;

    // The current score and lives remaining
    private int mScore = 0;
    private int mLives = 3;

    // Here is the Thread and two control variables
    private Thread mGameThread = null;
    // This volatile variable can be accessed
    // from inside and outside the thread
    private volatile boolean mPlaying;
    private boolean mPaused = true;

    // This PongAudio class is for playing audio
    private PongAudio pongAudio;

    // The PongGame constructor
    // Called when this line:
    // mPongGame = new PongGame(this, size.x, size.y);
    // is executed from PongActivity
    public PongGame(Context context, int x, int y) {
        // Super... calls the parent class
        // constructor of SurfaceView
        // provided by Android
        super(context);

        // Initialize these two members/fields
        // With the values passesd in as parameters
        mScreenX = x;
        mScreenY = y;

        // Font is 5% (1/20th) of screen width
        mFontSize = mScreenX / 20;
        // Margin is 1.5% (1/75th) of screen width
        mFontMargin = mScreenX / 75;

        //// Initialize the objects that we have to use
        //// i.e. the paint, ball, bat, and audio
        initializeObjects();

        // Everything is ready so start the game
        startNewGame();
    }

    //// This method initializes the objects that we have to use
    //// such as the paint, ball, bat, and audio.
    public void initializeObjects(){
        // Initialize the objects
        // ready for drawing with
        // getHolder is a method of SurfaceView
        mOurHolder = getHolder();
        mPaint = new Paint();

        // Initialize the ball and bat
        mBall = new Ball(mScreenX);
        mBat = new Bat(mScreenX, mScreenY);

        //// Color the ball and bat
        mBall.setColor(255, 25, 235, 150);
        mBat.setColor(255, 255, 255, 255);

        //// Initialize the PongAudio class
        pongAudio = new PongAudio(getContext());
    }

    // The player has just lost
    // or is starting their first game
    private void startNewGame(){

        // Put the ball back to the starting position
        mBall.reset(mScreenX, mScreenY);

        // Rest the score and the player's chances
        mScore = 0;
        mLives = 3;

    }

    // When we start the thread with:
    // mGameThread.start();
    // the run method is continuously called by Android
    // because we implemented the Runnable interface
    // Calling mGameThread.join();
    // will stop the thread
    @Override
    public void run() {
        // mPlaying gives us finer control
        // rather than just relying on the calls to run
        // mPlaying must be true AND
        // the thread running for the main loop to execute
        while (mPlaying) {

            // What time is it now at the start of the loop?
            long frameStartTime = System.currentTimeMillis();

            // Provided the game isn't paused call the update method
            if(!mPaused){
                update();
                // Now the bat and ball are in their new positions
                // we can see if there have been any collisions
                detectCollision();
            }

            // The movement has been handled and collisions
            // detected now we can draw the scene.
            draw();

            // How long did this frame/loop take?
            // Store the answer in timeThisFrame
            long timeThisFrame = System.currentTimeMillis() - frameStartTime;

            // Make sure timeThisFrame is at least 1 millisecond
            // because accidentally dividing by zero crashes the game
            if (timeThisFrame > 0) {
                // Store the current frame rate in mFPS
                // ready to pass to the update methods of
                // mBat and mBall next frame/loop
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }

        }

    }

    private void update() {
        // Update the bat and the ball
        mBall.update(mFPS);
        mBat.update(mFPS);
    }

    //// This method detects whether the ball has collided with the bat
    //// After that, it calls the method to detect
    //// if the ball collided with the wall
    private void detectCollision(){
        detectBatCollision();
        detectWallCollision();
    }

    //// This method detects if the bat has collided with the ball
    //// These methods are standalone to enable detecting them manually
    private void detectBatCollision(){
        // Has the bat hit the ball?
        if(RectF.intersects(mBat.getRect(), mBall.getRect())) {
            // Realistic-ish bounce
            mBall.batBounce(mBat.getRect());
            mBall.increaseVelocity();
            mScore++;
            if (!pongAudio.isSoundPaused()) pongAudio.beep();
        }
    }

    //// This method detects if the ball has collided with the wall
    //// It has been moved out of the method above to look cleaner
    //// These methods are standalone to enable detecting them manually
    private void detectWallCollision(){
        // Has the ball hit the edge of the screen

        // Bottom
        if(mBall.getRect().bottom > mScreenY){
            mBall.reverseYVelocity();

            mLives--;

            if (!pongAudio.isSoundPaused()) pongAudio.miss();

            if(mLives == 0){
                mPaused = true;
                startNewGame();
            }
        }

        // Top
        if(mBall.getRect().top < 0){
            mBall.reverseYVelocity();
            if (!pongAudio.isSoundPaused()) pongAudio.boop();
        }

        // Left
        if(mBall.getRect().left < 0){
            mBall.reverseXVelocity();
            if (!pongAudio.isSoundPaused()) pongAudio.bop();
        }

        // Right
        if(mBall.getRect().right > mScreenX){
            mBall.reverseXVelocity();
            if (!pongAudio.isSoundPaused()) pongAudio.bop();
        }
    }

    //// Draw the game objects and the HUD
    void draw() {

        if (mOurHolder.getSurface().isValid()) {
            // Lock the canvas (graphics memory) ready to draw
            mCanvas = mOurHolder.lockCanvas();

            // Fill the screen with a solid color
            mCanvas.drawColor(Color.argb(255, 26, 128, 182));

            //// Draw the game objects like the ball and bat
            drawGameObjects();

            //// Draw the HUD text like the score and lives
            drawHUDText();

            //// Draw the debugging text if debugging is turned on
            if(DEBUGGING) drawDebuggingText();

            // Display the drawing on screen
            // unlockCanvasAndPost is a method of SurfaceView
            mOurHolder.unlockCanvasAndPost(mCanvas);
        }

    }

    //// This method draw and colors the required Game Objects
    //// which are the ball and the bat.
    public void drawGameObjects(){
        //// Color the ball and draw the ball
        mPaint.setColor(mBall.getColor());
        mCanvas.drawRect(mBall.getRect(), mPaint);

        //// Color the bat and draw the bat
        mPaint.setColor(mBat.getColor());
        mCanvas.drawRect(mBat.getRect(), mPaint);
    }

    //// This method draw and colors the required HUD Text
    //// which are the score, lives, music, and sound.
    public void drawHUDText(){
        //// Color the text
        mPaint.setColor(Color.argb(255, 255, 255, 255));

        // Choose the font size
        mPaint.setTextSize(mFontSize);

        // Draw the HUD
        mCanvas.drawText("Score: " + mScore + "   Lives: " + mLives,
                mFontMargin, mFontSize, mPaint);

        //// Draw the music text
        mCanvas.drawText("Music: ",
                getWidth() / 1.275F, mFontSize, mPaint);

        //// Draw the music text
        mCanvas.drawText("Sound: ",
                getWidth() / 1.283F, mFontSize * 2, mPaint);
    }

    //// This method draw and colors the required Debugging Text
    //// which is the FPS.
    //// This method is only called if we are debugging.
    private void drawDebuggingText(){
        int debugSize = mFontSize / 2;
        int debugStart = 150;
        mPaint.setTextSize(debugSize);
        mCanvas.drawText("FPS: " + mFPS ,
                10, debugStart + debugSize, mPaint);
    }

    // Handle all the screen touches
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // This switch block replaces the
        // if statement from the Sub Hunter game
        switch (motionEvent.getAction() &
                MotionEvent.ACTION_MASK) {

            // The player has put their finger on the screen
            case MotionEvent.ACTION_DOWN:

                // If the game was paused unpause
                mPaused = false;

                // Where did the touch happen
                if(motionEvent.getX() > mScreenX / 2){
                    // On the right hand side
                    mBat.setMovementState(mBat.RIGHT);
                }
                else{
                    // On the left hand side
                    mBat.setMovementState(mBat.LEFT);
                }

                break;

            // The player lifted their finger
            // from anywhere on screen.
            // It is possible to create bugs by using
            // multiple fingers. We will use more
            // complicated and robust touch handling
            // in later projects
            case MotionEvent.ACTION_UP:

                // Stop the bat moving
                mBat.setMovementState(mBat.STOPPED);
                break;
        }
        return true;
    }

    // This method is called by PongActivity
    // when the player quits the game
    public void pause() {

        // Set mPlaying to false
        // Stopping the thread isn't
        // always instant
        mPlaying = false;
        try {
            // Stop the thread
            mGameThread.join();

            //// Pause the background music
            if (!pongAudio.isBgPaused()) pongAudio.bgPause();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }


    // This method is called by PongActivity
    // when the player starts the game
    public void resume() {
        mPlaying = true;
        // Initialize the instance of Thread
        mGameThread = new Thread(this);

        // Start the thread
        mGameThread.start();

        //// Unpause the background music
        if (!pongAudio.isBgPaused()) pongAudio.bg();
    }

    public PongAudio getPongAudio() {
        return this.pongAudio;
    }

}
