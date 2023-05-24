package cn.ultronxr.umpp.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ultronxr
 * @date 2022/07/06 10:46
 *
 * 服务 —— 天气信息，调用阿里云天气API
 */
public interface WeatherService {

    /**
     * 请求API，并解析Json，并提取所需信息，最终返回按地区查询的天气信息<br/>
     * 无特别需要，直接使用这个接口即可。
     *
     * @param area 地区名称
     * @return 按地区查询的天气信息
     */
    String allInOne(@NotNull String area);

    /**
     * 请求API，获取指定地区的天气信息
     *
     * @param area 地区名称
     * @return 请求结果
     */
    String request(@NotNull String area);

    /**
     * 把API请求结果解析成JsonNode
     *
     * @param response API请求结果，即 {@code request(area)} 的返回结果
     * @return {@code  JsonNode}
     */
    JsonNode parseJson(@NotNull String response);

    /**
     * 从天气数据根节点JsonNode中提取出所需要的信息
     *
     * @param weatherRootJsonNode {@code JsonNode} 天气数据根节点JsonNode，即 {@code parseJson(response)} 的返回结果
     * @return {@code String} 需要的天气信息组合成的字符串
     */
    String modifyWeatherInfo(@NotNull JsonNode weatherRootJsonNode);

    /**
     * 从天气预报节点JsonNode中提取出所需要的信息
     *
     * @param weatherForecastJsonNode {@code JsonNode} 天气预报节点JsonNode（now、f1~f7）
     * @return {@code String} 需要的天气预报信息组合成的字符串
     */
    String modifyWeatherForecastInfo(@NotNull JsonNode weatherForecastJsonNode);

}
