package com.tcpproject.wallpapertc.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.tcpproject.wallpapertc.BaseActivity;
import com.tcpproject.wallpapertc.Constants;
import com.tcpproject.wallpapertc.R;
import com.tcpproject.wallpapertc.listing.ListingElementActivity;
import com.tcpproject.wallpapertc.model.Category;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainListAdapter.Callback {
    private ArrayList<Category> categories ;
    private RecyclerView recyclerView;
    private MainListAdapter mainListAdapter ;
    private InterstitialAd mInterstitialAd;
    private Category category;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void setUpAds(){
        MobileAds.initialize(this, initializationStatus -> {});
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(Constants.XENKE_ADS);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }
    @Override
    protected void blindControl() {
        view = findViewById(android.R.id.content);
        recyclerView = findViewById(R.id.listCategory);
        createDataCategory();
        mainListAdapter = new MainListAdapter(categories,this);
        initRecyclerView();
        runLayoutAnimation(recyclerView);
    }
    private void initRecyclerView(){
        recyclerView.setAdapter(mainListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createDataCategory(){
        categories = new ArrayList<>();
        categories.add(new Category(1,"Food","https://img4.goodfon.com/wallpaper/nbig/4/3c/luk-bulochka-kotleta-gamburger-pomidor-sous-fast-food-hambur.jpg"));
        categories.add(new Category(2,"Movie","https://www.4kwallpaperhd.com/wp-content/uploads/2019/05/Godzilla-King-of-the-Monsters-4K-Wallpaper-HD-3840x2160.jpg"));
        categories.add(new Category(3,"Car","https://wallpaperaccess.com/full/33115.jpg"));
        categories.add(new Category(4,"Pet","https://www.tokkoro.com/picsup/5660400-husky-wallpapers.jpg"));
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
        setUpAds();
    }

    @Override
    protected void blindListener() {
        Activity activity = this;
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Intent intent = new Intent(activity, ListingElementActivity.class);
                Bundle extras = new Bundle();
                extras.putParcelable(Constants.CATEGORY, category);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }
    private void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onClickItem(Category category) {
        this.category = category;
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            Intent intent = new Intent(this, ListingElementActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(Constants.CATEGORY, category);
            intent.putExtras(extras);
            startActivity(intent);
        }

    }
}
