package com.silencedut.weather_core.corebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.silencedut.weather_core.corebase.StatusDataResource.Status.CACHE_HIT;
import static com.silencedut.weather_core.corebase.StatusDataResource.Status.ERROR;
import static com.silencedut.weather_core.corebase.StatusDataResource.Status.LOADING;
import static com.silencedut.weather_core.corebase.StatusDataResource.Status.SUCCESS;

//a generic class that describes a data with a status
public class StatusDataResource<T> {

    public enum  Status {
        SUCCESS,ERROR,LOADING,CACHE_HIT
    }

    @NonNull
    public final Status status;
    @Nullable public T data;
    @Nullable public final String message;
    private StatusDataResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> StatusDataResource<T> success(@NonNull T data) {
        return new StatusDataResource<>(SUCCESS, data, null);
    }

    public static <T> StatusDataResource<T> error(String msg) {
        return new StatusDataResource<>(ERROR, null, msg);
    }

    public static <T> StatusDataResource<T> loading() {
        return new StatusDataResource<>(LOADING, null, null);
    }

    public static <T> StatusDataResource<T> cacheHit(@NonNull T data) {
        return new StatusDataResource<>(CACHE_HIT, data, null);
    }

    public  boolean isSucceed( ) {
        return Status.SUCCESS.equals(status) ;
    }

}
