package com.silencedut.city.repository;

import java.util.List;

/**
 * Created by SilenceDut on 16/10/28.
 */

public class CityEntry {

    /**
     * name : 北京
     * name_en : beijing
     * city : {"name":"北京","county":[{"name":"北京","code":"CN101010100","name_en":"beijing"},{"name":"海淀","code":"CN101010200","name_en":"haidian"},{"name":"朝阳","code":"CN101010300","name_en":"chaoyang"},{"name":"顺义","code":"CN101010400","name_en":"shunyi"},{"name":"怀柔","code":"CN101010500","name_en":"huairou"},{"name":"通州","code":"CN101010600","name_en":"tongzhou"},{"name":"昌平","code":"CN101010700","name_en":"changping"},{"name":"延庆","code":"CN101010800","name_en":"yanqing"},{"name":"丰台","code":"CN101010900","name_en":"fengtai"},{"name":"石景山","code":"CN101011000","name_en":"shijingshan"},{"name":"大兴","code":"CN101011100","name_en":"daxing"},{"name":"房山","code":"CN101011200","name_en":"fangshan"},{"name":"密云","code":"CN101011300","name_en":"miyun"},{"name":"门头沟","code":"CN101011400","name_en":"mentougou"},{"name":"平谷","code":"CN101011500","name_en":"pinggu"}]}
     */

    private String name;
    private String name_en;
    private List<CityBean> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * name : 北京
         * county : [{"name":"北京","code":"CN101010100","name_en":"beijing"},{"name":"海淀","code":"CN101010200","name_en":"haidian"},{"name":"朝阳","code":"CN101010300","name_en":"chaoyang"},{"name":"顺义","code":"CN101010400","name_en":"shunyi"},{"name":"怀柔","code":"CN101010500","name_en":"huairou"},{"name":"通州","code":"CN101010600","name_en":"tongzhou"},{"name":"昌平","code":"CN101010700","name_en":"changping"},{"name":"延庆","code":"CN101010800","name_en":"yanqing"},{"name":"丰台","code":"CN101010900","name_en":"fengtai"},{"name":"石景山","code":"CN101011000","name_en":"shijingshan"},{"name":"大兴","code":"CN101011100","name_en":"daxing"},{"name":"房山","code":"CN101011200","name_en":"fangshan"},{"name":"密云","code":"CN101011300","name_en":"miyun"},{"name":"门头沟","code":"CN101011400","name_en":"mentougou"},{"name":"平谷","code":"CN101011500","name_en":"pinggu"}]
         */

        private String name;
        private List<CountyBean> county;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CountyBean> getCounty() {
            return county;
        }

        public void setCounty(List<CountyBean> county) {
            this.county = county;
        }

        public static class CountyBean {
            /**
             * name : 北京
             * code : CN101010100
             * name_en : beijing
             */

            private String name;
            private String code;
            private String name_en;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }
        }
    }
}
