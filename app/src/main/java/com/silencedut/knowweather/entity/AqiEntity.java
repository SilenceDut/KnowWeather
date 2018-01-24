package com.silencedut.knowweather.entity;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/20 .
 */

public class AqiEntity {
    public List<HeWeather6Bean> HeWeather6;

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101280101","location":"广州","parent_city":"广州","admin_area":"广东","cnty":"中国","lat":"23.12517738","lon":"113.28063965","tz":"+8.0"}
         * update : {"loc":"2018-01-20 11:09","utc":"2018-01-20 03:09"}
         * status : ok
         * air_now_city : {"aqi":"138","qlty":"轻度污染","main":"PM2.5","pm25":"105","pm10":"128","no2":"75","so2":"23","co":"1","o3":"17","pub_time":"2018-01-20 10:00"}
         * air_now_station : [{"air_sta":"广雅中学","aqi":"144","asid":"CNA1345","co":"1.3","lat":"23.1422","lon":"113.235","main":"PM2.5","no2":"88","o3":"8","pm10":"143","pm25":"110","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"24"},{"air_sta":"市五中","aqi":"149","asid":"CNA1346","co":"1.4","lat":"23.105","lon":"113.261","main":"PM2.5","no2":"123","o3":"12","pm10":"0","pm25":"114","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"23"},{"air_sta":"天河职幼","aqi":"0","asid":"CNA1347","co":"0.0","lat":"23.123303","lon":"113.384803","main":"-","no2":"0","o3":"0","pm10":"0","pm25":"0","pub_time":"2015-06-01 14:00","qlty":"-","so2":"0"},{"air_sta":"广东商学院","aqi":"134","asid":"CNA1348","co":"0.9","lat":"23.0916","lon":"113.348","main":"PM2.5","no2":"80","o3":"11","pm10":"124","pm25":"102","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"19"},{"air_sta":"市八十六中","aqi":"132","asid":"CNA1349","co":"1.2","lat":"23.105","lon":"113.433","main":"PM2.5","no2":"77","o3":"11","pm10":"128","pm25":"100","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"18"},{"air_sta":"番禺中学","aqi":"178","asid":"CNA1350","co":"1.6","lat":"22.9477","lon":"113.352","main":"PM2.5","no2":"128","o3":"6","pm10":"159","pm25":"134","pub_time":"2018-01-20 10:00","qlty":"中度污染","so2":"39"},{"air_sta":"花都师范","aqi":"129","asid":"CNA1351","co":"1.1","lat":"23.3917","lon":"113.215","main":"PM2.5","no2":"45","o3":"47","pm10":"110","pm25":"98","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"17"},{"air_sta":"市监测站","aqi":"133","asid":"CNA1352","co":"1.0","lat":"23.1331","lon":"113.26","main":"PM2.5","no2":"75","o3":"10","pm10":"125","pm25":"101","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"25"},{"air_sta":"九龙镇镇龙","aqi":"114","asid":"CNA1353","co":"1.2","lat":"23.2783","lon":"113.568","main":"PM2.5","no2":"39","o3":"16","pm10":"0","pm25":"86","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"26"},{"air_sta":"麓湖","aqi":"148","asid":"CNA1354","co":"1.6","lat":"23.1569","lon":"113.281","main":"PM2.5","no2":"58","o3":"21","pm10":"0","pm25":"113","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"21"},{"air_sta":"帽峰山森林公园","aqi":"127","asid":"CNA1355","co":"1.1","lat":"23.5538","lon":"113.589","main":"PM2.5","no2":"46","o3":"43","pm10":"0","pm25":"96","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"25"},{"air_sta":"体育西","aqi":"120","asid":"CNA2846","co":"1.3","lat":"23.1323","lon":"113.3208","main":"PM2.5","no2":"74","o3":"8","pm10":"108","pm25":"91","pub_time":"2018-01-20 10:00","qlty":"轻度污染","so2":"24"}]
         */

        public BasicBean basic;
        public UpdateBean update;
        public String status;
        public AirNowCityBean air_now_city;
        public List<AirNowStationBean> air_now_station;

        public static class BasicBean {
            /**
             * cid : CN101280101
             * location : 广州
             * parent_city : 广州
             * admin_area : 广东
             * cnty : 中国
             * lat : 23.12517738
             * lon : 113.28063965
             * tz : +8.0
             */

            public String cid;
            public String location;
            public String parent_city;
            public String admin_area;
            public String cnty;
            public String lat;
            public String lon;
            public String tz;
        }

        public static class UpdateBean {
            /**
             * loc : 2018-01-20 11:09
             * utc : 2018-01-20 03:09
             */

            public String loc;
            public String utc;
        }

        public static class AirNowCityBean {
            /**
             * aqi : 138
             * qlty : 轻度污染
             * main : PM2.5
             * pm25 : 105
             * pm10 : 128
             * no2 : 75
             * so2 : 23
             * co : 1
             * o3 : 17
             * pub_time : 2018-01-20 10:00
             */

            public String aqi;
            public String qlty;
            public String main;
            public String pm25;
            public String pm10;
            public String no2;
            public String so2;
            public String co;
            public String o3;
            public String pub_time;
        }

        public static class AirNowStationBean {
            /**
             * air_sta : 广雅中学
             * aqi : 144
             * asid : CNA1345
             * co : 1.3
             * lat : 23.1422
             * lon : 113.235
             * main : PM2.5
             * no2 : 88
             * o3 : 8
             * pm10 : 143
             * pm25 : 110
             * pub_time : 2018-01-20 10:00
             * qlty : 轻度污染
             * so2 : 24
             */

            public String air_sta;
            public String aqi;
            public String asid;
            public String co;
            public String lat;
            public String lon;
            public String main;
            public String no2;
            public String o3;
            public String pm10;
            public String pm25;
            public String pub_time;
            public String qlty;
            public String so2;
        }
    }
}
