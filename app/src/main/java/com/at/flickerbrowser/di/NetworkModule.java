package com.at.flickerbrowser.di;

import com.at.flickerbrowser.App;
import com.at.flickerbrowser.api.FlickrWebService;
import com.at.flickerbrowser.repo.LiveDataCallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @Named("FlickrBaseUrl")
    String provideFlickrBaseUrl() {
        return "https://api-test.allqt.com/";
    }

    @Provides
    @Singleton
    public Cache provideHttpCache(App app) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(app.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkhttpClient(Cache cache, HttpLoggingInterceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.cache(cache).addInterceptor(interceptor);
        return client.build();
    }

    @Provides
    @Singleton
    @Named("FlickrRetrofit")
    public Retrofit provideFlickrRetrofit(@Named("FlickrBaseUrl") String flickrBaseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(flickrBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build();
    }

    @Provides
    @Singleton
    public FlickrWebService provideFlickrWebService(@Named("FlickrRetrofit") Retrofit flickrRetrofit) {
        return flickrRetrofit.newBuilder().build().create(FlickrWebService.class);
    }

}

