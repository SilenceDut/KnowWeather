package com.silencedut.knowweather.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.promeg.pinyinhelper.Pinyin;
import com.silencedut.knowweather.WeatherApplication;
import com.silencedut.knowweather.citys.adapter.CityInfoData;
import com.silencedut.knowweather.utils.FileUtil;
import com.silencedut.knowweather.utils.PreferencesUtil;
import com.silencedut.knowweather.utils.TaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class DBManage {
    private static DBManage sDBManage;
    private static DBHelper sDBHelper;
    private static final String CITY_INITED = "CITY_INITED";

    private DBManage() {
    }

    public static DBManage getInstance() {
        if (sDBManage == null) {
            synchronized (DBHelper.class) {
                if (sDBManage == null) {
                    sDBManage = new DBManage();
                    sDBHelper = DBHelper.getInstance(WeatherApplication.getContext());
                }
            }
        }
        return sDBManage;
    }

    public void copyCitysToDB() {
        boolean cityInited = PreferencesUtil.get(CITY_INITED, false);
        if (cityInited) {
            return;
        }
        TaskExecutor.executeTask(new Runnable() {
            @Override
            public void run() {
                String citys = FileUtil.assetFile2String("cityList.txt", WeatherApplication.getContext());
                CityEntry cityEntry = WeatherApplication.getGson().fromJson(citys, CityEntry.class);
                Collections.sort(cityEntry.getCity_info(), new CityComparator());
                // Gets the data repository in write mode
                SQLiteDatabase db = sDBHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    ContentValues values;
                    for (CityEntry.CityInfoEntity cityInfoEntity : cityEntry.getCity_info()) {
                        // Create a new map of values, where column names are the keys

                        StringBuilder stringBuilder = new StringBuilder();
                        StringBuilder initials = new StringBuilder();
                        for (char hanzi : cityInfoEntity.getCity().toCharArray()) {
                            String pinyin = Pinyin.toPinyin(hanzi);
                            stringBuilder.append(pinyin);
                            initials.append(pinyin.charAt(0));
                        }
                        stringBuilder.append(initials);

                        values = new ContentValues();
                        values.put(CityDao.CITY_NAME, cityInfoEntity.getCity());
                        values.put(CityDao.PINYIN, stringBuilder.toString());
                        values.put(CityDao.CITY_ID, cityInfoEntity.getId().substring(2, cityInfoEntity.getId().length()));
                        db.insert(CityDao.TABLE_NAME, null, values);
                    }
                    db.setTransactionSuccessful(); //设置事务处理成功，不设置会自动回滚不提交。
                    PreferencesUtil.put(CITY_INITED, true);
                } catch (Exception e) {
                    e.getMessage();
                } finally {
                    db.endTransaction(); //处理完成

                }
            }
        },false);
    }

    /**
     * 读取所有城市
     *
     * @return
     */
    public List<CityInfoData> getAllCities() {
        String allCitySql = "select * from " + CityDao.TABLE_NAME;
        return getCitys(allCitySql, true);
    }

    /**
     * 通过名字或者拼音搜索
     *
     * @param keyword
     * @return
     */
    public List<CityInfoData> searchCity(final String keyword) {

        String searchSql = "select * from " + CityDao.TABLE_NAME + " where " + CityDao.CITY_NAME + " like \"%" + keyword + "%\" or " + CityDao.PINYIN + " like \"%" + keyword + "%\" or " + CityDao.CITY_ID + " like \"%" + keyword + "%\"";

        return getCitys(searchSql, false);
    }

    private List<CityInfoData> getCitys(String sql, boolean all) {
        SQLiteDatabase db = sDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<CityInfoData> result = new ArrayList<>();
        CityInfoData city;
        String lastInitial = "";
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(CityDao.CITY_NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(CityDao.PINYIN));
            String cityId = cursor.getString(cursor.getColumnIndex(CityDao.CITY_ID));
            city = new CityInfoData(name, pinyin, cityId);
            String currentInitial = pinyin.substring(0, 1);
            if (!lastInitial.equals(currentInitial) && all) {
                city.setInitial(currentInitial);
                lastInitial = currentInitial;
            }
            result.add(city);
        }
        cursor.close();
        db.close();
        return result;
    }


    /**
     * a-z排序
     */
    private class CityComparator implements Comparator<CityEntry.CityInfoEntity> {
        @Override
        public int compare(CityEntry.CityInfoEntity lhs, CityEntry.CityInfoEntity rhs) {
            char a = Pinyin.toPinyin(lhs.getCity().charAt(0)).charAt(0);
            char b = Pinyin.toPinyin(rhs.getCity().charAt(0)).charAt(0);
            ;
            return a - b;
        }
    }

}
