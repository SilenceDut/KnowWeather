package com.silencedut.knowweather.entity;

import java.util.List;

/**
 * Created by SilenceDut on 2018/1/9 .
 *
 * 和风天气接口
 */

public class HeWeather {
    private List<HeWeather6Bean> HeWeather6;

    public List<HeWeather6Bean> getHeWeather6() {
        return HeWeather6;
    }

    public void setHeWeather6(List<HeWeather6Bean> HeWeather6) {
        this.HeWeather6 = HeWeather6;
    }

    public static class HeWeather6Bean {
        /**
         * basic : {"cid":"CN101280102","location":"番禺","parent_city":"广州","admin_area":"广东","cnty":"中国","lat":"22.93858147","lon":"113.36461639","tz":"+8.0"}
         * update : {"loc":"2018-01-09 11:52","utc":"2018-01-09 03:52"}
         * status : ok
         * now : {"cloud":"25","cond_code":"300","cond_txt":"阵雨","fl":"0","hum":"79","pcpn":"0.0","pres":"1027","tmp":"7","vis":"8","wind_deg":"359","wind_dir":"北风","wind_sc":"微风","wind_spd":"9"}
         * dailyForecast : [{"cond_code_d":"305","cond_code_n":"104","cond_txt_d":"小雨","cond_txt_n":"阴","date":"2018-01-09","hum":"49","mr":"00:21","ms":"12:32","pcpn":"2.8","pop":"98","pres":"1025","sr":"07:09","ss":"17:59","tmp_max":"9","tmp_min":"6","uv_index":"0","vis":"18","wind_deg":"356","wind_dir":"北风","wind_sc":"3-4","wind_spd":"16"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-10","hum":"25","mr":"01:14","ms":"13:10","pcpn":"0.0","pop":"0","pres":"1026","sr":"07:09","ss":"18:00","tmp_max":"14","tmp_min":"7","uv_index":"6","vis":"20","wind_deg":"11","wind_dir":"北风","wind_sc":"3-4","wind_spd":"16"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-11","hum":"23","mr":"02:06","ms":"13:48","pcpn":"0.0","pop":"0","pres":"1026","sr":"07:09","ss":"18:00","tmp_max":"15","tmp_min":"8","uv_index":"6","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"4"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-12","hum":"24","mr":"02:57","ms":"14:27","pcpn":"0.0","pop":"0","pres":"1027","sr":"07:09","ss":"18:01","tmp_max":"16","tmp_min":"9","uv_index":"6","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"3"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-13","hum":"31","mr":"03:49","ms":"15:09","pcpn":"0.0","pop":"0","pres":"1025","sr":"07:09","ss":"18:02","tmp_max":"17","tmp_min":"11","uv_index":"7","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"7"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-14","hum":"34","mr":"04:39","ms":"15:53","pcpn":"0.0","pop":"0","pres":"1022","sr":"07:09","ss":"18:02","tmp_max":"18","tmp_min":"11","uv_index":"5","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"3"},{"cond_code_d":"101","cond_code_n":"101","cond_txt_d":"多云","cond_txt_n":"多云","date":"2018-01-15","hum":"47","mr":"05:29","ms":"16:38","pcpn":"0.0","pop":"0","pres":"1018","sr":"07:09","ss":"18:03","tmp_max":"20","tmp_min":"12","uv_index":"6","vis":"20","wind_deg":"0","wind_dir":"无持续风向","wind_sc":"微风","wind_spd":"6"}]
         * hourly : [{"cloud":"78","cond_code":"305","cond_txt":"小雨","dew":"4","hum":"57","pop":"71","pres":"1024","time":"2018-01-09 13:00","tmp":"6","wind_deg":"10","wind_dir":"北风","wind_sc":"3-4","wind_spd":"17"},{"cloud":"77","cond_code":"305","cond_txt":"小雨","dew":"4","hum":"53","pop":"63","pres":"1023","time":"2018-01-09 16:00","tmp":"8","wind_deg":"9","wind_dir":"北风","wind_sc":"微风","wind_spd":"16"},{"cloud":"76","cond_code":"101","cond_txt":"多云","dew":"2","hum":"46","pop":"0","pres":"1025","time":"2018-01-09 19:00","tmp":"8","wind_deg":"24","wind_dir":"东北风","wind_sc":"微风","wind_spd":"15"},{"cloud":"76","cond_code":"101","cond_txt":"多云","dew":"-1","hum":"36","pop":"0","pres":"1027","time":"2018-01-09 22:00","tmp":"6","wind_deg":"21","wind_dir":"东北风","wind_sc":"微风","wind_spd":"16"},{"cloud":"65","cond_code":"101","cond_txt":"多云","dew":"-4","hum":"30","pop":"0","pres":"1026","time":"2018-01-10 01:00","tmp":"6","wind_deg":"14","wind_dir":"东北风","wind_sc":"3-4","wind_spd":"18"},{"cloud":"64","cond_code":"103","cond_txt":"晴间多云","dew":"-7","hum":"26","pop":"0","pres":"1026","time":"2018-01-10 04:00","tmp":"7","wind_deg":"11","wind_dir":"北风","wind_sc":"3-4","wind_spd":"17"},{"cloud":"61","cond_code":"103","cond_txt":"晴间多云","dew":"-7","hum":"27","pop":"0","pres":"1027","time":"2018-01-10 07:00","tmp":"5","wind_deg":"10","wind_dir":"北风","wind_sc":"微风","wind_spd":"15"},{"cloud":"58","cond_code":"103","cond_txt":"晴间多云","dew":"-7","hum":"26","pop":"0","pres":"1028","time":"2018-01-10 10:00","tmp":"10","wind_deg":"9","wind_dir":"北风","wind_sc":"微风","wind_spd":"15"}]
         * lifestyle : [{"brf":"较舒适","txt":"白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。","type":"comf"},{"brf":"较冷","txt":"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。","type":"drsg"},{"brf":"易发","txt":"天冷空气湿度大，易发生感冒，请注意适当增加衣服，加强自我防护避免感冒。","type":"flu"},{"brf":"较不宜","txt":"有降水，且风力较强，推荐您在室内进行各种健身休闲运动；若坚持户外运动，请注意防风保暖。","type":"sport"},{"brf":"一般","txt":"天气稍凉，风稍大会加大些凉意，且预报有降水，旅游指数一般，外出旅游请注意防风保暖并携带雨具。","type":"trav"},{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。","type":"uv"},{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。","type":"cw"},{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","type":"air"}]
         */

        private BasicBean basic;
        private UpdateBean update;
        private String status;
        private NowBean now;
        private List<DailyForecastBean> daily_forecast;
        private List<HourlyBean> hourly;
        private List<LifestyleBean> lifestyle;

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public UpdateBean getUpdate() {
            return update;
        }

        public void setUpdate(UpdateBean update) {
            this.update = update;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public List<DailyForecastBean> getDailyForecast() {
            return daily_forecast;
        }

        public void setDailyForecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyBean> getHourly() {
            return hourly;
        }

        public void setHourly(List<HourlyBean> hourly) {
            this.hourly = hourly;
        }

        public List<LifestyleBean> getLifestyle() {
            return lifestyle;
        }

        public void setLifestyle(List<LifestyleBean> lifestyle) {
            this.lifestyle = lifestyle;
        }

        public static class BasicBean {
            /**
             * cid : CN101280102
             * location : 番禺
             * parent_city : 广州
             * admin_area : 广东
             * cnty : 中国
             * lat : 22.93858147
             * lon : 113.36461639
             * tz : +8.0
             */

            private String cid;
            private String location;
            private String parent_city;
            private String admin_area;
            private String cnty;
            private String lat;
            private String lon;
            private String tz;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getParent_city() {
                return parent_city;
            }

            public void setParent_city(String parent_city) {
                this.parent_city = parent_city;
            }

            public String getAdmin_area() {
                return admin_area;
            }

            public void setAdmin_area(String admin_area) {
                this.admin_area = admin_area;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getTz() {
                return tz;
            }

            public void setTz(String tz) {
                this.tz = tz;
            }
        }

        public static class UpdateBean {
            /**
             * loc : 2018-01-09 11:52
             * utc : 2018-01-09 03:52
             */

            private String loc;
            private String utc;

            public String getLoc() {
                return loc;
            }

            public void setLoc(String loc) {
                this.loc = loc;
            }

            public String getUtc() {
                return utc;
            }

            public void setUtc(String utc) {
                this.utc = utc;
            }
        }

        public static class NowBean {
            /**
             * cloud : 25
             * cond_code : 300
             * cond_txt : 阵雨
             * fl : 0
             * hum : 79
             * pcpn : 0.0
             * pres : 1027
             * tmp : 7
             * vis : 8
             * wind_deg : 359
             * wind_dir : 北风
             * wind_sc : 微风
             * wind_spd : 9
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String fl;
            private String hum;
            private String pcpn;
            private String pres;
            private String tmp;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getFl() {
                return fl;
            }

            public void setFl(String fl) {
                this.fl = fl;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class DailyForecastBean {
            /**
             * cond_code_d : 305
             * cond_code_n : 104
             * cond_txt_d : 小雨
             * cond_txt_n : 阴
             * date : 2018-01-09
             * hum : 49
             * mr : 00:21
             * ms : 12:32
             * pcpn : 2.8
             * pop : 98
             * pres : 1025
             * sr : 07:09
             * ss : 17:59
             * tmp_max : 9
             * tmp_min : 6
             * uv_index : 0
             * vis : 18
             * wind_deg : 356
             * wind_dir : 北风
             * wind_sc : 3-4
             * wind_spd : 16
             */

            private String cond_code_d;
            private String cond_code_n;
            private String cond_txt_d;
            private String cond_txt_n;
            private String date;
            private String hum;
            private String mr;
            private String ms;
            private String pcpn;
            private String pop;
            private String pres;
            private String sr;
            private String ss;
            private String tmp_max;
            private String tmp_min;
            private String uv_index;
            private String vis;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCond_code_d() {
                return cond_code_d;
            }

            public void setCond_code_d(String cond_code_d) {
                this.cond_code_d = cond_code_d;
            }

            public String getCond_code_n() {
                return cond_code_n;
            }

            public void setCond_code_n(String cond_code_n) {
                this.cond_code_n = cond_code_n;
            }

            public String getCond_txt_d() {
                return cond_txt_d;
            }

            public void setCond_txt_d(String cond_txt_d) {
                this.cond_txt_d = cond_txt_d;
            }

            public String getCond_txt_n() {
                return cond_txt_n;
            }

            public void setCond_txt_n(String cond_txt_n) {
                this.cond_txt_n = cond_txt_n;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getPcpn() {
                return pcpn;
            }

            public void setPcpn(String pcpn) {
                this.pcpn = pcpn;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            public String getTmp_max() {
                return tmp_max;
            }

            public void setTmp_max(String tmp_max) {
                this.tmp_max = tmp_max;
            }

            public String getTmp_min() {
                return tmp_min;
            }

            public void setTmp_min(String tmp_min) {
                this.tmp_min = tmp_min;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getVis() {
                return vis;
            }

            public void setVis(String vis) {
                this.vis = vis;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class HourlyBean {
            /**
             * cloud : 78
             * cond_code : 305
             * cond_txt : 小雨
             * dew : 4
             * hum : 57
             * pop : 71
             * pres : 1024
             * time : 2018-01-09 13:00
             * tmp : 6
             * wind_deg : 10
             * wind_dir : 北风
             * wind_sc : 3-4
             * wind_spd : 17
             */

            private String cloud;
            private String cond_code;
            private String cond_txt;
            private String dew;
            private String hum;
            private String pop;
            private String pres;
            private String time;
            private String tmp;
            private String wind_deg;
            private String wind_dir;
            private String wind_sc;
            private String wind_spd;

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
            }

            public String getCond_code() {
                return cond_code;
            }

            public void setCond_code(String cond_code) {
                this.cond_code = cond_code;
            }

            public String getCond_txt() {
                return cond_txt;
            }

            public void setCond_txt(String cond_txt) {
                this.cond_txt = cond_txt;
            }

            public String getDew() {
                return dew;
            }

            public void setDew(String dew) {
                this.dew = dew;
            }

            public String getHum() {
                return hum;
            }

            public void setHum(String hum) {
                this.hum = hum;
            }

            public String getPop() {
                return pop;
            }

            public void setPop(String pop) {
                this.pop = pop;
            }

            public String getPres() {
                return pres;
            }

            public void setPres(String pres) {
                this.pres = pres;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTmp() {
                return tmp;
            }

            public void setTmp(String tmp) {
                this.tmp = tmp;
            }

            public String getWind_deg() {
                return wind_deg;
            }

            public void setWind_deg(String wind_deg) {
                this.wind_deg = wind_deg;
            }

            public String getWind_dir() {
                return wind_dir;
            }

            public void setWind_dir(String wind_dir) {
                this.wind_dir = wind_dir;
            }

            public String getWind_sc() {
                return wind_sc;
            }

            public void setWind_sc(String wind_sc) {
                this.wind_sc = wind_sc;
            }

            public String getWind_spd() {
                return wind_spd;
            }

            public void setWind_spd(String wind_spd) {
                this.wind_spd = wind_spd;
            }
        }

        public static class LifestyleBean {
            /**
             * brf : 较舒适
             * txt : 白天会有降雨，这种天气条件下，人们会感到有些凉意，但大部分人完全可以接受。
             * type : comf
             */

            private String brf;
            private String txt;
            private String type;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
