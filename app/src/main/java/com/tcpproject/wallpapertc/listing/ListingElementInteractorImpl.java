package com.tcpproject.wallpapertc.listing;

import com.tcpproject.wallpapertc.model.Element;
import com.tcpproject.wallpapertc.model.ElementWrapper;
import com.tcpproject.wallpapertc.network.TmdbWebService;

import java.util.List;

import io.reactivex.Observable;

public class ListingElementInteractorImpl implements ListingElementInteractor {
    private TmdbWebService tmdbWebService;

    public ListingElementInteractorImpl(TmdbWebService tmdbWebService) {
        this.tmdbWebService = tmdbWebService;
    }

    @Override
    public Observable<List<Element>> queryListElement(int ctgId) {


        switch (ctgId) {
            case 1: {
                return tmdbWebService.elementsFood().map(ElementWrapper::getElementList);
            }
            case 2: {
                return tmdbWebService.elementsMovie().map(ElementWrapper::getElementList);
            }
            case 3: {
                return tmdbWebService.elementsCar().map(ElementWrapper::getElementList);
            }
            case 4: {
                return tmdbWebService.elementsPet().map(ElementWrapper::getElementList);
            }
            default: {
                return null;
            }
        }
    }
}
