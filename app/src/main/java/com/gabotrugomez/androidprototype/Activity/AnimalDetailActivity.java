package com.gabotrugomez.androidprototype.Activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.gabotrugomez.androidprototype.Views.CircleImgTransform;
import com.gabotrugomez.androidprototype.R;
import com.squareup.picasso.Picasso;

public class AnimalDetailActivity extends AppCompatActivity {

    boolean isLayoutOne;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // if "type" is 1, then "isLayoutOne" is true, otherwise false
        isLayoutOne = getIntent().getIntExtra("type", 0) == 0;

        if (isLayoutOne)
        {
            setContentView(R.layout.activity_animal_detail);
        }
        else
        {
            setContentView(R.layout.activity_animal_detail_two);

            ImageView roundedImgView = (ImageView) findViewById(R.id.rounded_img_view_animal_detail_two_act);
            Picasso.with(getApplicationContext())
                    .load("https://media.licdn.com/mpr/mpr/shrink_200_200/AAEAAQAAAAAAAAJ-AAAAJGY5OTk2ZTQyLTg4YWMtNGRhZC04YzRjLTYxMGM5MTBjNGQxNQ.png")
                    .error(R.drawable.img_not_found)
                    .transform(new CircleImgTransform())
                    .into(roundedImgView);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
