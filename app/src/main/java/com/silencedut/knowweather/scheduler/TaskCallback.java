package com.silencedut.knowweather.scheduler;

public interface TaskCallback{

    interface Callback<R,E> {
        void onSuccess(R response);
        void onError(E error);
    }

    interface Success<R> {
        void onSuccess(R response);
    }

}