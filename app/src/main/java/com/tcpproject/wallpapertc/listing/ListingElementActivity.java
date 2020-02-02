package com.tcpproject.wallpapertc.listing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;
import com.r0adkll.slidr.model.SlidrInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.tcpproject.wallpapertc.BaseActivity;
import com.tcpproject.wallpapertc.Constants;
import com.tcpproject.wallpapertc.R;
import com.tcpproject.wallpapertc.model.Category;
import com.tcpproject.wallpapertc.model.Element;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.inject.Inject;

public class ListingElementActivity extends BaseActivity implements ListingElementAdapter.Callback, ListingElementView {
    private RecyclerView recyclerView;
    private ImageView imgEleContent;
    private ListingElementAdapter listingElementAdapter;
    private ImageView btnBack, btnSetWallpaper;
    // reques code to change wallpaper activity
    private static final int REQUEST_CHANGE_WALLPAPER = 0x9345;
    private Element element;
    private String pathImageToChangeWallpater;
    private Bitmap contentBitmap;
    //
    @Inject
    ListingElementPresenter listingElementPresenter;
    //
    private View view;
    private ProgressBar processLoading;
    private SlidrInterface slidr;

    //ads
    private RewardedAd rewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAds();

    }

    @Override
    protected void blindControl() {
        recyclerView = findViewById(R.id.listElement);
        imgEleContent = findViewById(R.id.imgEleContent);
        processLoading = findViewById(R.id.progressLoading);
        listingElementPresenter.setView(this);
        listingElementPresenter.getListElementById(getIntentRedirct().getId());
        btnBack = findViewById(R.id.btnBack);
        btnSetWallpaper = findViewById(R.id.btnSetWallpaper);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManagerCenter
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerCenter);
        recyclerView.setAdapter(listingElementAdapter);
        SnapHelper snapHelperCenter = new LinearSnapHelper();
        snapHelperCenter.attachToRecyclerView(recyclerView);

    }
    private Category getIntentRedirct(){
        Bundle extras = getIntent().getExtras();
        return extras.getParcelable(Constants.CATEGORY);
    }
    @Override
    protected void setContentView() {

        setContentView(R.layout.listing_element_main);

//        // swipe left to back activity
//        slidr = Slidr.attach(this);
        view = findViewById(android.R.id.content);
        createListingElementComponent().inject(this);
    }

    @Override
    protected void blindListener() {
        Activity activityContext = this;
        btnBack.setOnClickListener(view -> finish());
        btnSetWallpaper.setOnClickListener(new View.OnClickListener() {
            boolean isEarn = false;

            @Override
            public void onClick(View view) {

                if (rewardedAd.isLoaded()) {

                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            if(!isEarn){
                                Snackbar.make(view, "Set wallpaper failed !! ", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("DISMISS", v -> {
                                        }).show();

                            }
                            // Ad closed.
                            rewardedAd = createAndLoadRewardedAd() ;
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            isEarn = true;
                            // User earned reward.
                            setWallpaper();
                        }

                        @Override
                        public void onRewardedAdFailedToShow(int errorCode) {
                            // Ad failed to display
                        }
                    };
                    rewardedAd.show(activityContext, adCallback);
                } else {
                    Log.d("TAG", "The rewarded ad wasn't loaded yet.");
                }
            }
        });
    }

    private void setWallpaper() {
        Uri image = getImageUri(this, contentBitmap);
        //Start Crop Activity
        Intent intent = new Intent(Intent.ACTION_ATTACH_DATA);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //can't use normal URI, because it requires the Uri from file
        intent.setDataAndType(image, "image/*");
        intent.putExtra("mimeType", "image/*");
        startActivityForResult(Intent.createChooser(intent, "Set Image"), REQUEST_CHANGE_WALLPAPER);
    }

    private void setContentImage(String url) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                contentBitmap = bitmap;
                imgEleContent.setImageBitmap(contentBitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }


            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                System.out.println("onPrepareLoad");
            }
        };
        imgEleContent.setTag(target);
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.image_holder_content)
                .into(target);
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        pathImageToChangeWallpater = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);

        return Uri.parse(pathImageToChangeWallpater);
    }


    @Override
    public void onClickItem(Element element) {
        this.element = element;
        setContentImage(element.getUrl());
    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_right);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void showListElement(List<Element> elements) {
        processLoading.setVisibility(View.GONE);
        listingElementAdapter = new ListingElementAdapter(elements, this);
        initRecyclerView();
        runLayoutAnimation(recyclerView);

        if (elements.size() > 0) {
            element = elements.get(0);
            setContentImage(element.getUrl());
        }

    }

    @Override
    public void loadingStart() {
        processLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadingFailed(String msg) {
        processLoading.setVisibility(View.GONE);
        showError(view, msg);
    }
    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                Constants.TRATHUONG_ADS);
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }
    private void addAds() {
        MobileAds.initialize(this,Constants.APP_ADS);
        this.rewardedAd = createAndLoadRewardedAd();
    }
}
