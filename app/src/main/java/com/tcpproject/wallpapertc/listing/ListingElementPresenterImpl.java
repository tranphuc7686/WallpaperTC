package com.tcpproject.wallpapertc.listing;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListingElementPresenterImpl implements ListingElementPresenter {
    private ListingElementInteractor listingElementInteractor;
    private ListingElementView listingElementView;
    private Disposable fetchSubscription;

    public ListingElementPresenterImpl(ListingElementInteractor listingElementInteractor) {
        this.listingElementInteractor = listingElementInteractor;
    }

    @Override
    public void getListElementById(int id) {
        listingElementView.loadingStart();
        fetchSubscription = listingElementInteractor.queryListElement(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(elements -> listingElementView.showListElement(elements), t -> listingElementView.loadingFailed(t.getMessage()));


    }

    @Override
    public void setView(ListingElementView view) {
        this.listingElementView = view;
    }

}
