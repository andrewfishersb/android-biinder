package com.example.guest.biinder.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.guest.biinder.R;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookCoverFragment extends Fragment {
    @Bind(R.id.testFragmentTextView) TextView mTestFragmentTextView;
    public BookCoverFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);
        ButterKnife.bind(this,view);
        Random rnd = new Random();
        String [] words = {"A","B","C","D","E","F"};
        int randomNumber = rnd.nextInt(words.length);

        mTestFragmentTextView.setText(words[randomNumber]);

        return view;
    }

}
