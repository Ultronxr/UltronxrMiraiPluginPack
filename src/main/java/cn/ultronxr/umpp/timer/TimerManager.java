package cn.ultronxr.umpp.timer;

import cn.ultronxr.umpp.timer.task.ElectricityBillTimerTask;

import java.util.Date;
import java.util.Timer;

/**
 * @author Ultronxr
 * @date 2023/05/26 17:59:12
 * @description 定时器管理，在这里启用或者禁用所有定时器任务
 */
public class TimerManager {

    private static final Timer TIMER = new Timer();


    /**
     * 启用所有定时器任务
     */
    public static void runTimerTasks() {
        TIMER.schedule(new ElectricityBillTimerTask(), new Date(), 2*60*60*1000L);
    }

}
