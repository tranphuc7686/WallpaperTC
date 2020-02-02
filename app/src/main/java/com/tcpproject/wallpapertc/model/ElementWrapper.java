package com.tcpproject.wallpapertc.model;

import com.squareup.moshi.Json;

import java.util.List;

public class ElementWrapper {
    @Json(name = "resulf")
    private List<Element> elements;

    public List<Element> getElementList() {
        return elements;
    }

    public void setMovieList(List<Element> elements) {
        this.elements = elements;
    }
}
