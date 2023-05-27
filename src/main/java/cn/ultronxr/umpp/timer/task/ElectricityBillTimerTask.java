package cn.ultronxr.umpp.timer.task;

import cn.ultronxr.umpp.service.ElectricityBillService;
import cn.ultronxr.umpp.service.impl.ElectricityBillServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.TimerTask;

/**
 * @author Ultronxr
 * @date 2023/05/26 17:51:45
 * @description 定时器任务 —— 定时查询电费余额，并按照预设条件决定是否发起自动提醒
 */
@Slf4j
public class ElectricityBillTimerTask extends TimerTask {

    private final ElectricityBillService electricityBillService = new ElectricityBillServiceImpl();


    @Override
    public void run() {
        log.info("[TimerTask] 电费余额查询定时任务开始运行。");

        // 如果未开启自动提醒功能，那么不做任何操作
        if(!electricityBillService.isAlertEnabled()) {
            log.info("[TimerTask] 未开启自动提醒功能，不做任何操作。");
            return;
        }

        ElectricityBillService.Result result = electricityBillService.getResult();
        // 如果不应该发起提醒，那么不做任何操作
        if(!electricityBillService.shouldAlert(result)) {
            log.info("[TimerTask] 不应该发起提醒，不做任何操作。（result={}）", result);
            return;
        }

        // 启用了QQ提醒
        if(electricityBillService.isQQAlertEnabled()) {
            log.info("[TimerTask] 使用QQ发起提醒。");

            Bot bot = Bot.getInstance(123L);
            if(bot.isOnline()) {
                Group group = bot.getGroup(123L);
                if(null != group) {
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(123L))
                            .append(" [低电费自动提醒] ")
                            .append(result.toString())
                            .build();
                    group.sendMessage(messageChain);
                }
                group = bot.getGroup(123L);
                if(null != group) {
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(123L))
                            .append(new At(123L))
                            .append(" [低电费自动提醒] ")
                            .append(result.toString())
                            .build();
                    group.sendMessage(messageChain);
                }
            }
        }

        // 启用了短信提醒
        if(electricityBillService.isSmsAlertEnabled()) {
            log.info("[TimerTask] 使用短信发起提醒。");

            // 发送短信提醒
        }
    }

}
