package com.example.guest.biinder.ui;

import android.content.Intent;
import android.graphics.Point;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.example.guest.biinder.R;

public class MainActivity extends AppCompatActivity{

    private GestureDetectorCompat mDetector;
    private View myView;
    private boolean change = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView = findViewById(R.id.fragmentBookCover);
        change = true;

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);

        if(myView.getScrollX() < -1000 && change ){
            change = false;
            Log.d("Triggered" , "THIS RAN!");
            Intent intent = new Intent(MainActivity.this,Test.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //may cause future problems
            finish();
        }
        else if(myView.getScrollX() > 1000 && change ){
            change = false;
            Log.d("Triggered" , "THIS RAN!");
            Intent intent = new Intent(MainActivity.this,Test.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //may cause future problems
            finish();
        }

        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }


        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            int velX = (int) (velocityX);
            int velY = (int) (velocityY);
            myView.scrollBy(velX, velY);
            Log.d("View x", Float.toString(myView.getScrollX()));

            return true;

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            Log.i("FLING!", "onFling has been called!");
            final int SWIPE_MIN_DISTANCE = 120;
            final int SWIPE_MAX_OFF_PATH = 250;
            final int SWIPE_THRESHOLD_VELOCITY = 200;
            int velX = (int) (velocityX * -0.05);
            int velY = (int) (velocityY * -0.05);
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i("Right!", "Right to Left");
                    myView.scrollBy(velX, velY);

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.i("Left!", "Left to Right");
                    myView.scrollBy(velX, velY);
                }


            } catch (Exception e) {
                // nothing
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }


    }

}
