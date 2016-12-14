package com.example.guest.biinder.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.guest.biinder.R;
import com.example.guest.biinder.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private GestureDetectorCompat mDetector;
    private boolean change = true;
    private DatabaseReference mBookReference;

    Context mContext = getBaseContext();

    @Bind(R.id.coverImage) ImageView mCoverImage;

    private String [] bookNames = {"Warscapia" , "Eragon" , "Last Horizon", "Ready Player One"};
    Book chosenBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        change = true;

        ButterKnife.bind(this);

        //previsouly in fragment
        Random rnd = new Random();
        int guess = rnd.nextInt(bookNames.length);

        mBookReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allBooks")
                .child(bookNames[guess]);

        mBookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String title = (String) dataSnapshot.child("title").getValue();
                String author = (String) dataSnapshot.child("author").getValue();
                String picture = (String) dataSnapshot.child("image").getValue();
                long wins = (long) dataSnapshot.child("likes").getValue();
                long losses = (long) dataSnapshot.child("dislikes").getValue();
                chosenBook = new Book(title, author, picture, wins, losses);

                Picasso.with(MainActivity.this).load(chosenBook.getImage()).resize(1400 , 2050).into(mCoverImage);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);

        if(mCoverImage.getScrollX() < -1000 && change ){
            change = false;
            Intent intent = new Intent(MainActivity.this,StatsActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
            chosenBook.addLike();
            mBookReference.child("likes").setValue(chosenBook.getLikes());
            startActivity(intent);
            //may cause future problems
            finish();
        }
        else if(mCoverImage.getScrollX() > 1000 && change ){
            change = false;
            Intent intent = new Intent(MainActivity.this,StatsActivity.class);
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK);
            chosenBook.addDislike();
            mBookReference.child("dislikes").setValue(chosenBook.getDislikes());
            Log.d("Dislikes",Long.toString(chosenBook.getDislikes()));
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
            return true;
        }


        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            int velX = (int) (velocityX);
            int velY = (int) (velocityY);
            mCoverImage.scrollBy(velX, velY);

            return true;

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
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
                    mCoverImage.scrollBy(velX, velY);

                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mCoverImage.scrollBy(velX, velY);
                }


            } catch (Exception e) {
                // nothing
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }


    }
//    public Book getBook(){
//        return chosenBook;
//    }


}
