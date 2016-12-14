package com.example.guest.biinder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.biinder.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.nextButton) Button nextButton;
    @Bind(R.id.dislikeText) TextView mDislikeText;
    @Bind(R.id.likeText) TextView mLikeText;
    @Bind(R.id.statBookImage) ImageView mCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        mLikeText.setText("Likes:  " + intent.getStringExtra("likes"));
        mDislikeText.setText("Dislikes:  "+intent.getStringExtra("dislikes"));
        String url = intent.getStringExtra("image");
        Picasso.with(StatsActivity.this).load(url).resize(1400 , 2050).into(mCover);


        nextButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == nextButton){

            Intent intent = new Intent(StatsActivity.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }


}
