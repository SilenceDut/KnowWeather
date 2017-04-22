package com.silencedut.knowweather.scheduler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface TaskCallback{

    abstract class Callback<R,E> {
        public Type rClass = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        abstract void onSuccess(R response);
        abstract void onError(E error);
    }

    abstract class Success<R> {
        public Type rClass = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        public abstract void onSuccess(R response);
    }

}