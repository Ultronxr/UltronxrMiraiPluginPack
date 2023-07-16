package cn.ultronxr.umpp.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.ultronxr.umpp.bean.GlobalData;
import cn.ultronxr.umpp.service.ElectricityBillService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Ultronxr
 * @date 2023/05/26 12:54:13
 * @description
 */
@Slf4j
public class ElectricityBillServiceImpl implements ElectricityBillService {

    /** 是否开启低电费 QQ 自动提醒 */
    private static boolean QQ_ALERT_ENABLE = true;

    /** 是否启用低电费 短信 自动提醒 */
    private static boolean SMS_ALERT_ENABLE = false;

    /** 记录上次发起提醒时的电费余额 */
    private static final float DEFAULT_LAST_ALERT_BALANCE = 100.0f;
    private static float LAST_ALERT_BALANCE = DEFAULT_LAST_ALERT_BALANCE;


    private static final String URL = "http://xxx.com";

    private static final HashMap<String, String> HEADERS = new HashMap<>() {{
        put("Accept", "*/*");
        put("Accept-Encoding", "gzip, deflate, br");
        put("Connection", "keep-alive");
        put("Cache-Control", "no-cache");
        put("Cookie", "xxx");
        put("Host", "xxx.com");
        put("Referer", "http://xxx.com");
        put("Pragma", "no-cache");
        put("User-Agent", GlobalData.USER_AGENT);
        put("X-Requested-With", "XMLHttpRequest");
    }};

    private static String sendRequest() {
        HttpRequest request = HttpUtil.createGet(URL).headerMap(HEADERS, true);
        return request.execute().body();
    }

    private static List<String> parseResponseBody(String responseBody) {
        String regex = "^<.*>欢迎您!(.*)。<.*>商铺号(.*)，剩余([-+]?\\d+(\\.\\d+)?)元<.*>费用状态：(.*)，合闸状态：(.*)。<.*>$";
        return ReUtil.getAllGroups(Pattern.compile(regex), responseBody, false);
    }

    @Nullable
    private Result sendRequestAndParseData() {
        String body = sendRequest();
        List<String> data = parseResponseBody(body);
        try {
            String name = data.get(0),
                    room = data.get(1),
                    balance = data.get(2),
                    billStatus = data.get(4),
                    electricityStatus = data.get(5);
            //log.info(body);
            //log.info("{}", data);

            Result result = new Result();
            result.setName(name);
            result.setRoom(room);
            result.setBalance(balance);
            result.setBillStatus(billStatus);
            result.setElectricityStatus(electricityStatus);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @Nullable
    public Result getResult() {
        return sendRequestAndParseData();
    }

    @Override
    public boolean isAlertEnabled() {
        return isQQAlertEnabled() || isSmsAlertEnabled();
    }

    @Override
    public boolean isQQAlertEnabled() {
        return QQ_ALERT_ENABLE;
    }

    @Override
    public boolean isSmsAlertEnabled() {
        return SMS_ALERT_ENABLE;
    }

    @Override
    public void setQQAlertEnable(boolean enable) {
        QQ_ALERT_ENABLE = enable;
    }

    @Override
    public void setSmsAlertEnable(boolean enable) {
        SMS_ALERT_ENABLE = enable;
    }

    @Override
    public boolean shouldAlert(Result result) {
        if(null == result) {
            return false;
        }
        if(!isAlertEnabled()) {
            return false;
        }

        float balance = Float.parseFloat(result.getBalance());
        // (10,20] 区间提醒一次，且夜间免打扰
        if(balance <= RED_LINE_20 && balance > RED_LINE_10 && LAST_ALERT_BALANCE > RED_LINE_20) {
            //if(!isInDaytimeWorkingHour()) {
            //    // 夜间免打扰
            //    return false;
            //}
            LAST_ALERT_BALANCE = balance;
            return true;
        }
        // (0,10] 区间提醒一次
        if(balance <= RED_LINE_10 && balance > RED_LINE_0 && LAST_ALERT_BALANCE > RED_LINE_10) {
            LAST_ALERT_BALANCE = balance;
            return true;
        }
        // (-∞,0] 区间提醒一次
        if(balance <= RED_LINE_0 && LAST_ALERT_BALANCE > RED_LINE_0) {
            LAST_ALERT_BALANCE = balance;
            return true;
        }
        // 充值了电费之后，重置 LAST_ALERT_BALANCE 值
        if(balance > LAST_ALERT_BALANCE) {
            LAST_ALERT_BALANCE = DEFAULT_LAST_ALERT_BALANCE;
        }
        return false;
    }

}
