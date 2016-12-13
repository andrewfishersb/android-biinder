package com.example.guest.biinder.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.guest.biinder.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.nextButton) Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        ButterKnife.bind(this);

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
