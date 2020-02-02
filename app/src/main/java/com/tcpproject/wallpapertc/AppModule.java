package com.tcpproject.wallpapertc;

import android.app.Activity;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
@Module
public class AppModule {
    private Context context;

    AppModule(Activity activity) {
        context = activity;
    }
    @Provides
    @Singleton
    public Context provideContext() {
        return context;
    }

}
