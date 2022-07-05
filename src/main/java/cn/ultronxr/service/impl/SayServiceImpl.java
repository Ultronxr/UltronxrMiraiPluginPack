package cn.ultronxr.service.impl;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.ultronxr.bean.GlobalData;
import cn.ultronxr.service.SayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2022/07/04 15:35
 */
@Slf4j
public class SayServiceImpl implements SayService {

    /** API请求链接，从上往下依次是：彩虹屁、朋友圈语录、毒鸡汤、口吐芬芳（接口失效）、火力全开（接口失效） */
    private static final String API_CHP = "https://api.shadiao.app/chp";
    private static final String API_PYQ = "https://api.shadiao.app/pyq";
    private static final String API_DJT = "https://api.shadiao.app/du";
    private static final String API_KTFF = "https://api.zuanbot.com/nmsl?level=min";
    private static final String API_HLQK = "https://api.zuanbot.com/nmsl?level=max";

    /** API的两个referer */
    private static final String REFERER_SHADIAO = "https://api.shadiao.app";
    private static final String REFERER_ZUANBOT = "https://zuanbot.com/";

    private final ObjectMapper jsonObjMapper = new ObjectMapper();


    @Override
    public String sayCHP() {
        return requestAPI(API_CHP, REFERER_SHADIAO);
    }

    @Override
    public String sayPYQ() {
        return requestAPI(API_PYQ, REFERER_SHADIAO);
    }

    @Override
    public String sayDJT() {
        return requestAPI(API_DJT, REFERER_SHADIAO);
    }

    @Override
    @Deprecated
    public String sayKTFF() {
        return requestAPI(API_KTFF, REFERER_ZUANBOT);
    }

    @Override
    @Deprecated
    public String sayHLQK() {
        return requestAPI(API_HLQK, REFERER_ZUANBOT);
    }

    private String requestAPI(String api, String referer) {
        String res = null;
        try {
            HttpResponse response = HttpRequest.get(api)
                    .header("User-Agent", GlobalData.USER_AGENT)
                    .header("Referer", referer)
                    .execute();
            res = response.body();
            response.close();
        } catch (Exception e) {
            log.error("接口请求异常！API - {}", api);
            e.printStackTrace();
            return "接口请求异常！";
        }
        try {
            res = jsonObjMapper.readTree(res).path("data").path("text").asText();
            res = UnicodeUtil.toString(res);
        } catch (JsonProcessingException e) {
            log.error("json解析异常！json内容 - {}", res);
            e.printStackTrace();
            return "json解析异常！";
        }
        return res;
    }

}
