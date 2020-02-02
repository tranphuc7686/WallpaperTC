package com.tcpproject.wallpapertc.listing;

import com.tcpproject.wallpapertc.model.Element;

import java.util.List;

import io.reactivex.Observable;

public interface ListingElementInteractor {
    Observable<List<Element>> queryListElement(int ctgId);
}
