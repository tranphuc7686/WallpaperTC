package com.tcpproject.wallpapertc.network;

import com.tcpproject.wallpapertc.model.ElementWrapper;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TmdbWebService {
    @GET("v0/b/light-sleep.appspot.com/o/pet.json?alt=media&token=fb6e824e-a174-4043-9189-e8c9613c0eb4")
    Observable<ElementWrapper> elementsPet();

    @GET("v0/b/light-sleep.appspot.com/o/car.json?alt=media&token=17bcdd89-5434-4bc8-a67e-643e2a6c41f1")
    Observable<ElementWrapper> elementsCar();

    @GET("v0/b/light-sleep.appspot.com/o/food.json?alt=media&token=061a00dd-a25f-4529-b600-d4169d69a12a")
    Observable<ElementWrapper> elementsFood();

    @GET("v0/b/light-sleep.appspot.com/o/movie.json?alt=media&token=b8021de1-2aa4-4e12-8257-5c60c52362c4")
    Observable<ElementWrapper> elementsMovie();
}
