package com.example.guest.biinder.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.guest.biinder.R;
import com.example.guest.biinder.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookCoverFragment extends Fragment {
    private String [] bookNames = {"Warscapia" , "Eragon" , "Last Horizon", "Ready Player One"};

    private DatabaseReference mBookReference;
    public Book temp;
    private Context mContext;
    @Bind(R.id.coverImage) ImageView mCoverImage;

    public BookCoverFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //did this the opposite ???
        temp = ((MainActivity)this.getActivity()).getBook();

        // Inflate the layout for this fragment
        mContext = getContext();
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

                temp = new Book(title, author, picture, wins, losses);
                Picasso.with(mContext).load(temp.getImage()).resize(1400 , 2050).into(mCoverImage);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);
        ButterKnife.bind(this,view);

        return view;
    }




}
