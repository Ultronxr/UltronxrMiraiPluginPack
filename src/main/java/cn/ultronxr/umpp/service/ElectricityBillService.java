package cn.ultronxr.umpp.service;

import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Ultronxr
 * @date 2023/05/26 12:54:05
 * @description 电费查询 service
 */
public interface ElectricityBillService {

    /** 警报红线 */
    float RED_LINE_20 = 20.0f;
    float RED_LINE_10 = 10.0f;
    float RED_LINE_0 = 0.0f;

    /** 白天可以发起提醒的时间段（小时） */
    int[] DAYTIME_WORKING_HOUR = new int[] {8, 22};

    @Data
    class Result {
        // 租户姓名
        private String name;
        // 房间号
        private String room;
        // 电费余额
        private String balance;
        // 电费状态（正常、报警、欠费）
        private String billStatus;
        // 用电状态（正常、断电）
        private String electricityStatus;

        @Override
        public String toString() {
            return "房间号：" + room + "  电费余额：" + balance + "  电费状态：" + billStatus + "  用电状态：" + electricityStatus;
        }

    }

    /**
     * 获取电费查询结果
     * @return Result 对象。当请求失败或数据解析失败时，返回 null
     */
    @Nullable
    Result getResult();

    /**
     * 检查是否启用了任意一种低电费自动提醒
     * @return true - 启用了任意一种自动提醒； false - 所有自动提醒都被禁用
     */
    boolean isAlertEnabled();

    /**
     * 检查是否启用了低电费 QQ 自动提醒
     * @return true - 启用； false - 禁用
     */
    boolean isQQAlertEnabled();

    /**
     * 检查是否启用了低电费 短信 自动提醒
     * @return true - 启用； false - 禁用
     */
    boolean isSmsAlertEnabled();

    /**
     * 设置低电费 QQ 自动提醒 是否启用
     * @param enable 是否启用（true/false）
     */
    void setQQAlertEnable(boolean enable);

    /**
     * 设置低电费 短信 自动提醒 是否启用
     * @param enable 是否启用（true/false）
     */
    void setSmsAlertEnable(boolean enable);

    /**
     * 检查是否应该发起低电费自动提醒<br/>
     * 本方法存在的目的是：每一个提醒区间只发起一次自动提醒，防止多次提醒恼人；<br/>
     *                   另外还可以加上夜间免打扰时间段，仅在白天发起提醒
     *
     * @param result 电费查询结果 result 对象
     * @return true - 应该发起提醒； false - 不应该发起提醒
     */
    boolean shouldAlert(Result result);

    /**
     * 是否处于白天可以发起提醒的时间段内
     * @return true - 白天可以发起提醒； false - 夜间免打扰
     */
    default boolean isInDaytimeWorkingHour() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("GMT+8"));
        return dateTime.getHour() >= DAYTIME_WORKING_HOUR[0] && dateTime.getHour() <= DAYTIME_WORKING_HOUR[1];
    }

}
