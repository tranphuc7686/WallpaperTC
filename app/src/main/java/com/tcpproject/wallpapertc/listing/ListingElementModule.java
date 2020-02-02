package com.tcpproject.wallpapertc.listing;

import com.tcpproject.wallpapertc.network.TmdbWebService;

import dagger.Module;
import dagger.Provides;

@Module
public class ListingElementModule {

    @Provides
    @ListingElementScope
    ListingElementInteractor providesListingElementInteractor(TmdbWebService tmdbWebService){
        return new ListingElementInteractorImpl(tmdbWebService);
    }
    @Provides
    @ListingElementScope
    ListingElementPresenter providesListingElementPresenter(ListingElementInteractor listingElementInteractor){
        return new ListingElementPresenterImpl(listingElementInteractor);
    }
}
