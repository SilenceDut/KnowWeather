package com.silencedut.city.repository.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.silencedut.weather_core.api.cityprovider.City;

/**
 * Created by SilenceDut on 2018/1/5 .
 */

@Database(entities = {City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao cityDao();
}
