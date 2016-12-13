package com.example.guest.biinder.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.biinder.R;
import com.example.guest.biinder.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookCoverFragment extends Fragment {

    private DatabaseReference mBookReference;
    private Book temp;

    @Bind(R.id.testFragmentTextView) TextView mTestFragmentTextView;
    @Bind(R.id.coverImage) ImageView mCoverImage;

    public BookCoverFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        Bitmap bmp =  BitmapFactory.decodeResource(getResources(),R.drawable.warscapia);//your image
//        ByteArrayOutputStream bYtE = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE);
//        bmp.recycle();
//        byte[] byteArray = bYtE.toByteArray();
//        final String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        mBookReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("allBooks")
                .child("Warscapia");

        mBookReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String title = (String) dataSnapshot.child("title").getValue();
                String author = (String) dataSnapshot.child("author").getValue();
                String picture = (String) dataSnapshot.child("image").getValue();
                long wins = (long) dataSnapshot.child("likes").getValue();
                long losses = (long) dataSnapshot.child("dislikes").getValue();

                temp = new Book(title, author, picture, wins, losses);

                byte[] decodedString = Base64.decode(temp.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                mCoverImage.setImageBitmap(decodedByte);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);
        ButterKnife.bind(this,view);
        Random rnd = new Random();
        String [] words = {"A","B","C","D","E","F"};
        int randomNumber = rnd.nextInt(words.length);

        mTestFragmentTextView.setText(words[randomNumber]);

        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
