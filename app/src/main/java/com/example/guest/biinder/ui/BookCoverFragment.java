package com.example.guest.biinder.ui;

import android.app.Activity;
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

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookCoverFragment extends Fragment {


    Book temp;
//    private  Book temp = new Book("Harry Potter","JK","https://upload.wikimedia.org/wikipedia/en/4/44/HarryPotter5poster.jpg",5,4);
    private Context mContext;
    @Bind(R.id.coverImage) ImageView mCoverImage;


    public BookCoverFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mContext = getContext();

        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);
        ButterKnife.bind(this,view);

        return view;
    }

}
