package com.silencedut.knowweather.db;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class CityEntry {
    /**
     * city_info : [{"city_unselected":"南子岛","cnty":"中国","id":"CN101310230","lat":"11.26","lon":"114.20","prov":"海南"},{"city_unselected":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","prov":"直辖市"},{"city_unselected":"海淀","cnty":"中国","id":"CN101010200","lat":"39.590000","lon":"116.170000","prov":"直辖市"}]
     */

    private List<CityInfoEntity> city_info;

    public void setCity_info(List<CityInfoEntity> city_info) {
        this.city_info = city_info;
    }

    public List<CityInfoEntity> getCity_info() {
        return city_info;
    }

    public static class CityInfoEntity {
        /**
         * city_unselected : 南子岛
         * cnty : 中国
         * id : CN101310230
         * lat : 11.26
         * lon : 114.20
         * prov : 海南
         */

        private String city;
        private String cnty;
        private String id;
        private String lat;
        private String lon;
        private String prov;

        public void setCity(String city) {
            this.city = city;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public void setProv(String prov) {
            this.prov = prov;
        }

        public String getCity() {
            return city;
        }

        public String getCnty() {
            return cnty;
        }

        public String getId() {
            return id;
        }

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public String getProv() {
            return prov;
        }
    }
}
