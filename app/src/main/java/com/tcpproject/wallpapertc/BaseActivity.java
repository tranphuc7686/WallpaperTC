package com.tcpproject.wallpapertc;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.tcpproject.wallpapertc.listing.ListingElementComponent;
import com.tcpproject.wallpapertc.listing.ListingElementModule;
import com.tcpproject.wallpapertc.network.NetworkModule;

public abstract class BaseActivity extends AppCompatActivity {
    private AppComponent appComponent;
    private ListingElementComponent listingElementComponent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appComponent = createAppComponent();
        setContentView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        blindControl();
        blindListener();


    }

    private AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }
    protected ListingElementComponent createListingElementComponent() {
        listingElementComponent = appComponent.plus(new ListingElementModule());
        return listingElementComponent;
    }
    protected void showError(View view,String msg){
        Snackbar.make(view, "Error : "+ msg, Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();
    }
    protected abstract void blindControl();
    protected abstract void setContentView();
    protected abstract void blindListener();
}
