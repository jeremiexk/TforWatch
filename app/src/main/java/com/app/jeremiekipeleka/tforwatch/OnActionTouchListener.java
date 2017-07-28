package com.app.jeremiekipeleka.tforwatch;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

/**
 * Created by jeremie kipeleka on 15/03/2017.
 */

/*
Usage:
  myView.setOnTouchListener(new OnActionTouchListener(this) {
    @Override
    public void onSwipeDown() {
      Toast.makeText(MainActivity.this, "Down", Toast.LENGTH_SHORT).show();
    }
  }
*/


public class OnActionTouchListener implements View.OnTouchListener
{

    public float VelX2= 0;
    public float VelY2 = 0;

    public float VelX1= 0;
    public float VelY1 = 0;

    float startScale=0.0f;
    float endScale=0.0f;

    private GestureDetector gestureDetector;

    private int mPtrCount = 0;

    private float mPrimStartTouchEventX = -1;
    private float mPrimStartTouchEventY = -1;
    private float mSecStartTouchEventX = -1;
    private float mSecStartTouchEventY = -1;
    private float mPrimSecStartTouchDistance = 0;

    private int mViewScaledTouchSlop = 0;


    public OnActionTouchListener(Context c) {
        gestureDetector = new GestureDetector(c, new GestureListener());
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {



        int action = (motionEvent.getAction() & MotionEvent.ACTION_MASK);

        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                mPtrCount++;
                if (mPtrCount == 1 && mPrimStartTouchEventY == -1 && mPrimStartTouchEventY == -1) {
                    mPrimStartTouchEventX = motionEvent.getX(0);
                    mPrimStartTouchEventY = motionEvent.getY(0);
                    Log.d("TAG", String.format("POINTER ONE X = %.5f, Y = %.5f", mPrimStartTouchEventX, mPrimStartTouchEventY));
                }
                if (mPtrCount == 2) {
                    // Starting distance between fingers
                    mSecStartTouchEventX = motionEvent.getX(1);
                    mSecStartTouchEventY = motionEvent.getY(1);
                    mPrimSecStartTouchDistance = distance(motionEvent, 0, 1);
                    Log.d("TAG", String.format("POINTER TWO X = %.5f, Y = %.5f", mSecStartTouchEventX, mSecStartTouchEventY));
                }

                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                mPtrCount--;
                if (mPtrCount < 2) {
                    mSecStartTouchEventX = -1;
                    mSecStartTouchEventY = -1;
                }
                if (mPtrCount < 1) {
                    mPrimStartTouchEventX = -1;
                    mPrimStartTouchEventY = -1;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                boolean isPrimMoving = isScrollGesture(motionEvent, 0, mPrimStartTouchEventX, mPrimStartTouchEventY);
                boolean isSecMoving = (mPtrCount > 1 && isScrollGesture(motionEvent, 1, mSecStartTouchEventX, mSecStartTouchEventY));

                // There is a chance that the gesture may be a scroll
                if (mPtrCount > 1 && isPinchGesture(motionEvent)) {
                    Log.d("TAG", "PINCH! OUCH!");

                } else if (isPrimMoving || isSecMoving) {
                    // A 1 finger or 2 finger scroll.
                    if (isPrimMoving && isSecMoving) {
                        Log.d("TAG", "Two finger scroll");
                    } else {
                        Log.d("TAG", "One finger scroll");
                    }
                }
                break;
        }


        return gestureDetector.onTouchEvent(motionEvent);



    }




    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        // Determines the fling velocity and then fires the appropriate swipe event accordingly
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                VelX2= e2.getX();
                VelY2 = e2.getY();

                VelX1= e1.getX();
                VelY1 = e1.getY();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeDown();
                        } else {
                            onSwipeUp();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }


            return result;
        }




        @Override
        public boolean onDoubleTap(MotionEvent e) {
            OnActionTouchListener.this.onDoubleTap(e);
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            OnActionTouchListener.this.onSingleTapUp(e);
            return super.onSingleTapUp(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            OnActionTouchListener.this.onSingleTapConfirmed(e);
            return super.onSingleTapConfirmed(e);
        }


    }

    public void onTouch(MotionEvent e) {

    }
    public void onDoubleTap(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onSingleTapUp(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onSingleTapConfirmed(MotionEvent e) {
        // To be overridden when implementing listener
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }



    public float getVelX1() {return this.VelX1;}

    public float getVelY1() {return this.VelY1;}

    public float getVelX2(){return this.VelX2;}

    public float getVelY2() {return VelY2;}


    private boolean isScrollGesture(MotionEvent event, int ptrIndex, float originalX, float originalY){
        float moveX = Math.abs(event.getX(ptrIndex) - originalX);
        float moveY = Math.abs(event.getY(ptrIndex) - originalY);

        if (moveX > mViewScaledTouchSlop || moveY > mViewScaledTouchSlop) {
            return true;
        }
        return false;
    }

    private boolean isPinchGesture(MotionEvent event) {
        if (event.getPointerCount() == 2) {
            final float distanceCurrent = distance(event, 0, 1);
            final float diffPrimX = mPrimStartTouchEventX - event.getX(0);
            final float diffPrimY = mPrimStartTouchEventY - event.getY(0);
            final float diffSecX = mSecStartTouchEventX - event.getX(1);
            final float diffSecY = mSecStartTouchEventY - event.getY(1);

            if (// if the distance between the two fingers has increased past
                // our threshold
                    Math.abs(distanceCurrent - mPrimSecStartTouchDistance) > mViewScaledTouchSlop
                            // and the fingers are moving in opposing directions
                            && (diffPrimY * diffSecY) <= 0
                            && (diffPrimX * diffSecX) <= 0) {
                // mPinchClamp = false; // don't clamp initially
                return true;
            }
        }

        return false;
    }

    private float distance(MotionEvent event, int first, int second) {
        if (event.getPointerCount() >= 2) {
            final float x = event.getX(first) - event.getX(second);
            final float y = event.getY(first) - event.getY(second);

            return (float) Math.sqrt(x * x + y * y);
        } else {
            return 0;
        }
    }



}