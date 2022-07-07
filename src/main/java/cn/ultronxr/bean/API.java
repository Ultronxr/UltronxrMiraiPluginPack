package cn.ultronxr.bean;

/**
 * @author Ultronxr
 * @date 2022/07/07 10:22
 *
 * API封装
 */
public class API {

    /**
     * 阿里云天气查询接口
     * 相关文档参见 {@link "https://market.aliyun.com/products/57096001/cmapi010812.html"}
     */
    public static class AliWeatherAPI {

        //private static final String APP_KEY = ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.key");

        //private static final String APP_SECRET = ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.secret");

        private static final String APP_CODE = GlobalData.ResBundle.ALI_CLOUD.getString("ali.weatherAPI.app.code");

        /** 请求接口的身份验证信息（添加在请求头，以“Authorization”为键） */
        public static final String AUTH_CODE = "APPCODE " + APP_CODE;

        /** API请求URL的前缀公共部分 */
        private static final String PREFIX_URL = "http://ali-weather.showapi.com/";

        /**
         * 不同用途、查询不同信息的API的请求地址
         */
        public enum APIs {
            // 地名查询天气
            areaToWeather(PREFIX_URL + "area-to-weather", "地名查询天气"),
            // 经纬度查询天气
            gpsToWeather(PREFIX_URL + "gps-to-weather", "经纬度查询天气"),
            // 景点名称查询天气
            spotToWeather(PREFIX_URL + "spot-to-weather", "景点名称查询天气"),
            // 区号邮编查询天气
            phonePostCodeToWeather(PREFIX_URL + "phone-post-code-weeather", "区号邮编查询天气"),
            // IP查询天气
            ipToWeather(PREFIX_URL + "ip-to-weather", "IP查询天气"),
            // 查询24小时预报
            hour24(PREFIX_URL + "hour24", "查询24小时预报"),
            // 未来7天指定日期天气
            areaToWeatherDate(PREFIX_URL + "area-to-weather-date", "未来7天指定日期天气"),
            // 未来15天预报
            day15(PREFIX_URL + "day15", "未来15天预报"),
            // 地名查询id
            areaToId(PREFIX_URL + "area-to-id", "地名查询id"),
            // 历史天气查询
            weatherHistory(PREFIX_URL + "weatherhistory", "历史天气查询");

            private final String url;
            private final String description;

            APIs(String url, String description) {
                this.url = url;
                this.description = description;
            }

            public String getUrl() {
                return this.url;
            }

            public String getDescription() {
                return this.description;
            }
        }
    }

    /**
     * 发言语录接口
     */
    public static class SayAPI {

        public static final String PREFIX_URL_SHADIAO = "https://api.shadiao.app/";
        public static final String PREFIX_URL_ZUANBOT = "https://api.zuanbot.com/";

        public static final String REFERER_SHADIAO = PREFIX_URL_SHADIAO;
        public static final String REFERER_ZUANBOT = PREFIX_URL_ZUANBOT;

        public enum APIs {
            // 彩虹屁
            chp(PREFIX_URL_SHADIAO + "chp", "彩虹屁"),
            // 朋友圈语录
            pyq(PREFIX_URL_SHADIAO + "pyq", "朋友圈语录"),
            // 毒鸡汤
            djt(PREFIX_URL_SHADIAO + "du", "毒鸡汤"),
            // 口吐芬芳（接口失效）
            ktff(PREFIX_URL_ZUANBOT + "nmsl?level=min", "口吐芬芳"),
            // 火力全开（接口失效）
            hlqk(PREFIX_URL_ZUANBOT + "nmsl?level=max", "火力全开");

            private final String url;
            private final String description;

            APIs(String url, String description) {
                this.url = url;
                this.description = description;
            }

            public String getUrl() {
                return this.url;
            }

            public String getDescription() {
                return this.description;
            }
        }


    }

}
