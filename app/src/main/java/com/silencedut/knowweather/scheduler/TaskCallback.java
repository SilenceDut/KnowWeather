package com.silencedut.knowweather.scheduler;

import com.silencedut.knowweather.scheduler.exception.ErrorBundle;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public interface TaskCallback{

    abstract class Callback<R> {
        public Type rType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        abstract void onSuccess(R response);
        abstract void onError(ErrorBundle error);
    }

    abstract class Success<R> extends Callback<R> {
        void onError(ErrorBundle error) {

        }
    }
}