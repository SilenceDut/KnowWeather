package com.silencedut.knowweather.weather.entity;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/25.
 */

public class WeatherEntity {


    /**
     * cityId : 101220901
     * basic : {"city_unselected":"亳州","province":"安徽省","temp":"20°","time":"2016-09-29 14:00:00","weather":"多云","weatherIcon":"/public","img":"0"}
     * aqi : {"aqi":"53","cityRank":"空气质量超过全国38%的城市或地区","pm10":"54","pm25":"21","quality":"良","advice":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"}
     * hoursForecast : [{"temp":"21°","time":"2016-09-29 15:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"20°","time":"2016-09-29 16:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"20°","time":"2016-09-29 17:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"18°","time":"2016-09-29 18:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"18°","time":"2016-09-29 19:00:00","weather":"多云","weatherIcon":"/public","img":"0"},{"temp":"17°","time":"2016-09-29 20:00:00","weather":"多云","weatherIcon":"/public","img":"0"},{"temp":"17°","time":"2016-09-29 21:00:00","weather":"多云","weatherIcon":"/public","img":"0"},{"temp":"17°","time":"2016-09-29 22:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-29 23:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 00:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 01:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 02:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 03:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 04:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 05:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"15°","time":"2016-09-30 06:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"15°","time":"2016-09-30 07:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"16°","time":"2016-09-30 08:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"17°","time":"2016-09-30 09:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"17°","time":"2016-09-30 10:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"18°","time":"2016-09-30 11:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"18°","time":"2016-09-30 12:00:00","weather":"多云","weatherIcon":"/public","img":"0"},{"temp":"18°","time":"2016-09-30 13:00:00","weather":"多云","weatherIcon":"/public","img":"0"},{"temp":"19°","time":"2016-09-30 14:00:00","weather":"阴","weatherIcon":"/public","img":"0"},{"temp":"19°","time":"2016-09-30 15:00:00","weather":"阴","weatherIcon":"/public","img":"0"}]
     * dailyForecast : [{"date":"2016-09-29","temp_range":"15~20°","weather":"多云","week":"今天","weatherIcon":"/public","img":"0"},{"date":"2016-09-30","temp_range":"17~22°","weather":"小雨","week":"周五","weatherIcon":"/public","img":"0"},{"date":"2016-10-01","temp_range":"17~23°","weather":"小雨","week":"周六","weatherIcon":"/public","img":"0"},{"date":"2016-10-02","temp_range":"16~26°","weather":"阴","week":"周日","weatherIcon":"/public","img":"0"},{"date":"2016-10-03","temp_range":"17~28°","weather":"多云","week":"周一","weatherIcon":"/public","img":"0"},{"date":"2016-10-04","temp_range":"18~28°","weather":"多云","week":"周二","weatherIcon":"/public","img":"0"},{"date":"2016-09-28","temp_range":"13~19°","weather":"小雨","week":"周三","weatherIcon":"/public","img":"0"}]
     * lifeIndex : [{"name":"防晒","level":"弱","content":"属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"},{"name":"穿衣","level":"较舒适","content":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},{"name":"运动","level":"较适宜","content":"阴天，较适宜进行各种户内外运动。"},{"name":"逛街","level":"较适宜","content":"天气较好，虽然风有点大，还是较适宜逛街的，不过出门前要给秀发定定型，别让风吹乱您的秀发。"},{"name":"晾晒","level":"不太适宜","content":"天气阴沉，不利于水分的迅速蒸发，不太适宜晾晒。若需要晾晒，请尽量选择通风的地点。"},{"name":"洗车","level":"较适宜","content":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"name":"感冒","level":"较易发","content":"天气较凉，较易发生感冒，请适当增加衣服。体质较弱的朋友尤其应该注意防护。"},{"name":"广场舞","level":"较适宜","content":"虽然是阴天，但仍比较适宜夜生活，只要您稍作准备就可以放心外出。"}]
     * alarms : [{"alarmContent":"安徽省气象台2016年09月29日11时30分变更台风蓝色预警信号区域。受台风\u201c鲇鱼\u201d残留云系影响，24小时内全省东北风平均风力4级左右，部分地区阵风可达8-9级，沿淮到沿江部分地区暴雨，局部大暴雨，请注意加强防范。","alarmDesc":"24小时内平均风力达6级以上或阵风8级以上，并可能持续","alarmId":"201609291135583211台风蓝色","alarmLevelNo":"01","alarmLevelNoDesc":"蓝色","alarmType":"01","alarmTypeDesc":"台风预警","precaution":"1.关好门窗并将易被风吹动的搭建物固紧;\n\n2.居民避免外出，停止室外作业到室内暂避;\n\n3.做好台风准备的同时，也要做好防雷电的准备。","publishTime":"09月29日 11:30 发布"},{"alarmContent":"安徽省气象台2016年09月29日07时00分发布暴雨蓝色预警信号。12小时内淮河以南部分地区降雨量将达50毫米以上，局部地区超过100毫米，请注意防范。","alarmDesc":"12小时内降雨量可达50毫米以上，并可能持续","alarmId":"201609290712583211暴雨蓝色","alarmLevelNo":"01","alarmLevelNoDesc":"蓝色","alarmType":"02","alarmTypeDesc":"暴雨预警","precaution":"1.不要在积水中行走，防止跌入窨井、地坑等;\n\n2.驾驶车辆遇到路面积水过深时，应尽量绕行;\n\n3.保存好通讯设备，与外界保持通讯。","publishTime":"09月29日 07:00 发布"},{"alarmContent":"安徽省气象台2016年09月28日22时00分确认大风蓝色预警信号。24小时内全省大部分地区平均风力4-5级，阵风可达8级左右，请注意防范。","alarmDesc":"24小时内受大风影响，平均风力为6～7级或阵风7～8级，并可能持续。","alarmId":"201609282214583211大风蓝色","alarmLevelNo":"01","alarmLevelNoDesc":"蓝色","alarmType":"05","alarmTypeDesc":"大风预警","precaution":"1.关好门窗，妥善安置易受大风影响的室外物品；\n\n2.刮风时不要在广告牌、临时搭建物等下面逗留。","publishTime":"09月28日 22:00 发布"}]
     */

    private String cityId;
    private BasicEntity basic;
    private AqiEntity aqi;
    private List<HoursForecastEntity> hoursForecast;
    private List<DailyForecastEntity> dailyForecast;
    private List<LifeIndexEntity> lifeIndex;
    private List<AlarmsEntity> alarms;

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public void setAqi(AqiEntity aqi) {
        this.aqi = aqi;
    }

    public void setHoursForecast(List<HoursForecastEntity> hoursForecast) {
        this.hoursForecast = hoursForecast;
    }

    public void setDailyForecast(List<DailyForecastEntity> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public void setLifeIndex(List<LifeIndexEntity> lifeIndex) {
        this.lifeIndex = lifeIndex;
    }

    public void setAlarms(List<AlarmsEntity> alarms) {
        this.alarms = alarms;
    }

    public String getCityId() {
        return cityId;
    }

    public BasicEntity getBasic() {
        return basic;
    }

    public AqiEntity getAqi() {
        return aqi;
    }

    public List<HoursForecastEntity> getHoursForecast() {
        return hoursForecast;
    }

    public List<DailyForecastEntity> getDailyForecast() {
        return dailyForecast;
    }

    public List<LifeIndexEntity> getLifeIndex() {
        return lifeIndex;
    }

    public List<AlarmsEntity> getAlarms() {
        return alarms;
    }

    public static class BasicEntity {
        /**
         * city_unselected : 亳州
         * province : 安徽省
         * temp : 20°
         * time : 2016-09-29 14:00:00
         * weather : 多云
         * weatherIcon : /public
         * img : 0
         */

        private String city;
        private String province;
        private String temp;
        private String time;
        private String weather;
        private String weatherIcon;
        private String img;

        public void setCity(String city) {
            this.city = city;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setWeatherIcon(String weatherIcon) {
            this.weatherIcon = weatherIcon;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCity() {
            return city;
        }

        public String getProvince() {
            return province;
        }

        public String getTemp() {
            return temp;
        }

        public String getTime() {
            return time;
        }

        public String getWeather() {
            return weather;
        }

        public String getWeatherIcon() {
            return weatherIcon;
        }

        public String getImg() {
            return img;
        }
    }

    public static class AqiEntity {
        /**
         * aqi : 53
         * cityRank : 空气质量超过全国38%的城市或地区
         * pm10 : 54
         * pm25 : 21
         * quality : 良
         * advice : 气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。
         */

        private String aqi;
        private String cityRank;
        private String pm10;
        private String pm25;
        private String quality;
        private String advice;

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public void setCityRank(String cityRank) {
            this.cityRank = cityRank;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getAqi() {
            return aqi;
        }

        public String getCityRank() {
            return cityRank;
        }

        public String getPm10() {
            return pm10;
        }

        public String getPm25() {
            return pm25;
        }

        public String getQuality() {
            return quality;
        }

        public String getAdvice() {
            return advice;
        }
    }

    public static class HoursForecastEntity {
        /**
         * temp : 21°
         * time : 2016-09-29 15:00:00
         * weather : 阴
         * weatherIcon : /public
         * img : 0
         */

        private String temp;
        private String time;
        private String weather;
        private String weatherIcon;
        private String img;

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setWeatherIcon(String weatherIcon) {
            this.weatherIcon = weatherIcon;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTemp() {
            return temp;
        }

        public String getTime() {
            return time;
        }

        public String getWeather() {
            return weather;
        }

        public String getWeatherIcon() {
            return weatherIcon;
        }

        public String getImg() {
            return img;
        }
    }

    public static class DailyForecastEntity {
        /**
         * date : 2016-09-29
         * temp_range : 15~20°
         * weather : 多云
         * week : 今天
         * weatherIcon : /public
         * img : 0
         */

        private String date;
        private String temp_range;
        private String weather;
        private String week;
        private String weatherIcon;
        private String img;

        public void setDate(String date) {
            this.date = date;
        }

        public void setTemp_range(String temp_range) {
            this.temp_range = temp_range;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public void setWeatherIcon(String weatherIcon) {
            this.weatherIcon = weatherIcon;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDate() {
            return date;
        }

        public String getTemp_range() {
            return temp_range;
        }

        public String getWeather() {
            return weather;
        }

        public String getWeek() {
            return week;
        }

        public String getWeatherIcon() {
            return weatherIcon;
        }

        public String getImg() {
            return img;
        }
    }

    public static class LifeIndexEntity {
        /**
         * name : 防晒
         * level : 弱
         * content : 属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        private String name;
        private String level;
        private String content;

        public void setName(String name) {
            this.name = name;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public String getLevel() {
            return level;
        }

        public String getContent() {
            return content;
        }
    }

    public static class AlarmsEntity {
        /**
         * alarmContent : 安徽省气象台2016年09月29日11时30分变更台风蓝色预警信号区域。受台风“鲇鱼”残留云系影响，24小时内全省东北风平均风力4级左右，部分地区阵风可达8-9级，沿淮到沿江部分地区暴雨，局部大暴雨，请注意加强防范。
         * alarmDesc : 24小时内平均风力达6级以上或阵风8级以上，并可能持续
         * alarmId : 201609291135583211台风蓝色
         * alarmLevelNo : 01
         * alarmLevelNoDesc : 蓝色
         * alarmType : 01
         * alarmTypeDesc : 台风预警
         * precaution : 1.关好门窗并将易被风吹动的搭建物固紧;
         * <p>
         * 2.居民避免外出，停止室外作业到室内暂避;
         * <p>
         * 3.做好台风准备的同时，也要做好防雷电的准备。
         * publishTime : 09月29日 11:30 发布
         */

        private String alarmContent;
        private String alarmDesc;
        private String alarmId;
        private String alarmLevelNo;
        private String alarmLevelNoDesc;
        private String alarmType;
        private String alarmTypeDesc;
        private String precaution;
        private String publishTime;

        public void setAlarmContent(String alarmContent) {
            this.alarmContent = alarmContent;
        }

        public void setAlarmDesc(String alarmDesc) {
            this.alarmDesc = alarmDesc;
        }

        public void setAlarmId(String alarmId) {
            this.alarmId = alarmId;
        }

        public void setAlarmLevelNo(String alarmLevelNo) {
            this.alarmLevelNo = alarmLevelNo;
        }

        public void setAlarmLevelNoDesc(String alarmLevelNoDesc) {
            this.alarmLevelNoDesc = alarmLevelNoDesc;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
        }

        public void setAlarmTypeDesc(String alarmTypeDesc) {
            this.alarmTypeDesc = alarmTypeDesc;
        }

        public void setPrecaution(String precaution) {
            this.precaution = precaution;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getAlarmContent() {
            return alarmContent;
        }

        public String getAlarmDesc() {
            return alarmDesc;
        }

        public String getAlarmId() {
            return alarmId;
        }

        public String getAlarmLevelNo() {
            return alarmLevelNo;
        }

        public String getAlarmLevelNoDesc() {
            return alarmLevelNoDesc;
        }

        public String getAlarmType() {
            return alarmType;
        }

        public String getAlarmTypeDesc() {
            return alarmTypeDesc;
        }

        public String getPrecaution() {
            return precaution;
        }

        public String getPublishTime() {
            return publishTime;
        }
    }
}
