package cn.ultronxr.service.impl;

import cn.ultronxr.service.WeatherService;
import cn.ultronxr.util.AliWeatherAPIUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2022/07/06 10:47
 */
@Slf4j
public class WeatherServiceImpl implements WeatherService {

    private final ObjectMapper jsonObjMapper = new ObjectMapper();


    @Override
    public String allInOne(@NotNull String area) {
        String response = this.request(area);
        JsonNode weatherRootJsonNode = this.parseJson(response);
        if(weatherRootJsonNode == null) {
            return "接口请求结果无法正常解析为json！";
        }
        String weatherResult = this.modifyWeatherInfo(weatherRootJsonNode);
        if(weatherResult == null) {
            return "从json提取天气信息失败！";
        }
        return weatherResult;
    }

    @Override
    public String request(@NotNull String area) {
        return AliWeatherAPIUtils.getWeatherByArea(area);
    }

    @Override
    public JsonNode parseJson(@NotNull String response) {
        JsonNode weatherJsonRootNode = null;
        try {
            weatherJsonRootNode = jsonObjMapper.readTree(response);
        } catch (JsonProcessingException e) {
            log.error("接口请求结果无法正常解析为json！\nresponse - {}", response);
            e.printStackTrace();
        }
        return weatherJsonRootNode;
    }

    @Override
    public String modifyWeatherInfo(@NotNull JsonNode weatherRootJsonNode) {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            weatherRootJsonNode = weatherRootJsonNode.path("showapi_res_body");

            // cityInfo-城市信息、now-当前天气、f1-今日天气、f2-明天天气、f3-后天天气
            JsonNode cityInfo = weatherRootJsonNode.path("cityInfo"),
                    now = weatherRootJsonNode.path("now"),
                    f1 = weatherRootJsonNode.path("f1"),
                    f2 = weatherRootJsonNode.path("f2"),
                    f3 = weatherRootJsonNode.path("f3");

            sb.append("●地区\n  ")
                    .append(cityInfo.path("c9").asText()).append(" - ")
                    .append(cityInfo.path("c7").asText()).append(" - ")
                    .append(cityInfo.path("c3").asText()).append("\n");
            sb.append("●经纬海拔\n  ")
                    .append(cityInfo.path("longitude").asText()).append(" - ")
                    .append(cityInfo.path("latitude").asText()).append(" - ")
                    .append(cityInfo.path("c15").asText()).append("\n");
            sb.append("●今天天气\n").append(modifyWeatherForecastInfo(f1))
                    .append("●明天天气\n").append(modifyWeatherForecastInfo(f2))
                    .append("●后天天气\n").append(modifyWeatherForecastInfo(f3));
        } catch (Exception e) {
            log.error("从json提取天气信息失败！\njson - {}", weatherRootJsonNode.asText());
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    @Override
    public String modifyWeatherForecastInfo(@NotNull JsonNode weatherForecastJsonNode) {
        StringBuilder sb = null;
        try {
            sb = new StringBuilder();
            sb.append("  降水概率：").append(weatherForecastJsonNode.path("jiangshui").asText()).append("\n")
                    .append("  白天天气：").append(weatherForecastJsonNode.path("day_weather").asText()).append("\n")
                    .append("  白天气温：").append(weatherForecastJsonNode.path("day_air_temperature").asText()).append(" ℃\n")
                    .append("  白天风力：").append(weatherForecastJsonNode.path("day_wind_power").asText()).append("\n")
                    .append("  夜间天气：").append(weatherForecastJsonNode.path("night_weather").asText()).append("\n")
                    .append("  夜间气温：").append(weatherForecastJsonNode.path("night_air_temperature").asText()).append(" ℃\n")
                    .append("  夜间风力：").append(weatherForecastJsonNode.path("night_wind_power").asText()).append("\n")
                    .append("  日出日落：").append(weatherForecastJsonNode.path("sun_begin_end").asText()).append("\n");
        } catch (Exception e) {
            log.error("从json提取天气预报信息失败！\njson - {}", weatherForecastJsonNode.asText());
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

}
