package com.mobileapp.localnews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import com.mobileapp.localnews.data.NewsPojo;
import com.google.android.gms.ads.AdRequest;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {



    TextView movieTitle;
    ImageView poster_icon;//iv
    TextView  desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);

        NewsPojo.Inner data= (NewsPojo.Inner) getIntent().getSerializableExtra("movieData");

//        Logger.log("click data::"+moviesData.getOriginal_title());

        initWidgets();
        setDataToWidgets(data);

        showAds();

    }

    private void initWidgets(){

        movieTitle= (TextView) findViewById(R.id.movieTitle);
        poster_icon= (ImageView) findViewById(R.id.poster_icon);//iv
        desc= (TextView) findViewById(R.id.desc);




//        markAsFav.setBackgroundColor(getResources().getColor(R.color.movieTitleBg));
    }
    private void setDataToWidgets( NewsPojo.Inner moviesData){

        Picasso.with(this).load( moviesData.getUrlToImage())
                .placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(poster_icon);

        movieTitle.setText(moviesData.getTitle());
        desc.setText( moviesData.getDescription());

    }

    private  void showAds(){
        AdView mAdView = (AdView) findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
