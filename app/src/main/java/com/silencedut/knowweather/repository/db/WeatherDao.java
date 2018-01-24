package com.silencedut.knowweather.repository.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by SilenceDut on 2018/1/15 .
 */
@Dao
public interface WeatherDao {


    @Insert(onConflict = REPLACE)
    void saveWeather(Weather weather);

    @Query("DELETE FROM weather WHERE cityId LIKE :cityId")
    void deleteWeather(String cityId);

    @Query("SELECT * FROM weather WHERE cityId LIKE :cityId")
    Weather fetchWeather(String cityId);

    @Query("SELECT * FROM weather")
    List<Weather> fetchFollowedWeather();
}
