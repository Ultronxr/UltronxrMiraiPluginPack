package cn.ultronxr.util;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.bean.API.AliWeatherAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ultronxr
 * @date 2022/07/07 18:41
 *
 * 阿里云天气API 方法库
 */
public class AliWeatherAPIUtils {

    /**
     * 地名查询天气，除了地名其他参数采用默认"0"
     *
     * @param area  {@code String} 地名
     *
     * @return      {@code String} API返回的响应体内容
     */
    public static String getWeatherByArea(String area) {
        return getWeatherByArea(area, "0", "0", "0", "0", "0", "0");
    }

    /**
     * 地名查询天气
     * 这里的参数命名直接沿用API中的请求参数
     *
     * @param area              {@code String} 地名
     * @param areaid            {@code String} 地区ID，名称和ID必须输入一个，如果都输入，以areaid为准
     * @param need3HourForcast  {@code String} 是否需要每小时数据的累积数组；  0-不需要、1-需要
     * @param needAlarm         {@code String} 是否需要天气预警；             0-不需要、1-需要
     * @param needHourData      {@code String} 是否需要每小时数据的累积数组；  0-不需要、1-需要
     * @param needIndex         {@code String} 是否需要返回指数数据（穿衣指数、紫外线指数等） 0-不需要、1-需要
     * @param needMoreDay       {@code String} 是否需要返回7天数据中的后4天    0-不需要、1-需要
     *
     * @return                  {@code String} API返回的响应体内容
     */
    public static String getWeatherByArea(String area, String areaid, String need3HourForcast, String needAlarm,
                                          String needHourData, String needIndex, String needMoreDay){
        Map<String, Object> paramMap = new HashMap<>(10);
        paramMap.put("area", area);
        paramMap.put("areaid", areaid);
        paramMap.put("need3HourForcast", need3HourForcast);
        paramMap.put("needAlarm", needAlarm);
        paramMap.put("needHourData", needHourData);
        paramMap.put("needIndex", needIndex);
        paramMap.put("needMoreDay", needMoreDay);
        return requestWeatherAPI(AliWeatherAPI.APIs.areaToWeather, paramMap);
    }

    /**
     * 请求天气API，获取接口响应信息
     *
     * @param weatherAPI 阿里天气API中的具体请求地址
     *                    {@link AliWeatherAPI.APIs}
     * @param paramMap   请求参数Map
     * @return String    响应体内容
     */
    public static String requestWeatherAPI(AliWeatherAPI.APIs weatherAPI, Map<String, Object> paramMap) {
        return HttpRequest.get(weatherAPI.getUrl())
                .header("Authorization", AliWeatherAPI.AUTH_CODE)
                .form(paramMap)
                .execute()
                .body();
    }

}
