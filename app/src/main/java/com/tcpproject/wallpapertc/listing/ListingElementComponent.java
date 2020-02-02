package com.tcpproject.wallpapertc.listing;

import dagger.Subcomponent;

@ListingElementScope
@Subcomponent(modules = {ListingElementModule.class})
public interface ListingElementComponent {
    void inject(ListingElementActivity target);
}
