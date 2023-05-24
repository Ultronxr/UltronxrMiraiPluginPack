package cn.ultronxr.umpp.service.impl;

import cn.hutool.core.text.UnicodeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.ultronxr.umpp.bean.API;
import cn.ultronxr.umpp.bean.GlobalData;
import cn.ultronxr.umpp.service.SayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ultronxr
 * @date 2022/07/04 15:35
 */
@Slf4j
public class SayServiceImpl implements SayService {

    private final ObjectMapper jsonObjMapper = new ObjectMapper();


    @Override
    public String sayCHP() {
        return requestAPI(API.SayAPI.APIs.chp.getUrl(), API.SayAPI.REFERER_SHADIAO);
    }

    @Override
    public String sayPYQ() {
        return requestAPI(API.SayAPI.APIs.pyq.getUrl(), API.SayAPI.REFERER_SHADIAO);
    }

    @Override
    public String sayDJT() {
        return requestAPI(API.SayAPI.APIs.djt.getUrl(), API.SayAPI.REFERER_SHADIAO);
    }

    @Override
    @Deprecated
    public String sayKTFF() {
        log.warn("警告：该API失效！");
        return requestAPI(API.SayAPI.APIs.ktff.getUrl(), API.SayAPI.REFERER_ZUANBOT);
    }

    @Override
    @Deprecated
    public String sayHLQK() {
        log.warn("警告：该API失效！");
        return requestAPI(API.SayAPI.APIs.hlqk.getUrl(), API.SayAPI.REFERER_ZUANBOT);
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
