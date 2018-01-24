package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

/**
 * Created by SilenceDut on 2018/1/15 .
 */
@Entity(tableName = "weather",primaryKeys = {"cityId"})
public class Weather {

    @NonNull
    public String cityId ="";
    public String weatherJson;

}
