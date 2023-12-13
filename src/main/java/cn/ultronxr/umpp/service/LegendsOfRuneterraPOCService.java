package cn.ultronxr.umpp.service;

import lombok.Data;

/**
 * @author Ultronxr
 * @date 2023/12/13 13:18:12
 * @description LOR 游戏服务接口
 *              <b>POC 英雄之路<b/>
 */
public interface LegendsOfRuneterraPOCService {

    /**
     * 计算结果封装
     */
    @Data
    class CalResult {
        // 状态码
        private int code = -1;
        // 计算结果注释
        private String msg = "";
        // 计算结果
        private Integer result = -1;
    }

    /**
     * 根据 总经验值 计算 英雄所处的等级
     *
     * @param totalExp 总经验值
     * @return 英雄等级
    */
    CalResult calChampLevelByTotalExp(int totalExp);

    /**
     * 根据 当前总经验值 和 即将获得的经验值 计算 英雄将要达到的等级
     *
     * @param totalExp 当前总经验值
     * @param gainExp  本次获得的经验值
     * @return 英雄将要达到的等级
    */
    CalResult calChampLevelByGainExp(int totalExp, int gainExp);

    /**
     * 计算 通关/打过前3关 某个难度的英雄之路冒险后，可以获得的 经验值
     *
     * @param adventureDifficultyStar 冒险模式的难度星级
     * @param isWeekly                是否是周常
     * @param isFirst3                是否只打前3关：true - 只打前3关；false - 完全通关
     * @param multiplier              携带经验宝珠的加成倍数，可选项：0、5、10（只打前3关的话，则强制采用缺省值：0）
     * @return 通关某个难度的英雄之路冒险后，可以获得的 经验值
    */
    CalResult calExpGainInAdventure(String adventureDifficultyStar, boolean isWeekly, boolean isFirst3, int multiplier);

    /**
     * 计算 英雄升级到某个等级所需的经验值
     *
     * @param totalExp    当前总经验值
     * @param targetLevel 目标等级
     * @return 英雄升级到某个等级所需的经验值
    */
    CalResult calExpNeeded2LevelUp(int totalExp, int targetLevel);

}
