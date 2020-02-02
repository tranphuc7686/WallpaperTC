package com.tcpproject.wallpapertc.listing;

import com.tcpproject.wallpapertc.model.Element;

import java.util.List;

public interface ListingElementView {
    void showListElement(List<Element> elements);
    void loadingStart();
    void loadingFailed(String msg);
}
