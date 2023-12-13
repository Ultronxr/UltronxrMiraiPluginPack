package cn.ultronxr.umpp.service.impl;

import cn.ultronxr.umpp.service.LegendsOfRuneterraPOCService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author Ultronxr
 * @date 2023/12/13 13:19:22
 * @description
 */
@Slf4j
public class LegendsOfRuneterraPOCServiceImpl implements LegendsOfRuneterraPOCService {

    /** 英雄升级到每一级的累计经验（不是两级之间的经验差），英雄等级 = 数组下标 + 1，等级范围 [1, 30] */
    private static final int[] CHAMPION_LEVEL_UP_TOTAL_EXP = {
                0,    50,   150,   300,   500,
              800,  1250,  1750,  2310,  2980,
             3780,  4710,  5780,  6990,  8350,
             9870, 11560, 13420, 15460, 17680,
            20090, 22700, 25510, 28530, 31760,
            35210, 38880, 42780, 46920, 51290
    };

    /** 通关/打过前3关 某个难度星级的英雄之路冒险后，可以获得的 经验值*/
    private static final HashMap<String, Integer> GAIN_EXP_IN_ADVENTURE = new HashMap<>() {{
        //    普通通关                周常通关                    普通前3关                    周常前3关
        put("0.5",  100);    put("0.5_WEEKLY",  100);    put("0.5_FIRST3",  60);    put("0.5_WEEKLY_FIRST3",   -1);
        put(  "1",  305);    put(  "1_WEEKLY",  305);    put(  "1_FIRST3", 155);    put(  "1_WEEKLY_FIRST3",  155);
        put("1.5",  605);    put("1.5_WEEKLY",  605);    put("1.5_FIRST3", 305);    put("1.5_WEEKLY_FIRST3",   -1);
        put(  "2",  985);    put(  "2_WEEKLY",  985);    put(  "2_FIRST3", 395);    put(  "2_WEEKLY_FIRST3",   -1);
        put("2.5", 1425);    put("2.5_WEEKLY", 1425);    put("2.5_FIRST3", 565);    put("2.5_WEEKLY_FIRST3",  565);
        put(  "3", 1925);    put(  "3_WEEKLY", 1925);    put(  "3_FIRST3", 770);    put(  "3_WEEKLY_FIRST3",   -1);
        put("3.5", 3100);    put("3.5_WEEKLY", 3100);    put("3.5_FIRST3", 615);    put("3.5_WEEKLY_FIRST3",   -1);
        put(  "4", 4505);    put(  "4_WEEKLY", 4505);    put(  "4_FIRST3", 285);    put(  "4_WEEKLY_FIRST3", 1505);
        put("4.5", 4470);    put("4.5_WEEKLY", 4470);    put("4.5_FIRST3",  -1);    put("4.5_WEEKLY_FIRST3", 1785);
    }};

    @Override
    public CalResult calChampLevelByTotalExp(int totalExp) {
        CalResult calResult = new CalResult();

        if(totalExp < 0) {
            calResult.setMsg("英雄总经验值不能为负！合法取值区间：[0, 51290]，超出51290的仍按照30级封顶。");
            return calResult;
        }
        // 超出满经验30级的情况，按照30级封顶
        if(totalExp > CHAMPION_LEVEL_UP_TOTAL_EXP[29]) {
            calResult.setCode(0);
            calResult.setResult(30);
            return calResult;
        }

        for(int i = 0; i < CHAMPION_LEVEL_UP_TOTAL_EXP.length; i++) {
            if(totalExp <= CHAMPION_LEVEL_UP_TOTAL_EXP[i]) {
                // [TOTAL_EXP[i], TOTAL_EXP[i+1]) 这个区间内的等级都是 i；当达到 TOTAL_EXP[i+1] 时，英雄等级才是 i+1
                calResult.setResult(totalExp == CHAMPION_LEVEL_UP_TOTAL_EXP[i] ? i+1 : i);
                break;
            }
        }
        calResult.setCode(0);
        return calResult;
    }

    @Override
    public CalResult calChampLevelByGainExp(int totalExp, int gainExp) {
        return this.calChampLevelByTotalExp(totalExp + gainExp);
    }

    @Override
    public CalResult calExpGainInAdventure(String adventureDifficultyStar, boolean isWeekly, boolean isFirst3, int multiplier) {
        CalResult calResult = new CalResult();

        if(!GAIN_EXP_IN_ADVENTURE.containsKey(adventureDifficultyStar)) {
            calResult.setMsg("冒险难度星级超出范围！合法取值：0.5；1；1.5；2；2.5；3；3.5；4；4.5");
            return calResult;
        }
        if(multiplier != 0 && multiplier != 5 && multiplier != 10) {
            calResult.setMsg("经验宝珠加成倍数超出范围！合法取值：0、5、10");
            return calResult;
        }

        String key = adventureDifficultyStar + (isWeekly ? "_WEEKLY" : "") + (isFirst3 ? "_FIRST3" : "");
        Integer exp = GAIN_EXP_IN_ADVENTURE.getOrDefault(key, -1);
        if(exp == -1) {
            calResult.setMsg("未找到符合该条件的经验值记录！key = " + key);
            return calResult;
        }

        calResult.setCode(0);
        // 如果只打前3关，经验宝珠不生效
        if(isFirst3) {
            multiplier = 0;
        }
        // 经验宝珠的经验是额外获得的，获得总经验数实际上是 n+1 倍
        calResult.setResult(exp * (multiplier + 1));
        return calResult;
    }

    @Override
    public CalResult calExpNeeded2LevelUp(int totalExp, int targetLevel) {
        CalResult levelNow = this.calChampLevelByTotalExp(totalExp);
        if(levelNow.getCode() != 0) {
            return levelNow;
        }

        CalResult calResult = new CalResult();

        if(levelNow.getResult() == 30) {
            calResult.setMsg("已经满级！无需计算！");
            return calResult;
        }
        if(targetLevel < 0 || targetLevel > 30 || targetLevel <= levelNow.getResult()) {
            calResult.setMsg("目标等级超出范围！合法取值：["+ levelNow.getResult() +", 30]");
            return calResult;
        }

        int targetLevelTotalExp = CHAMPION_LEVEL_UP_TOTAL_EXP[targetLevel - 1];
        calResult.setResult(targetLevelTotalExp - totalExp);
        calResult.setCode(0);
        return calResult;
    }

}
