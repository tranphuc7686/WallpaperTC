package com.tcpproject.wallpapertc;

import com.tcpproject.wallpapertc.listing.ListingElementComponent;
import com.tcpproject.wallpapertc.listing.ListingElementModule;
import com.tcpproject.wallpapertc.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class})
public interface  AppComponent {
    ListingElementComponent plus(ListingElementModule listingElementModule);

}
