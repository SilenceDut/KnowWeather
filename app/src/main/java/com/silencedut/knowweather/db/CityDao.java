package com.silencedut.knowweather.db;

import android.provider.BaseColumns;

/**
 * Created by SilenceDut on 16/10/28.
 */

class CityDao implements BaseColumns {
    private CityDao() {
    }

    static final String TABLE_NAME = "city";

    static final String PINYIN = "cityPinyin";

    static final String CITY_NAME = "cityName";

    static final String CITY_ID = "cityId";

    private static final String CITY_INFO = "cityInfo";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    static final String SQL_CREATE_ENTRIES = "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME + " (" +
            PINYIN + TEXT_TYPE + COMMA_SEP +
            CITY_NAME + TEXT_TYPE + COMMA_SEP +
            CITY_ID + TEXT_TYPE + COMMA_SEP +
            CITY_INFO + TEXT_TYPE +
            ")";


    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
}


